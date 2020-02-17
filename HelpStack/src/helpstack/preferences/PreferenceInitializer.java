package helpstack.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import helpstack.Activator;
import helpstack.intrase.database.IntraSEProvider;
import helpstack.recommendation.database.RecommendationProvider;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_SERVER_DR, RecommendationProvider.D_HOST);
		store.setDefault(PreferenceConstants.P_PORT_DR, RecommendationProvider.D_PORT);
		store.setDefault(PreferenceConstants.P_SERVER_DISE, IntraSEProvider.D_HOST);
		store.setDefault(PreferenceConstants.P_PORT_DISE, IntraSEProvider.D_PORT);
	}

}
