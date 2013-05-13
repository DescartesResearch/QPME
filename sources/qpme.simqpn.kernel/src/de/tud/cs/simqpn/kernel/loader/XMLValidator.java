package de.tud.cs.simqpn.kernel.loader;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNException;

public class XMLValidator {
	
	private static Logger log = Logger.getLogger(XMLValidator.class);

	public static void validateInputNet(Element net) throws SimQPNException {
		XPath xpathSelector = XMLHelper.createXPath("//color-ref[@maximum-capacity > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Max population attribute currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}		
		xpathSelector = XMLHelper.createXPath("//color-ref[@ranking > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Ranking attribute currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}
		xpathSelector = XMLHelper.createXPath("//color-ref[@priority > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Priority attribute currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}
		xpathSelector = XMLHelper.createXPath("//transition[@priority > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Transition priorities currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}
	}


}
