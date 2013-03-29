package org.languages.alng.debug.model.events;

import org.spoofax.debug.core.language.events.AbstractEventInfo;
import org.spoofax.debug.interfaces.info.IVarEventInfo;

public class AlngVarEvent extends AbstractEventInfo implements IVarEventInfo {

	protected String varName;
	
	public AlngVarEvent(String threadName, String filename, String location, String varName) {
		super(threadName, filename, location);
		this.varName = varName;
	}

	@Override
	public String getEventType() {
		return "var";
	}

	@Override
	public String getVarname() {
		return varName;
	}

}
