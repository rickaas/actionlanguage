package org.languages.alng.invocation;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.spoofax.interpreter.library.IOperatorRegistry;
import org.strategoxt.HybridInterpreter;

import util.ALTestConstants;
import util.HybridInterpreterHelper;

public class SpoofaxLibraryTests {

	private HybridInterpreter i;
	
	@Test
	public void testIMPLibraryLoaded() throws Exception {
		i = null;
		i = HybridInterpreterHelper.createHybridInterpreter();
		// RL: IMPLibrary is loaded via: result.addOperatorRegistry(new IMPLibrary());
		//i.getContext().getOperatorRegistry(IMPLibrary.REGISTRY_NAME); // sdf2imp
		IOperatorRegistry iOp = i.getContext().getOperatorRegistry("sdf2imp"); // sdf2imp
		Assert.assertNull(iOp); // Interpreter was not created via org.strategoxt.imp.runtime.Environment (which is a plugin)
		//String name = iOp.getOperatorRegistryName();
		//System.out.println("NAME: " + name);
	}
	
//	//@Test
//	public void createWithEnvironment()
//	{
//		// fails because we are not a plugin
//		org.strategoxt.imp.runtime.Environment.createInterpreter();
//	}
//	
//	@Test
//	public void testPluginPathOverride()
//	{
//		PluginPathOverride.override();
//		
//		HybridInterpreter i = createHybridInterpreter();
//		i.addOperatorRegistry(new IMPLibrary());
//		
//		IOperatorRegistry iOp = i.getContext().getOperatorRegistry("sdf2imp"); // sdf2imp
//		String name = iOp.getOperatorRegistryName();
//		System.out.println("NAME: " + name);
//		
//		Assert.assertTrue(PluginPathOverride.isOverridden());
//		// plugin-path
//		i.setCurrent(i.getFactory().makeString("foo"));
//		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "plugin-path"));
//		System.out.println(i.current().toString());
//		
//		PluginPathOverride.undo();
//	}
//	
//	@Test
//	public void loadIMPLibrary()
//	{
//		HybridInterpreter i = createHybridInterpreter();
//		i.addOperatorRegistry(new IMPLibrary());
//		
//		IOperatorRegistry iOp = i.getContext().getOperatorRegistry("sdf2imp"); // sdf2imp
//		String name = iOp.getOperatorRegistryName();
//		System.out.println("NAME: " + name);
//		
//		Assert.assertFalse(PluginPathOverride.isOverridden()); // should not be overridden
//		// plugin-path
//		i.setCurrent(i.getFactory().makeString("foo"));
//		Assert.assertFalse(HybridInterpreterHelper.safeInvoke(i, "plugin-path")); // cannot run because we are not a plugin
//		System.out.println(i.current().toString());
//	}
	
	/**
	 * Create a new HybridInterpreter with actionlanguage.jar and libdsldi.jar loaded.
	 * @return
	 */
	protected HybridInterpreter createHybridInterpreter()
	{
		// init the Context
		//org.strategoxt.lang.Context context = trans.Main.init(); // not needed
		i = new HybridInterpreter();
		i.init();
		// Load the Jars.

		// language independent instrumentation
		HybridInterpreterHelper.safeLoadJar(i, ALTestConstants.LIBDSLDI_JAR);
		// also load dsldi-java because it contains strategies implemented in java
		// TODO: move the java strategies from dsldi-java.jar to lidsldi-java.jar
		HybridInterpreterHelper.safeLoadJar(i, ALTestConstants.DSLDI_JAVA_JAR);
		// language specific strategies
		HybridInterpreterHelper.safeLoadJar(i, ALTestConstants.ACTIONLANGUAGE_JAR);

		return i;
	}
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
