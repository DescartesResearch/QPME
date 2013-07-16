package de.tud.cs.simqpn.kernel.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class NetFlattener {
	
	private static Logger log = Logger.getLogger(NetFlattener.class);

	/**
	 * Prepares the net for simulation. Should be called after Simulator.configure and
	 * before Simulator.execute
	 */
	public static Element prepareNet(Element net, String configurationString, String statsDir) throws SimQPNException {
		Element result = net;
		XPath xpathSelector = XMLHelper.createXPath("//place[@xsi:type = 'subnet-place']");
		if(xpathSelector.selectSingleNode(net) != null) {
			try {
				// There are subnet-places in the net; replace the subnet place by their subnet
				InputStream xslt = SimQPNController.class.getResourceAsStream("/de/tud/cs/simqpn/transforms/hqpn_transform.xsl");
				TransformerFactory transformFactory = TransformerFactory.newInstance();
				Transformer hqpnTransform = transformFactory.newTransformer(new StreamSource(xslt));
				DocumentSource source = new DocumentSource(net.getDocument());
				DocumentResult flattenNet = new DocumentResult();
				hqpnTransform.transform(source, flattenNet);
				result = flattenNet.getDocument().getRootElement();
				// Save the transformed net to disk for debug purposes
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmssS");
				File f = new File(statsDir, "SimQPN_FlatHQPN_" + configurationString + "_" + dateFormat.format(new Date()) + ".qpe");
				XMLWriter writer = null;
				try {
					writer = new XMLWriter(new FileOutputStream(f), OutputFormat.createPrettyPrint());
					writer.write(result.getDocument());
				} catch (IOException e) {
					log.error("",e);
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
						}
					}
				}
			} catch (Exception e) {
				log.error("Could not merge subnets into a flattened net.", e);
				throw new SimQPNException();
			}
		}			
		return result;
	}

}
