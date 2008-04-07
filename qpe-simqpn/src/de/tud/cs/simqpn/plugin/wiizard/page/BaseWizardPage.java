package de.tud.cs.simqpn.plugin.wiizard.page;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;

import de.tud.cs.qpe.model.DocumentManager;

public abstract class BaseWizardPage extends WizardPage implements PropertyChangeListener {
	protected Element net;

	protected PropertyChangeSupport pcs;
	
	public BaseWizardPage(String pageName, ISelection selection, Element net) {
		super(pageName);
		this.net = net;
		pcs = new PropertyChangeSupport(this);
	}

	protected Element getMetaAttribute() {
		Element metaAttributeContainer = net.element("meta-attributes");
		if (metaAttributeContainer == null) {
			metaAttributeContainer = net.addElement("meta-attributes");
		}

		String activeConfiguration = Page1ConfigurationSelectionWizardPage.activeConfiguration;
		
		XPath xpathSelector = DocumentHelper.createXPath("meta-attribute[@name = 'sim-qpn' and @configuration-name='" + activeConfiguration + "']");
		Element mataAttribute = (Element) xpathSelector.selectSingleNode(metaAttributeContainer);
		return mataAttribute;
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

				XPath xpathSelector = DocumentHelper.createXPath("meta-attribute[@name = 'sim-qpn' and @configuration-name='" + configruationName + "']");
				metaAttribute = (Element) xpathSelector.selectSingleNode(metaAttributeContainer);
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


	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
	
	public void firePropertyChanged(String key) {
		pcs.firePropertyChange(key, null, null);
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		String key = event.getPropertyName();
		if(Page1ConfigurationSelectionWizardPage.PROP_CONFIGURATION_CHANGED.equals(key)) {
			updateDialog();	
		}
	}
	
	protected abstract void updateDialog();
}
