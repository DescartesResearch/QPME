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
�* http://www.eclipse.org/legal/epl-v10.html
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
 * Original Author(s):  Samuel Kounev and Christofer Dutz
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2006        Christofer Dutz   Created.
 * 
 */
package de.tud.cs.simqpn.ui.wizard.page;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.simqpn.ui.model.Configuration;

public abstract class BaseWizardPage extends WizardPage {
	protected Element net;
	
	protected Configuration currentConfiguration;
	
	public BaseWizardPage(String pageName, ISelection selection, Element net) {
		super(pageName);
		this.net = net;
	}

	protected Element getMetaAttribute() {
		Element metaAttributeContainer = net.element("meta-attributes");
		if (metaAttributeContainer == null) {
			metaAttributeContainer = net.addElement("meta-attributes");
		}

		//String activeConfiguration = Page1ConfigurationSelectionWizardPage.activeConfiguration;
		
		//XPath xpathSelector = DocumentHelper.createXPath("meta-attribute[@name = 'sim-qpn' and @configuration-name='" + activeConfiguration + "']");
//		Element mataAttribute = (Element) xpathSelector.selectSingleNode(metaAttributeContainer);
//		return mataAttribute;
		return null;
	}
	
	protected Element getMetaAttribute(Element parent) {
		Element metaAttribute = null;

		Element configuration = getMetaAttribute();
		if(configuration != null) {
			// Get the name of the active configuration.
			String configruationName = configuration.attributeValue("configuration-name");

			// Try to get a meta-attribute of the current element
			// with that configuration name or create a new one.
			if (configruationName != null) {
				Element metaAttributeContainer = parent.element("meta-attributes");
				if (metaAttributeContainer == null) {
					metaAttributeContainer = parent.addElement("meta-attributes");
				}

				metaAttribute = NetHelper.getMetadata(parent, configruationName);
				if (metaAttribute == null) {
					metaAttribute = new DefaultElement("meta-attribute");
					metaAttribute.addAttribute("name", "sim-qpn");
					metaAttribute.addAttribute("configuration-name", configruationName);
					DocumentManager.addChild(metaAttributeContainer, metaAttribute);
				}
			}
		}
		return metaAttribute;
	}
		
	protected abstract void updateDialog();
}
