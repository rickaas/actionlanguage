package org.languages.alng.debug.model;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.spoofax.debug.core.eclipse.LIDebugTarget;

import com.sun.jdi.VirtualMachine;

public class AlngDebugTarget extends LIDebugTarget {

	public AlngDebugTarget(String languageID, ILaunch launch, String port) {
		super(languageID, launch, port);
		// TODO Auto-generated constructor stub
	}

	public AlngDebugTarget(IDebugTarget javaTarget, String languageID, ILaunch launch, VirtualMachine vm) {
		super(javaTarget, languageID, launch, vm);
	}
}
