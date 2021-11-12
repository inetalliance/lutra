package net.inetalliance.lutra.elements;

import net.inetalliance.lutra.listeners.CloneListener;
import net.inetalliance.lutra.rules.AttributeRule;
import net.inetalliance.lutra.rules.ChildRule;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.IOException;
import java.io.Writer;

public class Entity extends Element {
	private final String entityName;
	public Entity(String entityName) {
		super(ElementType.ENTITY, ChildRule.NONE, AttributeRule.NONE);
		this.entityName = entityName;
	}

	@Override
	public String toString() {
		return "&" + entityName + ";";
	}
	@Override
	public boolean toString(final Appendable output, final boolean pretty, final int depth,
	                        final ElementType previous, final ElementType next) throws IOException {
		output.append(toString());
		return false;
	}

	@Override
	public Element copy() {
		return new Entity(entityName);
	}

	@Override
	public Element copyWithListeners(Iterable<? extends CloneListener> listeners) {
		return new Entity(entityName);
	}

	@Override
	public Element setText(String text) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element setClass(String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element setClass(Enum<?>... cssClasses) {
		throw new UnsupportedOperationException();
	}

	public static EntityReference ampersand(final XMLStreamException e) {
		return new EntityReference() {
			@Override
			public EntityDeclaration getDeclaration() {
				return null;
			}

			@Override
			public String getName() {
				return "amp";
			}

			@Override
			public int getEventType() {
				return ENTITY_REFERENCE;
			}

			@Override
			public Location getLocation() {
				return e.getLocation();
			}

			@Override
			public boolean isStartElement() {
				return false;
			}

			@Override
			public boolean isAttribute() {
				return false;
			}

			@Override
			public boolean isNamespace() {
				return false;
			}

			@Override
			public boolean isEndElement() {
				return false;
			}

			@Override
			public boolean isEntityReference() {
				return true;
			}

			@Override
			public boolean isProcessingInstruction() {
				return false;
			}

			@Override
			public boolean isCharacters() {
				return false;
			}

			@Override
			public boolean isStartDocument() {
				return false;
			}

			@Override
			public boolean isEndDocument() {
				return false;
			}

			@Override
			public StartElement asStartElement() {
				return null;
			}

			@Override
			public EndElement asEndElement() {
				return null;
			}

			@Override
			public Characters asCharacters() {
				return null;
			}

			@Override
			public QName getSchemaType() {
				return null;
			}

			@Override
			public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {

			}
		};
	}

}
