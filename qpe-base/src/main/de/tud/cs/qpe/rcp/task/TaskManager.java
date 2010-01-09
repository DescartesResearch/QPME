/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
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
package de.tud.cs.qpe.rcp.task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.XPath;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.model.DocumentManager;

public class TaskManager implements PropertyChangeListener {
	protected List<Rule> ruleList;
	protected Object taskView;
	
	public TaskManager() {
		DocumentManager.addDocumentRegistrationListener(this);
		ruleList = new ArrayList<Rule>();
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewReference visibleViews[] = activePage.getViewReferences();
		for (int i = 0; i < visibleViews.length; i++) {
			if (IPageLayout.ID_TASK_LIST.equals(visibleViews[i].getId())) {
				taskView = (Object) visibleViews[i].getPart(true);
				break;
			}
		}
	}
	
	protected void addRule(String xPath, int level, String message) {
		Rule rule = new Rule(xPath, level, message);
		ruleList.add(rule);
	}

	protected void updateTasks(Document doc) {
		Iterator<Rule> ruleIterator = ruleList.iterator();
		while(ruleIterator.hasNext()) {
			Rule rule = ruleIterator.next();
			XPath xpath = rule.getXPath();
			Iterator problemNodesIterator = xpath.selectNodes(doc).iterator();
			while(problemNodesIterator.hasNext()) {
				// Stopped work here :)
			}
		}
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		String key = event.getPropertyName();
		if(DocumentManager.PROP_DOCUMENT_REGISTERD.equals(key)) {
			Document doc = (Document) event.getNewValue();
			DocumentManager.addPropertyChangeListener(doc.getRootElement(), this);
		} else if(DocumentManager.PROP_DOCUMENT_UNREGISTERD.equals(key)) {
			Document doc = (Document) event.getNewValue();
			DocumentManager.removePropertyChangeListener(doc.getRootElement(), this);
		} else if(DocumentManager.PROP_DOCUMENT_MODIFIED.equals(key)) {
			Document doc = (Document) event.getOldValue();
			updateTasks(doc);
		}
	}
	
	class Rule {
		public static final int LEVEL_WARNING = 1;
		public static final int LEVEL_ERROR = 2;
		
		protected XPath xPath;
		protected int level;
		protected String message;
		
		public Rule(String xPath, int level, String message) {
			this.xPath = DocumentHelper.createXPath(xPath);
			this.level = level;
			this.message = message;
		}

		public int getLevel() {
			return level;
		}

		public String getMessage() {
			return message;
		}

		public XPath getXPath() {
			return xPath;
		}
	}
}
