package org.languages.alng.debug.launching.ui;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

public class AlngTabGroup extends AbstractLaunchConfigurationTabGroup {

//	public AlngTabGroup() {
//		// TODO Auto-generated constructor stub
//	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		setTabs(new ILaunchConfigurationTab[] {
				new AlngMainTab(),
				new SourceLookupTab(), // was sourcelookuptab
				new CommonTab()
		});
	}

}
