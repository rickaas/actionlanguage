package org.languages.alng.runtime;


public class SystemFunctions extends PrintFunctions {

	public static SystemFunctions instance = null;
	
    public static void create()
    {
        instance = new SystemFunctions();
    }
    
	public static void throwStuff(String message)
	{
		throw new RuntimeException(message);
	}
}
