package org.languages.alng.debug.model;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.languages.alng.debug.ActionLanguageConstants;
import org.spoofax.debug.core.eclipse.LILineBreakpoint;

public class AlngLineBreakpoint extends LILineBreakpoint {

	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public AlngLineBreakpoint() {
	}
	
	public AlngLineBreakpoint(final IResource resource, final int lineNumber) throws CoreException {
		super(resource, lineNumber);
	}
	@Override
	public String getModelIdentifier() {
		return ActionLanguageConstants.ID_ALNG_DEBUG_MODEL;
	}

	@Override
	public String getLineBreakpointMarkerID() {
		return ActionLanguageConstants.ALNG_LINEBREAKPOINT_MARKER;
	}

}
