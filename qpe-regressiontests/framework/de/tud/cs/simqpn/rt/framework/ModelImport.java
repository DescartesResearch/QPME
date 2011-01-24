package de.tud.cs.simqpn.rt.framework;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.tud.cs.simqpn.rt.framework.stats.PlaceStatistics;
import de.tud.cs.simqpn.rt.framework.stats.Statistics;

public class ModelImport {
	
	public void initWithModelStructure(SimulationResults reference, File modelFile) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document doc = documentBuilder.parse(modelFile);
		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		NodeList places = (NodeList)xpath.evaluate("//place", doc, XPathConstants.NODESET);
		for(int i = 0; i < places.getLength(); i++) {
			Element xmlElem = (Element)places.item(i);
			NodeList colorRefs = (NodeList)xpath.evaluate("./color-refs/color-ref", xmlElem, XPathConstants.NODESET);			
			
			String type = xmlElem.getAttribute("type");
			if(type.equals("ordinary-place")) {
				PlaceStatistics elem = new PlaceStatistics(xmlElem.getAttribute("name"), "place");
				
				for(int j = 0; j < colorRefs.getLength(); j++) {
					Element xmlColorRef = (Element)colorRefs.item(j);
					String colorName = (String)xpath.evaluate("//color[@id='" + xmlColorRef.getAttribute("color-id") + "']/@name", doc, XPathConstants.STRING);
					Statistics colorRef = new Statistics(colorName);
					
					elem.addColorStats(colorRef);
				}
				reference.addPlaceStatistics(elem);
			} else if(type.equals("queueing-place")) {
				PlaceStatistics queueElem = new PlaceStatistics(xmlElem.getAttribute("name"), "qplace:queue");
				PlaceStatistics depositoryElem = new PlaceStatistics(xmlElem.getAttribute("name"), "qplace:depository");
				for(int j = 0; j < colorRefs.getLength(); j++) {
					Element xmlColorRef = (Element)colorRefs.item(j);
					String colorName = (String)xpath.evaluate("//color[@id='" + xmlColorRef.getAttribute("color-id") + "']/@name", doc, XPathConstants.STRING);
					Statistics queueColorRef = new Statistics(colorName);
					Statistics depositoryColorRef = new Statistics(colorName);
					
					queueElem.addColorStats(queueColorRef);
					depositoryElem.addColorStats(depositoryColorRef);
				}
				
				reference.addPlaceStatistics(queueElem);
				reference.addPlaceStatistics(depositoryElem);
			}
		}
	}
}
