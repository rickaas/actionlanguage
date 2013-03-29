package org.languages.alng.invocation;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.spoofax.interpreter.core.InterpreterException;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;

public class RegisterStrategiesForInstrumentation {

	private HybridInterpreter i;
	
	/**
	 * We register extract/generation strategies by name so we can call them by their name.
	 * @throws InterpreterException
	 */
	@Test
	public void testRegisterExtractStrategy() throws InterpreterException
	{
		i = HybridInterpreterHelper.createHybridInterpreter();
		
		// extract-function-info is DSL-specific
		// we loaded actionlanguage.jar in the Interpreter so it is available
		String extractStrategy = "extract-function-info";
		Assert.assertNotNull(i.lookupUncifiedSVar(extractStrategy));
		
		
//	    // current term is the matched term
//	    // return debug-information.
//	    ext-call-extract-strategy(|strategy-name) = java-call-extract-strategy(|strategy-name)
//	    
//	    // ignore current term
//	    ext-register-extract-strategy(extract-strategy|strategy-name) = java-register-extract-strategy(extract-strategy|strategy-name)
//
//	    // fails if no extract strategy was found with the given name
//	    ext-exists-extract-strategy(|strategy-name) = java-exists-extract-strategy(|strategy-name)
		
//		// current term is the matched term
//		// return debug-information.
//		external java-call-extract-strategy(|strategy-name)
//		
//		// ignore current term
//		external java-register-extract-strategy(extract-strategy|strategy-name)
//
//		// fails if no extract strategy was found with the given name
//		external java-exists-extract-strategy(|strategy-name)
		
		Assert.assertNotNull(i.lookupUncifiedSVar("ext_call_extract_strategy_0_1"));
		Assert.assertNotNull(i.lookupUncifiedSVar("ext_register_extract_strategy_1_1"));
		Assert.assertNotNull(i.lookupUncifiedSVar("ext_exists_extract_strategy_0_1"));
		
		Assert.assertNotNull(i.lookupUncifiedSVar("java_call_extract_strategy_0_1"));
		Assert.assertNotNull(i.lookupUncifiedSVar("java_register_extract_strategy_1_1"));
		Assert.assertNotNull(i.lookupUncifiedSVar("java_exists_extract_strategy_0_1"));
		// java-exists-extract-strategy(|extract-name)
		
		Assert.assertEquals(1, i.lookupUncifiedSVar("ext_exists_extract_strategy_0_1").getTermParams().length);
		Assert.assertEquals("t0", i.lookupUncifiedSVar("ext_exists_extract_strategy_0_1").getTermParams()[0]);
		
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(extractStrategy));
		boolean b = HybridInterpreterHelper.safeInvoke(i, "ext_exists_extract_strategy_0_1");
		Assert.assertFalse(b); // Could not find extract transformation: extract-function-info
		i.getContext().getVarScope().removeVar("t0");
		
		// check the strategy signature
		Assert.assertEquals(1, i.lookupUncifiedSVar("ext_register_extract_strategy_1_1").getTermParams().length);
		Assert.assertEquals(1, i.lookupUncifiedSVar("ext_register_extract_strategy_1_1").getStrategyParams().length);
		Assert.assertEquals("t0", i.lookupUncifiedSVar("ext_register_extract_strategy_1_1").getTermParams()[0]);
		Assert.assertEquals("s0", i.lookupUncifiedSVar("ext_register_extract_strategy_1_1").getStrategyParams()[0].name);
		
		// call ext-register-extract-strategy(extract-strategy|strategy-name)
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(extractStrategy));
		i.getContext().getVarScope().addSVar("s0", i.lookupUncifiedSVar(extractStrategy));
		b = HybridInterpreterHelper.safeInvoke(i, "ext_register_extract_strategy_1_1");
		Assert.assertTrue(b);
		i.getContext().getVarScope().removeVar("t0");
		i.getContext().getVarScope().removeSVar("s0");

		// it should now exist
		i.getContext().getVarScope().add("t0", i.getFactory().makeString(extractStrategy));
		b = HybridInterpreterHelper.safeInvoke(i, "ext_exists_extract_strategy_0_1");
		Assert.assertTrue(b); // Found extract transformation: extract-function-info
		i.getContext().getVarScope().removeVar("t0");


	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
