package org.languages.alng.runtime.debug;

import org.spoofax.debug.interfaces.java.FireEvent;
import org.spoofax.debug.interfaces.java.GlobalVarEnvironmentScope;

public class DebugEvents {

	public static void enter(String filename, String location, String functionName) {
		String eventInfo = join(filename, location, functionName);
		GlobalVarEnvironmentScope.get().enterFrame();
		FireEvent.enter(eventInfo);
	}
	
	public static void exit(String filename, String location, String functionName) {
		String eventInfo = join(filename, location, functionName);
		GlobalVarEnvironmentScope.get().exitFrame(); // Or should this be after exit?
		FireEvent.exit(eventInfo);
	}
	
	public static void var(String filename, String location, String varName, Object value) {
		String eventInfo = join(filename, location, varName);
		GlobalVarEnvironmentScope.get().setVar(varName, value);
		FireEvent.var(eventInfo);
	}
	
	public static void step(String filename, String location) {
		String eventInfo = join(filename, location);
		FireEvent.step(eventInfo);
	}
	
	private static String join(String s1, String s2, String s3) {
		String s = s1 + "\t" + s2 + "\t" + s3;
		return s;
	}
	
	private static String join(String s1, String s2) {
		String s = s1 + "\t" + s2;
		return s;
	}
}
