package org.languages.alng.debug.control;

import org.eclipse.debug.core.ILaunch;
import org.languages.alng.debug.ActionLanguageConstants;
import org.languages.alng.debug.model.AlngDebugTarget;
import org.spoofax.debug.core.control.java.VMContainer;
import org.spoofax.debug.core.language.IDebugServiceFactory;
import org.spoofax.debug.core.language.LIConstants;

import com.sun.jdi.VirtualMachine;

public class AlngDebugServiceFactory implements IDebugServiceFactory {

	@Override
	public VMContainer createVMContainer(VirtualMachine vm) {
		return new AlngVMContainer(vm);
	}
	
	@Override
	public AlngDebugTarget createDebugTarget(ILaunch launch, String port) {
		AlngDebugTarget target = new AlngDebugTarget(getLanguageID(), launch, port);
		return target;
	}
	
	@Override
	public LIConstants getLIConstants() {
		return new ActionLanguageConstants.AlngAttributes();
	}

	public static String getLanguageID() {
		String languageID = org.languages.alng.Activator.getInstance().getLanguageID();
		return languageID;
	}
}
