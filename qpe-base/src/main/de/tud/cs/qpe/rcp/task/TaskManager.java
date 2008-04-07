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
