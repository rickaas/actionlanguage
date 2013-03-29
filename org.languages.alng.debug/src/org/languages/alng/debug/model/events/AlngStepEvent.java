package org.languages.alng.debug.model.events;

import org.spoofax.debug.core.language.events.AbstractEventInfo;

public class AlngStepEvent extends AbstractEventInfo {

	public AlngStepEvent(String threadName, String filename, String location) {
		super(threadName, filename, location);
	}

	@Override
	public String getEventType() {
		return "step";
	}

}
