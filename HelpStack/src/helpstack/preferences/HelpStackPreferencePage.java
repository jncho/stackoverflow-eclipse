package helpstack.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import helpstack.Activator;
import helpstack.intrase.database.IntraSEProvider;
import helpstack.recommendation.database.RecommendationProvider;

public class HelpStackPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public HelpStackPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("IP and port configuration");
	}

	public void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.P_SERVER_DISE, "SERVER Database Intra Stack Exchange (ISE)", getFieldEditorParent()));
		addField(new IntegerFieldEditor(PreferenceConstants.P_PORT_DISE, "PORT Database Intra Stack Exchange (ISE)", getFieldEditorParent()));
		
		addField(new StringFieldEditor(PreferenceConstants.P_SERVER_DR, "SERVER Database Recommendations", getFieldEditorParent()));
		addField(new IntegerFieldEditor(PreferenceConstants.P_PORT_DR, "PORT Database Recommendations", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {
	}
	
	@Override
	public boolean performOk() {
		super.performOk();
		IntraSEProvider.updateInstance();
		RecommendationProvider.updateInstance();
		return true;
	}
	
	@Override
	protected void performDefaults() {
		IntraSEProvider.updateInstance();
		RecommendationProvider.updateInstance();
		super.performDefaults();
		
	}

}