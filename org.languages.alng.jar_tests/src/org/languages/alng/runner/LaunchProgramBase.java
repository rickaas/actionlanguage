package org.languages.alng.runner;

import org.junit.After;
import org.junit.Before;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;

public class LaunchProgramBase {

	protected HybridInterpreter i = null;
	
	@Before
	public void setup()
	{
		initHI();
		
		PluginPathOverride.override(); // when this test is not executed in a plugin, the plugin path strategy fails so override it
	}
	
	@After
	public void tearDown()
	{
		PluginPathOverride.undo(); // reset the override
	}
	
	protected void initHI()
	{
		// init the HybridInterpreter
		i = HybridInterpreterHelper.createHybridInterpreter();
	}
	
}
