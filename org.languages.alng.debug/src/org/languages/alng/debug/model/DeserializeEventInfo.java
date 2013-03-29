package org.languages.alng.debug.model;

import java.util.regex.Pattern;

import org.languages.alng.debug.model.events.AlngEnterEvent;
import org.languages.alng.debug.model.events.AlngExitEvent;
import org.languages.alng.debug.model.events.AlngStepEvent;
import org.languages.alng.debug.model.events.AlngVarEvent;
import org.spoofax.debug.interfaces.extractor.IDeserializeEventInfo;
import org.spoofax.debug.interfaces.info.IEventInfo;
import org.spoofax.debug.interfaces.java.DebugEventBase;

/**
 * eventType is language specific
 * @author rlindeman
 *
 */
public class DeserializeEventInfo implements IDeserializeEventInfo {

	public static final String TAB = "\t";
	
	public final static String TAB_IN_REGEX = "\t";
	
	public final static Pattern tabSplit = Pattern.compile(TAB_IN_REGEX);
	
	// in ActionLanguage we only we a single thread. At least for now...
	public final static String THREAD_MAIN = "main";
	
	public IEventInfo deserialize(String eventType, String eventInfoData) {
		if (DebugEventBase.ENTER.equals(eventType)) {
			return enter(eventInfoData);
		} else if (DebugEventBase.EXIT.equals(eventType)) {
			return exit(eventInfoData);
		} else if (DebugEventBase.STEP.equals(eventType)) {
			return step(eventInfoData);
		} else if (DebugEventBase.VAR.equals(eventType)) {
			return var(eventInfoData);
		} else {
			throw new RuntimeException("Not supported event type " + eventType + " with info " + eventInfoData);
		}
	}
	
	private IEventInfo enter(String eventInfoData) {
		String[] tokens = tabSplit.split(eventInfoData);
		if (tokens.length != 3) throw new RuntimeException("Could not deserialize event info: " + eventInfoData);
		String filename = tokens[0];
		String location = tokens[1];
		String functionName = tokens[2];
		return new AlngEnterEvent(THREAD_MAIN, filename, location, functionName);
	}
	
	private IEventInfo exit(String eventInfoData) {
		String[] tokens = tabSplit.split(eventInfoData);
		if (tokens.length != 3) throw new RuntimeException("Could not deserialize event info: " + eventInfoData);
		String filename = tokens[0];
		String location = tokens[1];
		String functionName = tokens[2];
		return new AlngExitEvent(THREAD_MAIN, filename, location, functionName);
	}
	
	private IEventInfo step(String eventInfoData) {
		String[] tokens = tabSplit.split(eventInfoData);
		if (tokens.length != 2) throw new RuntimeException("Could not deserialize event info: " + eventInfoData);
		String filename = tokens[0];
		String location = tokens[1];
		return new AlngStepEvent(THREAD_MAIN, filename, location);

	}
	
	private IEventInfo var(String eventInfoData) {
		String[] tokens = tabSplit.split(eventInfoData);
		if (tokens.length != 3) throw new RuntimeException("Could not deserialize event info: " + eventInfoData);
		String filename = tokens[0];
		String location = tokens[1];
		String varName = tokens[2];
		return new AlngVarEvent(THREAD_MAIN, filename, location, varName);
	}
}
