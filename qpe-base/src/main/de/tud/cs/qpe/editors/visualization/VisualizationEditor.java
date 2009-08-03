package de.tud.cs.qpe.editors.visualization;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;


public class VisualizationEditor extends EditorPart {

	public static final String ID = "de.tud.cs.qpe.editors.visualization";

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof VisualizationEditorInput)) {
			throw new PartInitException("Invalid Input: Must be VisualizationEditorInput");
		}

		setSite(site);
		setInput(input);
	}

	public void createPartControl(Composite parent) {
		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		Composite composite = new Composite(scroll, SWT.NONE);
		RowLayout layout = new RowLayout();
		layout.marginTop = 20;
		layout.marginLeft = 20;
		layout.spacing = 20;
		composite.setLayout(layout);
		for (ChartGroup chartGroup : getChartGroups()) {
			for (SizedVisualizationComponent visu : chartGroup) {
				Composite c = visu.getVisualizationComponent().realize(composite);
				c.setLayoutData(new RowData(visu.getSize().width, visu.getSize().height));
			}
		}
		scroll.setContent(composite);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		scroll.setMinSize(composite.computeSize(parent.getSize().x, SWT.DEFAULT));
	}

//	public void createPartControl(Composite parent) {
//		Font font = new Font(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay(), new FontData("Arial", 12, SWT.BOLD));
//		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
//		Composite composite = new Composite(scroll, SWT.NONE);
//		RowLayout layout = new RowLayout(SWT.VERTICAL);
//		layout.marginTop = 20;
//		layout.marginLeft = 20;
//		layout.spacing = 20;
//		layout.fill = true;
//		composite.setLayout(layout);
//		for (ChartGroup chartGroup : getChartGroups()) {
//			Label label = new Label(composite, SWT.NONE);
//			label.setText(chartGroup.getTitle());
//			label.setFont(font);
//			Composite groupComposite = new Composite(composite, SWT.NONE);
//			RowLayout groupLayout = new RowLayout(SWT.HORIZONTAL);
//			groupLayout.spacing = 20;
//			groupLayout.wrap = true;
//			groupComposite.setLayout(groupLayout);
//			for (SizedVisualizationComponent visu : chartGroup) {
//				Composite c = visu.getVisualizationComponent().realize(groupComposite);
//				c.setLayoutData(new RowData(visu.getSize().width, visu.getSize().height));
//			}
//		}
//		scroll.setContent(composite);
//		scroll.setExpandHorizontal(true);
//		scroll.setExpandVertical(true);
//		scroll.setMinSize(composite.computeSize(parent.getSize().x, SWT.DEFAULT));
//	}

	public void setFocus() {
		// do nothing
	}


	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	
	private List<ChartGroup> getChartGroups() {
		return ((VisualizationEditorInput) getEditorInput()).getChartGroups(); 
	}
}
