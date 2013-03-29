package org.languages.alng.runner;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import util.ALTestConstants;
import util.HybridInterpreterHelper;


public class LaunchProgramTests extends LaunchProgramBase {

	@Test
	public void run001()
	{
		String alngLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test001_tiny.alng";
		i.setCurrent(i.getFactory().makeString(alngLocation));
		
		// current term should be the path to an Alng program
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "run-al-program"));
		Assert.assertEquals("\"Hello World!\\nHello Foobar!\\n\"", i.current().toString());
	}
	
	@Test
	public void debug001()
	{
		String alngLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test001_tiny.alng";
		i.setCurrent(i.getFactory().makeString(alngLocation));
		
		// current term should be the path to an Alng program
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "debug-al-program"));
		Assert.assertEquals("\"Hello World!\\nHello Foobar!\\n\"", i.current().toString());
	}
	
	@Test
	public void run002()
	{
		String alngLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test002_assigninblock.alng";
		i.setCurrent(i.getFactory().makeString(alngLocation));
		
		// current term should be the path to an Alng program
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "run-al-program"));
		Assert.assertEquals("\"b\\ntrue\\n\"", i.current().toString());
	}
	
	@Test
	public void debug002()
	{
		String alngLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test002_assigninblock.alng";
		i.setCurrent(i.getFactory().makeString(alngLocation));
		
		// current term should be the path to an Alng program
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "debug-al-program"));
		Assert.assertEquals("\"b\\ntrue\\n\"", i.current().toString());
	}
	
	@Test
	public void debug004()
	{
		String alngLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test004_entitywithmultiplereturn.alng";
		i.setCurrent(i.getFactory().makeString(alngLocation));
		
		// current term should be the path to an Alng program
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "debug-al-program"));
		Assert.assertEquals("\"new user\\n\"", i.current().toString());
	}
	
	@Test
	public void debug012()
	{
		String alngLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test012_functionwitharguments.alng";
		i.setCurrent(i.getFactory().makeString(alngLocation));
		
		// current term should be the path to an Alng program
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "debug-al-program"));
		Assert.assertEquals("\"i=6\\nk=1152\\n\"", i.current().toString());
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
