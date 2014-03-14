package de.tud.cs.simqpn.kernel.loading;

import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

public class XMLHelper {
	private static Map<String, String> namespaceUris = new HashMap<String, String>();	
	static {
		namespaceUris.put("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
	}

	public static XPath createXPath(String xpath) {
		XPath selector = DocumentHelper.createXPath(xpath);
		selector.setNamespaceURIs(namespaceUris);
		return selector;
	}
	
	public static Element getSettings(Element element, String configuration) {
		XPath xpathSelector = XMLHelper.createXPath("meta-attributes/meta-attribute[starts-with(@xsi:type, 'simqpn') and @configuration-name='" + configuration + "']");
		Element elementSettings = (Element) xpathSelector.selectSingleNode(element);
		return elementSettings;
	}
	
	public static Element getElement(Element element, String configurationName) {
		XPath xpathSelector = XMLHelper
				.createXPath("meta-attributes/meta-attribute[starts-with(@xsi:type, 'simqpn') and @configuration-name='"
						+ configurationName + "']");
		Element elementSettings = (Element) xpathSelector
				.selectSingleNode(element);
		return elementSettings;
	}
}
