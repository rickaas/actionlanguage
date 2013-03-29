package org.languages.alng.runtime;

import org.languages.alng.runtime.debug.DebugEvents;

public class DebugFunctions {

	public static DebugFunctions instance = null;
	
    public static void create()
    {
        instance = new DebugFunctions();
    }
    
	public static void enter(String filename, String location, String functionName)
	{
		DebugEvents.enter(filename, location, functionName);
	}
	
	public static void exit(String filename, String location, String functionName)
	{
		DebugEvents.exit(filename, location, functionName);
	}
	
	public static void var(String filename, String location, String varName, Object value)
	{
		DebugEvents.var(filename, location, varName, value);
	}
	
	public static void step(String filename, String location)
	{
		DebugEvents.step(filename, location);
	}
}
