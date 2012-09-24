package de.tud.cs.qpe.model;

import javax.xml.XMLConstants;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.tree.DefaultElement;

public class ColorReferenceHelper extends XPathHelper {
	
	public static Element getPlace(Element colorRef) {
		return element(colorRef, "../..");
	}
	
	public static Element createSimqpnBatchMeansColoRefConfigurationMetadata(Element colorRef, String configName) {
		if (PlaceHelper.isQueueingPlace(getPlace(colorRef))) {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-batch-means-queueing-color-configuration");
		} else {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-batch-means-color-configuration");
		}
	}
	
	public static Element createSimqpnReplDelColoRefConfigurationMetadata(Element colorRef, String configName) {
		if (PlaceHelper.isQueueingPlace(getPlace(colorRef))) {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-replication-deletion-queueing-color-configuration");
		} else {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-replication-deletion-color-configuration");
		}
	}
	
	public static Element createSimqpnWelchColoRefConfigurationMetadata(Element colorRef, String configName) {
		if (PlaceHelper.isQueueingPlace(getPlace(colorRef))) {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-welch-queueing-color-configuration");
		} else {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-welch-color-configuration");
		}
	}
	
	private static Element createSimqpnColoRefConfigurationMetadata(Element colorRef, String configName, String type) {
		Element metaAttributeContainer = colorRef.element("meta-attributes"); 
		if(metaAttributeContainer == null) {
			metaAttributeContainer = colorRef.addElement("meta-attributes");
		}

		Element metaAttribute = new DefaultElement("meta-attribute");
		metaAttribute.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), type);
		metaAttribute.addAttribute("configuration-name", configName);
		
		DocumentManager.addChild(metaAttributeContainer, metaAttribute);
		return metaAttribute;
	}

}
