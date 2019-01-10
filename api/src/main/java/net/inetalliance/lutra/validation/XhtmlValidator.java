package net.inetalliance.lutra.validation;

import net.inetalliance.funky.Escaper;
import net.inetalliance.lutra.DocumentBuilder;
import net.inetalliance.lutra.elements.Element;
import net.inetalliance.types.annotations.Xhtml;
import net.inetalliance.types.localized.LocalizedString;
import net.inetalliance.validation.ValidationError;
import net.inetalliance.validation.types.LocalizedObjectWrapper;
import net.inetalliance.validation.types.StatelessTypeValidator;
import net.inetalliance.validation.validators.AnnotationValidator;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.util.Locale;

public class XhtmlValidator extends AnnotationValidator<Xhtml>
{
	public XhtmlValidator()
	{
		super(2);
		final StatelessTypeValidator<Xhtml, String> string =
			new StatelessTypeValidator<>() {
				@Override
				public String validate(final Object object, String fieldValue, final Locale locale,
				                       final boolean creating) {
					if (fieldValue == null)
						return null;
					else {
						for (int i = 0; i < 3; i++) {
							final DocumentBuilder builder = new DocumentBuilder(null);
							try {
								final StringBuilder xml = new StringBuilder(Element.XML_HEADER);
								xml.append("<span>");
								xml.append(fieldValue);
								xml.append("</span>");
								builder.load(new ByteArrayInputStream(xml.toString().getBytes()));
								return null;
							} catch (SAXException e) {
								if (e.getMessage().contains("\"&\" in the entity reference"))
									fieldValue = fieldValue.replaceAll("&", "&amp;");
								else if (Element.SAX_ENTITY_ERROR.matcher(e.getMessage()).matches())
									fieldValue = Escaper.html40.escape(fieldValue);
								else
									return e.getMessage();
							} catch (Exception e) {
								throw new ValidationError(e);
							}
						}
						return "XHTML parse failed.";
					}
				}

				@Override
				public String toJavascript(final Locale locale) {
					return null;
				}

				@Override
				public String toColumnCheck(final String name) {
					return null;
				}
			};

		register(String.class, string);
		register(LocalizedString.class,
			new LocalizedObjectWrapper<>(String.class, string,
				"validation.localizedXhtml"));
	}
}
