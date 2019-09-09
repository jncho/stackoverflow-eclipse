package helpstack.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import helpstack.Activator;
import helpstack.intrase.database.DatabaseIntraSE;
import helpstack.recommendation.database.DatabaseRecommendation;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_SERVER_DR, DatabaseRecommendation.D_HOST);
		store.setDefault(PreferenceConstants.P_PORT_DR, DatabaseRecommendation.D_PORT);
		store.setDefault(PreferenceConstants.P_SERVER_DISE, DatabaseIntraSE.D_HOST);
		store.setDefault(PreferenceConstants.P_PORT_DISE, DatabaseIntraSE.D_PORT);
	}

}
