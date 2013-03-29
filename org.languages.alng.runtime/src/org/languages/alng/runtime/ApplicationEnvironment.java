package org.languages.alng.runtime;

public class ApplicationEnvironment {

	public static void init()
	{
		SystemFunctions.create();
		DebugFunctions.create();
	}
}
