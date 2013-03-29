package org.languages.alng.debug.launching;

import org.languages.alng.debug.ActionLanguageConstants;
import org.spoofax.debug.core.launching.LILaunchShortCut;

public class AlngLaunchShortcut extends LILaunchShortCut {



	public void searchAndLaunch(Object[] search, String mode) {
		// TODO: implement!
        /*
    	IType[] types = null;
        if (search != null) {
            try {
             types = AppletLaunchConfigurationUtils.findApplets(
                     new ProgressMonitorDialog(getShell()), search);
            } catch (Exception e) {
               
            }
            IType type = null;
         if (types.length == 0) {
                MessageDialog.openInformation(
                    getShell(), "Applet Launch", "No applets found."};
         } else if (types.length > 1) {
                type = chooseType(types, mode);
         } else {
                type = types[0];
            }
            if (type != null) {
             launch(type, mode);
            }
        }
        */
    }
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.JavaLaunchShortcut#getTypeSelectionTitle()
	 */
	protected String getTypeSelectionTitle() {
		return "Select ActionLanguage Application"; //LauncherMessages.JavaApplicationLaunchShortcut_0;
	}

	public String getLaunchConfigurationTypeID() {
		return ActionLanguageConstants.ALNG_LAUNCH_CONFIG_TYPE;
	}
	public String getProgramAttribute() {
		return ActionLanguageConstants.ATTR_ALNG_PROGRAM;
	}
}
