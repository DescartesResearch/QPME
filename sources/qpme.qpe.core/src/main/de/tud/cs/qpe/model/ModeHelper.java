package de.tud.cs.qpe.model;

import java.util.List;

import org.dom4j.Element;

public class ModeHelper extends XPathHelper {
	
	public static List<Element> listIncomingConnections(Element mode) {
		return queryElements(mode, "../../connections/connection[@target-id = '"
				+ mode.attributeValue("id") + "']");
	}
	
	public static List<Element> listOutgoingConnections(Element mode) {
		return queryElements(mode, "../../connections/connection[@source-id = '"
				+ mode.attributeValue("id") + "']");
	}

}
