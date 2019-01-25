package net.inetalliance.lutra.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.*;
import static java.util.stream.Collectors.*;

/**
 * Provides functors for escaping and unescaping strings for various languages.
 */
public interface Escaper {
	String escape(String str);

	String unescape(String str);

	Escaper url = new Escaper() {

		@Override
		public String escape(final String str) {
			return URLEncoder.encode(str, StandardCharsets.UTF_8);
		}

		@Override
		public String unescape(final String str) {
			return URLDecoder.decode(str, StandardCharsets.UTF_8);
		}
	};

	interface StreamEscaper
		extends Escaper {
		String escape(char c);

		void unescape(StringBuilder out, String str, int length);

		default String escape(final String str) {
			return ofNullable(str)
				.filter(s -> !s.isEmpty())
				.map(s -> str.chars().mapToObj(i -> (char) i)
					.map(this::escape)
					.collect(joining())
					.trim())
				.orElse(str);
		}

		default String unescape(final String str) {
			return ofNullable(str).filter(s -> !s.isEmpty()).map(s -> {
				final int length = s.length();
				final StringBuilder out = new StringBuilder(s.length());
				unescape(out, s, length);
				return out.toString().trim();
			}).orElse(str);
		}
	}

	Escaper js = new StreamEscaper() {
		public String escape(char ch) {
			// handle unicode
			if (ch > 0xfff) {
				return "\\u" + hex(ch);
			} else if (ch > 0xff) {
				return "\\u0" + hex(ch);
			} else if (ch > 0x7f) {
				return "\\u00" + hex(ch);
			} else if (ch < 32) {
				return Escaper.commonEscapes(ch, hex(ch));
			} else {
				switch (ch) {
					case '/':
						return "\\/";
					case '"':
						return "\\\"";
					case '\\':
						return "\\\\";
					default:
						return String.valueOf(ch);
				}
			}
		}

		@Override
		public void unescape(StringBuilder out, String str, int length) {
			Escaper.commonUnescapes(out, str, length);
		}
	};

	Escaper javaString = new StreamEscaper() {
		@Override
		public String escape(char ch) {
			// handle unicode
			if (ch > 0xfff) {
				return "\\u" + hex(ch);
			} else if (ch > 0xff) {
				return "\\u0" + hex(ch);
			} else if (ch > 0x7f) {
				return "\\u00" + hex(ch);
			} else if (ch < 32) {
				return commonEscapes(ch, hex(ch));
			} else {
				switch (ch) {
					case '\'':
						return "\\\'";
					case '"':
						return "\\\"";
					case '\\':
						return "\\\\";
					default:
						return String.valueOf(ch);
				}
			}
		}

		@Override
		public void unescape(StringBuilder out, String str, int length) {
			commonUnescapes(out, str, length);
		}
	};

	static String hex(final char c) {
		return Integer.toHexString(c).toUpperCase();
	}

	static String commonEscapes(char ch, String hex) {
		switch (ch) {
			case '\b':  // backspace
				return "\\b";
			case '\n':  // new line
				return "\\n";
			case '\t':  // tab
				return "\\t";
			case '\f':  // form feed
				return "\\f";
			case '\r':  // carriage return
				return "\\r";
			default:
				if (ch > 0xf) {
					return "\\u00" + hex;
				} else {
					return "\\u000" + hex;
				}
		}
	}

	static void commonUnescapes(StringBuilder out, String str, int length) {
		final StringBuffer unicode = new StringBuffer(4);
		boolean hadSlash = false;
		boolean inUnicode = false;
		for (int i = 0; i < length; i++) {
			final char ch = str.charAt(i);
			if (inUnicode) {
				// if in unicode, then we're reading unicode
				// values in somehow
				unicode.append(ch);
				if (unicode.length() == 4) {
					// unicode now contains the four hex digits
					// which represents our unicode character
					try {
						final int value = Integer.parseInt(unicode.toString(), 16);
						out.append((char) value);
						unicode.setLength(0);
						inUnicode = false;
						hadSlash = false;
					} catch (NumberFormatException nfe) {
						throw new RuntimeException(String.format("Unable to parse unicode value: %s", unicode), nfe);
					}
				}
				continue;
			}
			if (hadSlash) {
				// handle an escaped value
				hadSlash = false;
				switch (ch) {
					case '\\':
						out.append('\\');
						break;
					case '\'':
						out.append('\'');
						break;
					case '\"':
						out.append('"');
						break;
					case 'r':
						out.append('\r');
						break;
					case 'f':
						out.append('\f');
						break;
					case 't':
						out.append('\t');
						break;
					case 'n':
						out.append('\n');
						break;
					case 'b':
						out.append('\b');
						break;
					case 'u': {
						// uh-oh, we're in unicode country....
						inUnicode = true;
						break;
					}
					default:
						out.append(ch);
						break;
				}
				continue;
			} else if (ch == '\\') {
				hadSlash = true;
				continue;
			}
			out.append(ch);
		}
		if (hadSlash) {
			// then we're in the weird case of a \ at the end of the
			// string, let's output it anyway.
			out.append('\\');
		}
	}

	class EntityEscaper
		implements Escaper {
		private final Map<String, Integer> toCode = new HashMap<>(255);
		private final Map<Integer, String> toName = new HashMap<>(255);
		private static final Pattern entityPattern = Pattern.compile("&[A-Za-z0-9]*;");

		@SafeVarargs
		private EntityEscaper(final Map<String, Integer>... entityMaps) {
			for (final Map<String, Integer> map : entityMaps) {
				for (final Map.Entry<String, Integer> entry : map.entrySet()) {
					toCode.put(entry.getKey(), entry.getValue());
					toName.put(entry.getValue(), entry.getKey());
				}
			}
		}

		public String removeEntities(final String string) {
			final Matcher matcher = entityPattern.matcher(string);
			final StringBuffer replaced = new StringBuffer(string.length());
			while (matcher.find()) {
				matcher.appendReplacement(replaced, "");
			}
			matcher.appendTail(replaced);
			return replaced.toString();
		}

		public String replaceWithUnicode(final String string) {
			final Matcher matcher = entityPattern.matcher(string);
			final StringBuffer replaced = new StringBuffer(string.length());
			while (matcher.find()) {
				final String entity = matcher.group();
				final Integer code = toCode.get(entity.substring(1, entity.length() - 1));
				final String replacement = code == null ? entity : String.format("&#%03d;", code);
				matcher.appendReplacement(replaced, replacement);
			}
			matcher.appendTail(replaced);
			return replaced.toString();
		}

		@Override
		public String escape(final String str) {
			if (str == null) {
				return null;
			}
			final StringBuilder builder = new StringBuilder((int) (str.length() * 1.1));
			final int length = str.length();
			for (int i = 0; i < length; i++) {
				final char c = str.charAt(i);
				final String entityName = toName.get((int) c);
				if (entityName == null) {
					if (c > 0x7F) {
						builder.append("&#");
						builder.append(Integer.toString(c, 10));
						builder.append(';');
					} else {
						builder.append(c);
					}
				} else {
					builder.append('&');
					builder.append(entityName);
					builder.append(';');
				}
			}
			return builder.toString();
		}

		@Override
		public String unescape(final String str) {
			if (str == null) {
				return null;
			}
			final int firstAmp = str.indexOf('&');
			if (firstAmp < 0) {
				return str;
			} else {
				final StringBuilder builder = new StringBuilder((int) (str.length() * 1.1));
				builder.append(str, 0, firstAmp);
				final int length = str.length();
				for (int i = firstAmp; i < length; i++) {
					final char c = str.charAt(i);
					if (c == '&') {
						final int nextIdx = i + 1;
						final int semiColonIdx = str.indexOf(';', nextIdx);
						if (semiColonIdx == -1) {
							builder.append(c);
							continue;
						}
						final int amphersandIdx = str.indexOf('&', i + 1);
						if (amphersandIdx != -1 && amphersandIdx < semiColonIdx) {
							// Then the text looks like &...&...;
							builder.append(c);
							continue;
						}
						final String entityContent = str.substring(nextIdx, semiColonIdx);
						int entityValue = -1;
						final int entityContentLen = entityContent.length();
						if (entityContentLen > 0) {
							if (entityContent.charAt(0) == '#') { // escaped value content is an integer (decimal or
								// hexidecimal)
								if (entityContentLen > 1) {
									final char isHexChar = entityContent.charAt(1);
									try {
										switch (isHexChar) {
											case 'X':
											case 'x': {
												entityValue = Integer.parseInt(entityContent.substring(2), 16);
												break;
											}
											default: {
												entityValue = Integer.parseInt(entityContent.substring(1), 10);
											}
										}
										if (entityValue > 0xFFFF) {
											entityValue = -1;
										}
									} catch (NumberFormatException e) {
										entityValue = -1;
									}
								}
							} else { // escaped value content is an entity name
								final Integer result = toCode.get(entityContent);
								entityValue = result == null ? -1 : result;
							}
						}

						if (entityValue == -1) {
							builder.append('&').append(entityContent).append(';');
						} else {
							builder.append((char) entityValue);
						}
						i = semiColonIdx; // move index up to the semi-colon
					} else {
						builder.append(c);
					}
				}
				return builder.toString();
			}
		}

		static final Map<String, Integer> basic = new HashMap<>(4);
		static final Map<String, Integer> apos = new HashMap<>(1);
		static final Map<String, Integer> iso8859_1 = new HashMap<>(255);
		static final Map<String, Integer> html4 = new HashMap<>(255);

		static {
			basic.put("quot", 34); // " - double-quote
			basic.put("amp", 38); // & - ampersand
			basic.put("lt", 60); // < - less-than
			basic.put("gt", 62); // > - greater-than
			apos.put("apos", 39); // XML apostrophe
			iso8859_1.put("nbsp", 160); // non-breaking space
			iso8859_1.put("iexcl", 161); // inverted exclamation mark
			iso8859_1.put("cent", 162); // cent sign
			iso8859_1.put("pound", 163); // pound sign
			iso8859_1.put("curren", 164); // currency sign
			iso8859_1.put("yen", 165); // yen sign = yuan sign
			iso8859_1.put("brvbar", 166); // broken bar = broken vertical bar
			iso8859_1.put("sect", 167); // section sign
			iso8859_1.put("uml", 168); // diaeresis = spacing diaeresis
			iso8859_1.put("copy", 169); // � - copyright sign
			iso8859_1.put("ordf", 170); // feminine ordinal indicator
			iso8859_1.put("laquo", 171); // left-pointing double angle quotation mark = left pointing guillemet
			iso8859_1.put("not", 172); // not sign
			iso8859_1.put("shy", 173); // soft hyphen = discretionary hyphen
			iso8859_1.put("reg", 174); // � - registered trademark sign
			iso8859_1.put("macr", 175); // macron = spacing macron = overline = APL overbar
			iso8859_1.put("deg", 176); // degree sign
			iso8859_1.put("plusmn", 177); // plus-minus sign = plus-or-minus sign
			iso8859_1.put("sup2", 178); // superscript two = superscript digit two = squared
			iso8859_1.put("sup3", 179); // superscript three = superscript digit three = cubed
			iso8859_1.put("acute", 180); // acute accent = spacing acute
			iso8859_1.put("micro", 181); // micro sign
			iso8859_1.put("para", 182); // pilcrow sign = paragraph sign
			iso8859_1.put("middot", 183); // middle dot = Georgian comma = Greek middle dot
			iso8859_1.put("cedil", 184); // cedilla = spacing cedilla
			iso8859_1.put("sup1", 185); // superscript one = superscript digit one
			iso8859_1.put("ordm", 186); // masculine ordinal indicator
			iso8859_1.put("raquo", 187); // right-pointing double angle quotation mark = right pointing guillemet
			iso8859_1.put("frac14", 188); // vulgar fraction one quarter = fraction one quarter
			iso8859_1.put("frac12", 189); // vulgar fraction one half = fraction one half
			iso8859_1.put("frac34", 190); // vulgar fraction three quarters = fraction three quarters
			iso8859_1.put("iquest", 191); // inverted question mark = turned question mark
			iso8859_1.put("Agrave", 192); // � - uppercase A, grave accent
			iso8859_1.put("Aacute", 193); // � - uppercase A, acute accent
			iso8859_1.put("Acirc", 194); // � - uppercase A, circumflex accent
			iso8859_1.put("Atilde", 195); // � - uppercase A, tilde
			iso8859_1.put("Auml", 196); // � - uppercase A, umlaut
			iso8859_1.put("Aring", 197); // � - uppercase A, ring
			iso8859_1.put("AElig", 198); // � - uppercase AE
			iso8859_1.put("Ccedil", 199); // � - uppercase C, cedilla
			iso8859_1.put("Egrave", 200); // � - uppercase E, grave accent
			iso8859_1.put("Eacute", 201); // � - uppercase E, acute accent
			iso8859_1.put("Ecirc", 202); // � - uppercase E, circumflex accent
			iso8859_1.put("Euml", 203); // � - uppercase E, umlaut
			iso8859_1.put("Igrave", 204); // � - uppercase I, grave accent
			iso8859_1.put("Iacute", 205); // � - uppercase I, acute accent
			iso8859_1.put("Icirc", 206); // � - uppercase I, circumflex accent
			iso8859_1.put("Iuml", 207); // � - uppercase I, umlaut
			iso8859_1.put("ETH", 208); // � - uppercase Eth, Icelandic
			iso8859_1.put("Ntilde", 209); // � - uppercase N, tilde
			iso8859_1.put("Ograve", 210); // � - uppercase O, grave accent
			iso8859_1.put("Oacute", 211); // � - uppercase O, acute accent
			iso8859_1.put("Ocirc", 212); // � - uppercase O, circumflex accent
			iso8859_1.put("Otilde", 213); // � - uppercase O, tilde
			iso8859_1.put("Ouml", 214); // � - uppercase O, umlaut
			iso8859_1.put("times", 215); // multiplication sign
			iso8859_1.put("Oslash", 216); // � - uppercase O, slash
			iso8859_1.put("Ugrave", 217); // � - uppercase U, grave accent
			iso8859_1.put("Uacute", 218); // � - uppercase U, acute accent
			iso8859_1.put("Ucirc", 219); // � - uppercase U, circumflex accent
			iso8859_1.put("Uuml", 220); // � - uppercase U, umlaut
			iso8859_1.put("Yacute", 221); // � - uppercase Y, acute accent
			iso8859_1.put("THORN", 222); // � - uppercase THORN, Icelandic
			iso8859_1.put("szlig", 223); // � - lowercase sharps, German
			iso8859_1.put("agrave", 224); // � - lowercase a, grave accent
			iso8859_1.put("aacute", 225); // � - lowercase a, acute accent
			iso8859_1.put("acirc", 226); // � - lowercase a, circumflex accent
			iso8859_1.put("atilde", 227); // � - lowercase a, tilde
			iso8859_1.put("auml", 228); // � - lowercase a, umlaut
			iso8859_1.put("aring", 229); // � - lowercase a, ring
			iso8859_1.put("aelig", 230); // � - lowercase ae
			iso8859_1.put("ccedil", 231); // � - lowercase c, cedilla
			iso8859_1.put("egrave", 232); // � - lowercase e, grave accent
			iso8859_1.put("eacute", 233); // � - lowercase e, acute accent
			iso8859_1.put("ecirc", 234); // � - lowercase e, circumflex accent
			iso8859_1.put("euml", 235); // � - lowercase e, umlaut
			iso8859_1.put("igrave", 236); // � - lowercase i, grave accent
			iso8859_1.put("iacute", 237); // � - lowercase i, acute accent
			iso8859_1.put("icirc", 238); // � - lowercase i, circumflex accent
			iso8859_1.put("iuml", 239); // � - lowercase i, umlaut
			iso8859_1.put("eth", 240); // � - lowercase eth, Icelandic
			iso8859_1.put("ntilde", 241); // � - lowercase n, tilde
			iso8859_1.put("ograve", 242); // � - lowercase o, grave accent
			iso8859_1.put("oacute", 243); // � - lowercase o, acute accent
			iso8859_1.put("ocirc", 244); // � - lowercase o, circumflex accent
			iso8859_1.put("otilde", 245); // � - lowercase o, tilde
			iso8859_1.put("ouml", 246); // � - lowercase o, umlaut
			iso8859_1.put("divide", 247); // division sign
			iso8859_1.put("oslash", 248); // � - lowercase o, slash
			iso8859_1.put("ugrave", 249); // � - lowercase u, grave accent
			iso8859_1.put("uacute", 250); // � - lowercase u, acute accent
			iso8859_1.put("ucirc", 251); // � - lowercase u, circumflex accent
			iso8859_1.put("uuml", 252); // � - lowercase u, umlaut
			iso8859_1.put("yacute", 253); // � - lowercase y, acute accent
			iso8859_1.put("thorn", 254); // � - lowercase thorn, Icelandic
			iso8859_1.put("yuml", 255); // � - lowercase y, umlaut
			// <!-- Latin Extended-B -->
			html4.put("fnof", 402); // latin small f with hook = function= florin, U+0192 ISOtech -->
			// <!-- Greek -->
			html4.put("Alpha", 913); // greek capital letter alpha, U+0391 -->
			html4.put("Beta", 914); // greek capital letter beta, U+0392 -->
			html4.put("Gamma", 915); // greek capital letter gamma,U+0393 ISOgrk3 -->
			html4.put("Delta", 916); // greek capital letter delta,U+0394 ISOgrk3 -->
			html4.put("Epsilon", 917); // greek capital letter epsilon, U+0395 -->
			html4.put("Zeta", 918); // greek capital letter zeta, U+0396 -->
			html4.put("Eta", 919); // greek capital letter eta, U+0397 -->
			html4.put("Theta", 920); // greek capital letter theta,U+0398 ISOgrk3 -->
			html4.put("Iota", 921); // greek capital letter iota, U+0399 -->
			html4.put("Kappa", 922); // greek capital letter kappa, U+039A -->
			html4.put("Lambda", 923); // greek capital letter lambda,U+039B ISOgrk3 -->
			html4.put("Mu", 924); // greek capital letter mu, U+039C -->
			html4.put("Nu", 925); // greek capital letter nu, U+039D -->
			html4.put("Xi", 926); // greek capital letter xi, U+039E ISOgrk3 -->
			html4.put("Omicron", 927); // greek capital letter omicron, U+039F -->
			html4.put("Pi", 928); // greek capital letter pi, U+03A0 ISOgrk3 -->
			html4.put("Rho", 929); // greek capital letter rho, U+03A1 -->
			// <!-- there is no Sigmaf, and no U+03A2 character either -->
			html4.put("Sigma", 931); // greek capital letter sigma,U+03A3 ISOgrk3 -->
			html4.put("Tau", 932); // greek capital letter tau, U+03A4 -->
			html4.put("Upsilon", 933); // greek capital letter upsilon,U+03A5 ISOgrk3 -->
			html4.put("Phi", 934); // greek capital letter phi,U+03A6 ISOgrk3 -->
			html4.put("Chi", 935); // greek capital letter chi, U+03A7 -->
			html4.put("Psi", 936); // greek capital letter psi,U+03A8 ISOgrk3 -->
			html4.put("Omega", 937); // greek capital letter omega,U+03A9 ISOgrk3 -->
			html4.put("alpha", 945); // greek small letter alpha,U+03B1 ISOgrk3 -->
			html4.put("beta", 946); // greek small letter beta, U+03B2 ISOgrk3 -->
			html4.put("gamma", 947); // greek small letter gamma,U+03B3 ISOgrk3 -->
			html4.put("delta", 948); // greek small letter delta,U+03B4 ISOgrk3 -->
			html4.put("epsilon", 949); // greek small letter epsilon,U+03B5 ISOgrk3 -->
			html4.put("zeta", 950); // greek small letter zeta, U+03B6 ISOgrk3 -->
			html4.put("eta", 951); // greek small letter eta, U+03B7 ISOgrk3 -->
			html4.put("theta", 952); // greek small letter theta,U+03B8 ISOgrk3 -->
			html4.put("iota", 953); // greek small letter iota, U+03B9 ISOgrk3 -->
			html4.put("kappa", 954); // greek small letter kappa,U+03BA ISOgrk3 -->
			html4.put("lambda", 955); // greek small letter lambda,U+03BB ISOgrk3 -->
			html4.put("mu", 956); // greek small letter mu, U+03BC ISOgrk3 -->
			html4.put("nu", 957); // greek small letter nu, U+03BD ISOgrk3 -->
			html4.put("xi", 958); // greek small letter xi, U+03BE ISOgrk3 -->
			html4.put("omicron", 959); // greek small letter omicron, U+03BF NEW -->
			html4.put("pi", 960); // greek small letter pi, U+03C0 ISOgrk3 -->
			html4.put("rho", 961); // greek small letter rho, U+03C1 ISOgrk3 -->
			html4.put("sigmaf", 962); // greek small letter final sigma,U+03C2 ISOgrk3 -->
			html4.put("sigma", 963); // greek small letter sigma,U+03C3 ISOgrk3 -->
			html4.put("tau", 964); // greek small letter tau, U+03C4 ISOgrk3 -->
			html4.put("upsilon", 965); // greek small letter upsilon,U+03C5 ISOgrk3 -->
			html4.put("phi", 966); // greek small letter phi, U+03C6 ISOgrk3 -->
			html4.put("chi", 967); // greek small letter chi, U+03C7 ISOgrk3 -->
			html4.put("psi", 968); // greek small letter psi, U+03C8 ISOgrk3 -->
			html4.put("omega", 969); // greek small letter omega,U+03C9 ISOgrk3 -->
			html4.put("thetasym", 977); // greek small letter theta symbol,U+03D1 NEW -->
			html4.put("upsih", 978); // greek upsilon with hook symbol,U+03D2 NEW -->
			html4.put("piv", 982); // greek pi symbol, U+03D6 ISOgrk3 -->
			// <!-- General Punctuation -->
			html4.put("bull", 8226); // bullet = black small circle,U+2022 ISOpub -->
			// <!-- bullet is NOT the same as bullet operator, U+2219 -->
			html4.put("hellip", 8230); // horizontal ellipsis = three dot leader,U+2026 ISOpub -->
			html4.put("prime", 8242); // prime = minutes = feet, U+2032 ISOtech -->
			html4.put("Prime", 8243); // double prime = seconds = inches,U+2033 ISOtech -->
			html4.put("oline", 8254); // overline = spacing overscore,U+203E NEW -->
			html4.put("frasl", 8260); // fraction slash, U+2044 NEW -->
			// <!-- Letterlike Symbols -->
			html4.put("weierp", 8472); // script capital P = power set= Weierstrass p, U+2118 ISOamso -->
			html4.put("image", 8465); // blackletter capital I = imaginary part,U+2111 ISOamso -->
			html4.put("real", 8476); // blackletter capital R = real part symbol,U+211C ISOamso -->
			html4.put("trade", 8482); // trade mark sign, U+2122 ISOnum -->
			html4.put("alefsym", 8501); // alef symbol = first transfinite cardinal,U+2135 NEW -->
			// <!-- alef symbol is NOT the same as hebrew letter alef,U+05D0 although the
			// same glyph could be used to depict both characters -->
			// <!-- Arrows -->
			html4.put("larr", 8592); // leftwards arrow, U+2190 ISOnum -->
			html4.put("uarr", 8593); // upwards arrow, U+2191 ISOnum-->
			html4.put("rarr", 8594); // rightwards arrow, U+2192 ISOnum -->
			html4.put("darr", 8595); // downwards arrow, U+2193 ISOnum -->
			html4.put("harr", 8596); // left right arrow, U+2194 ISOamsa -->
			html4.put("crarr", 8629); // downwards arrow with corner leftwards= carriage return, U+21B5 NEW -->
			html4.put("lArr", 8656); // leftwards double arrow, U+21D0 ISOtech -->
			// <!-- ISO 10646 does not say that lArr is the same as the 'is implied by'
			// arrow but also does not have any other character for that function.
			// So ? lArr canbe used for 'is implied by' as ISOtech suggests -->
			html4.put("uArr", 8657); // upwards double arrow, U+21D1 ISOamsa -->
			html4.put("rArr", 8658); // rightwards double arrow,U+21D2 ISOtech -->
			// <!-- ISO 10646 does not say this is the 'implies' character but does not
			// have another character with this function so ?rArr can be used for
			// 'implies' as ISOtech suggests -->
			html4.put("dArr", 8659); // downwards double arrow, U+21D3 ISOamsa -->
			html4.put("hArr", 8660); // left right double arrow,U+21D4 ISOamsa -->
			// <!-- Mathematical Operators -->
			html4.put("forall", 8704); // for all, U+2200 ISOtech -->
			html4.put("part", 8706); // partial differential, U+2202 ISOtech -->
			html4.put("exist", 8707); // there exists, U+2203 ISOtech -->
			html4.put("empty", 8709); // empty set = null set = diameter,U+2205 ISOamso -->
			html4.put("nabla", 8711); // nabla = backward difference,U+2207 ISOtech -->
			html4.put("isin", 8712); // element of, U+2208 ISOtech -->
			html4.put("notin", 8713); // not an element of, U+2209 ISOtech -->
			html4.put("ni", 8715); // contains as member, U+220B ISOtech -->
			// <!-- should there be a more memorable name than 'ni'? -->
			html4.put("prod", 8719); // n-ary product = product sign,U+220F ISOamsb -->
			// <!-- prod is NOT the same character as U+03A0 'greek capital letter pi'
			// though the same glyph might be used for both -->
			html4.put("sum", 8721); // n-ary summation, U+2211 ISOamsb -->
			// <!-- sum is NOT the same character as U+03A3 'greek capital letter sigma'
			// though the same glyph might be used for both -->
			html4.put("minus", 8722); // minus sign, U+2212 ISOtech -->
			html4.put("lowast", 8727); // asterisk operator, U+2217 ISOtech -->
			html4.put("radic", 8730); // square root = radical sign,U+221A ISOtech -->
			html4.put("prop", 8733); // proportional to, U+221D ISOtech -->
			html4.put("infin", 8734); // infinity, U+221E ISOtech -->
			html4.put("ang", 8736); // angle, U+2220 ISOamso -->
			html4.put("and", 8743); // logical and = wedge, U+2227 ISOtech -->
			html4.put("or", 8744); // logical or = vee, U+2228 ISOtech -->
			html4.put("cap", 8745); // intersection = cap, U+2229 ISOtech -->
			html4.put("cup", 8746); // union = cup, U+222A ISOtech -->
			html4.put("int", 8747); // integral, U+222B ISOtech -->
			html4.put("there4", 8756); // therefore, U+2234 ISOtech -->
			html4.put("sim", 8764); // tilde operator = varies with = similar to,U+223C ISOtech -->
			// <!-- tilde operator is NOT the same character as the tilde, U+007E,although
			// the same glyph might be used to represent both -->
			html4.put("cong", 8773); // approximately equal to, U+2245 ISOtech -->
			html4.put("asymp", 8776); // almost equal to = asymptotic to,U+2248 ISOamsr -->
			html4.put("ne", 8800); // not equal to, U+2260 ISOtech -->
			html4.put("equiv", 8801); // identical to, U+2261 ISOtech -->
			html4.put("le", 8804); // less-than or equal to, U+2264 ISOtech -->
			html4.put("ge", 8805); // greater-than or equal to,U+2265 ISOtech -->
			html4.put("sub", 8834); // subset of, U+2282 ISOtech -->
			html4.put("sup", 8835); // superset of, U+2283 ISOtech -->
			// <!-- note that nsup, 'not a superset of, U+2283' is not covered by the
			// Symbol font encoding and is not included. Should it be, for symmetry?
			// It is in ISOamsn --> <!ENTITY nsub", 8836);
			// not a subset of, U+2284 ISOamsn -->
			html4.put("sube", 8838); // subset of or equal to, U+2286 ISOtech -->
			html4.put("supe", 8839); // superset of or equal to,U+2287 ISOtech -->
			html4.put("oplus", 8853); // circled plus = direct sum,U+2295 ISOamsb -->
			html4.put("otimes", 8855); // circled times = vector product,U+2297 ISOamsb -->
			html4.put("perp", 8869); // up tack = orthogonal to = perpendicular,U+22A5 ISOtech -->
			html4.put("sdot", 8901); // dot operator, U+22C5 ISOamsb -->
			// <!-- dot operator is NOT the same character as U+00B7 middle dot -->
			// <!-- Miscellaneous Technical -->
			html4.put("lceil", 8968); // left ceiling = apl upstile,U+2308 ISOamsc -->
			html4.put("rceil", 8969); // right ceiling, U+2309 ISOamsc -->
			html4.put("lfloor", 8970); // left floor = apl downstile,U+230A ISOamsc -->
			html4.put("rfloor", 8971); // right floor, U+230B ISOamsc -->
			html4.put("lang", 9001); // left-pointing angle bracket = bra,U+2329 ISOtech -->
			// <!-- lang is NOT the same character as U+003C 'less than' or U+2039 'single left-pointing angle quotation
			// mark' -->
			html4.put("rang", 9002); // right-pointing angle bracket = ket,U+232A ISOtech -->
			// <!-- rang is NOT the same character as U+003E 'greater than' or U+203A
			// 'single right-pointing angle quotation mark' -->
			// <!-- Geometric Shapes -->
			html4.put("loz", 9674); // lozenge, U+25CA ISOpub -->
			// <!-- Miscellaneous Symbols -->
			html4.put("spades", 9824); // black spade suit, U+2660 ISOpub -->
			// <!-- black here seems to mean filled as opposed to hollow -->
			html4.put("clubs", 9827); // black club suit = shamrock,U+2663 ISOpub -->
			html4.put("hearts", 9829); // black heart suit = valentine,U+2665 ISOpub -->
			html4.put("diams", 9830); // black diamond suit, U+2666 ISOpub -->

			// <!-- Latin Extended-A -->
			html4.put("OElig", 338); // -- latin capital ligature OE,U+0152 ISOlat2 -->
			html4.put("oelig", 339); // -- latin small ligature oe, U+0153 ISOlat2 -->
			// <!-- ligature is a misnomer, this is a separate character in some languages -->
			html4.put("Scaron", 352); // -- latin capital letter S with caron,U+0160 ISOlat2 -->
			html4.put("scaron", 353); // -- latin small letter s with caron,U+0161 ISOlat2 -->
			html4.put("Yuml", 376); // -- latin capital letter Y with diaeresis,U+0178 ISOlat2 -->
			// <!-- Spacing Modifier Letters -->
			html4.put("circ", 710); // -- modifier letter circumflex accent,U+02C6 ISOpub -->
			html4.put("tilde", 732); // small tilde, U+02DC ISOdia -->
			// <!-- General Punctuation -->
			html4.put("ensp", 8194); // en space, U+2002 ISOpub -->
			html4.put("emsp", 8195); // em space, U+2003 ISOpub -->
			html4.put("thinsp", 8201); // thin space, U+2009 ISOpub -->
			html4.put("zwnj", 8204); // zero width non-joiner,U+200C NEW RFC 2070 -->
			html4.put("zwj", 8205); // zero width joiner, U+200D NEW RFC 2070 -->
			html4.put("lrm", 8206); // left-to-right mark, U+200E NEW RFC 2070 -->
			html4.put("rlm", 8207); // right-to-left mark, U+200F NEW RFC 2070 -->
			html4.put("ndash", 8211); // en dash, U+2013 ISOpub -->
			html4.put("mdash", 8212); // em dash, U+2014 ISOpub -->
			html4.put("lsquo", 8216); // left single quotation mark,U+2018 ISOnum -->
			html4.put("rsquo", 8217); // right single quotation mark,U+2019 ISOnum -->
			html4.put("sbquo", 8218); // single low-9 quotation mark, U+201A NEW -->
			html4.put("ldquo", 8220); // left double quotation mark,U+201C ISOnum -->
			html4.put("rdquo", 8221); // right double quotation mark,U+201D ISOnum -->
			html4.put("bdquo", 8222); // double low-9 quotation mark, U+201E NEW -->
			html4.put("dagger", 8224); // dagger, U+2020 ISOpub -->
			html4.put("Dagger", 8225); // double dagger, U+2021 ISOpub -->
			html4.put("permil", 8240); // per mille sign, U+2030 ISOtech -->
			html4.put("lsaquo", 8249); // single left-pointing angle quotation mark,U+2039 ISO proposed -->
			// <!-- lsaquo is proposed but not yet ISO standardized -->
			html4.put("rsaquo", 8250); // single right-pointing angle quotation mark,U+203A ISO proposed -->
			// <!-- rsaquo is proposed but not yet ISO standardized -->
			html4.put("euro", 8364); // -- euro sign, U+20AC NEW -->
		}
	}

	EntityEscaper html32 = new EntityEscaper(EntityEscaper.basic, EntityEscaper.iso8859_1);
	EntityEscaper html40 = new EntityEscaper(EntityEscaper.basic, EntityEscaper.iso8859_1, EntityEscaper.html4);
	EntityEscaper xml = new EntityEscaper(EntityEscaper.basic, EntityEscaper.apos);
}

