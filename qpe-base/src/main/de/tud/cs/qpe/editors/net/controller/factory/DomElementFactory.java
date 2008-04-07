package de.tud.cs.qpe.editors.net.controller.factory;

import org.dom4j.Element;
import org.eclipse.gef.requests.CreationFactory;

public class DomElementFactory implements CreationFactory {
	protected Class classType;

	public DomElementFactory(Class classType) {
		this.classType = classType;
	}

	public Element getNewObject() {
		try {
			return (Element) classType.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object getObjectType() {
		return classType;
	}
}
