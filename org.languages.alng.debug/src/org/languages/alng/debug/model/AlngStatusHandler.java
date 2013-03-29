package org.languages.alng.debug.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.ui.statushandlers.StatusManager;

public class AlngStatusHandler implements IStatusHandler {

	@Override
	public Object handleStatus(IStatus status, Object source)
			throws CoreException {
		StatusManager.getManager().handle(status, StatusManager.SHOW | StatusManager.LOG);
		return null;
	}

}
