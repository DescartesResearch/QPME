/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Jürgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2014/03/14  Jürgen Walter     Extracted from NetLoader
 * 
 */
package de.tud.cs.simqpn.kernel.loading.flatten;

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

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.loading.XMLHelper;

/**
 * This class provides a transformation from HQPNs to QPNs.
 */
public class XMLNetFlattener {

	private static Logger log = Logger.getLogger(XMLNetFlattener.class);

	/**
	 * Transforms Hierarchical-QPNs to QPNs. 
	 * 
	 * Should be called after configuration and before
	 * execution of simulation.
	 */
	public static Element flattenHierarchicalNetParts(Element net, String configurationString,
			String statsDir) throws SimQPNException {
		Element result = net;
		XPath xpathSelector = XMLHelper
				.createXPath("//place[@xsi:type = 'subnet-place']");
		if (xpathSelector.selectSingleNode(net) != null) {
			try {
				// There are subnet-places in the net; replace the subnet place
				// by their subnet
				InputStream xslt = XMLNetFlattener.class.getResourceAsStream("/"+XMLNetFlattener.class.getName().replace(".", "/").replace("XMLNetFlattener", "")+"hqpn_transform.xsl");

				TransformerFactory transformFactory = TransformerFactory
						.newInstance();
				Transformer hqpnTransform = transformFactory
						.newTransformer(new StreamSource(xslt));
				DocumentSource source = new DocumentSource(net.getDocument());
				DocumentResult flattenNet = new DocumentResult();
				hqpnTransform.transform(source, flattenNet);
				result = flattenNet.getDocument().getRootElement();
				// Save the transformed net to disk for debug purposes
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd_HHmmssS");
				File f = new File(statsDir, "SimQPN_FlatHQPN_"
						+ configurationString + "_"
						+ dateFormat.format(new Date()) + ".qpe");
				XMLWriter writer = null;
				try {
					writer = new XMLWriter(new FileOutputStream(f),
							OutputFormat.createPrettyPrint());
					writer.write(result.getDocument());
				} catch (IOException e) {
					log.error("", e);
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
