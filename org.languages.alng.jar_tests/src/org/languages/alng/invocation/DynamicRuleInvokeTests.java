package org.languages.alng.invocation;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.spoofax.interpreter.stratego.SDefT;
import org.spoofax.interpreter.terms.IStrategoString;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;

public class DynamicRuleInvokeTests {

	private HybridInterpreter i = null;
	
	/**
	 * Test the behavior of dynamic rules when called from Java.
	 */
	@Test
	public void dynamicRuleGET_DSL_PROGRAM_FILENAME()
	{
		i = HybridInterpreterHelper.createHybridInterpreter();
		
		SDefT def = i.lookupUncifiedSVar("GET-DSL-PROGRAM-FILENAME");
		Assert.assertNotNull(def);
		Assert.assertNotNull(i.getContext().getVarScope().lookupSVar("GET_DSL_PROGRAM_FILENAME_0_0"));
		boolean b = HybridInterpreterHelper.safeInvoke(i, "GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertFalse(b);
		Assert.assertNull(i.current());
		
		// GET-DSL-PROGRAM-FILENAME, fails the rule is not set.
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-FILENAME");
		Assert.assertFalse(b);
		Assert.assertNull(i.current());
		
		// all_keys_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "all_keys_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("[]", i.current().toString());
		// bagof_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "bagof_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("[]", i.current().toString());
		//i.getCompiledContext()
		
		i.setCurrent(i.getFactory().makeString("FOOBAR"));
		// GET-DSL-PROGRAM-FILENAME, fails the rule is not set.
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-FILENAME");
		Assert.assertFalse(b);
		Assert.assertNotNull(i.current()); 
		
		// all_keys_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "all_keys_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("[]", i.current().toString());
		// bagof_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "bagof_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("[]", i.current().toString());
		
		// SET-DSL-PROGRAM-FILENAME
		i.setCurrent(i.getFactory().makeString("testing_filename.dsl"));
		b = HybridInterpreterHelper.safeInvoke(i, "SET-DSL-PROGRAM-FILENAME");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		
		// all_keys_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "all_keys_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("[[DR_DUMMY]]", i.current().toString());
		// bagof_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "bagof_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("[\"testing_filename.dsl\"]", i.current().toString());
		
		i.setCurrent(i.getFactory().makeString("FOOBAR"));
		
		// GET-DSL-PROGRAM-FILENAME
		b = HybridInterpreterHelper.safeInvoke(i, "GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertEquals("testing_filename.dsl", ((IStrategoString)i.current()).stringValue());
		
		
		// all_keys_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "all_keys_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("[[DR_DUMMY]]", i.current().toString());
		// bagof_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "bagof_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("[\"testing_filename.dsl\"]", i.current().toString());
		// once_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "once_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("\"testing_filename.dsl\"", i.current().toString());
		
		// do another set
		// SET-DSL-PROGRAM-FILENAME
		i.setCurrent(i.getFactory().makeString("next.dsl"));
		b = HybridInterpreterHelper.safeInvoke(i, "SET-DSL-PROGRAM-FILENAME");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		
		// all_keys_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "all_keys_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("[[DR_DUMMY]]", i.current().toString());
		// bagof_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "bagof_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("[\"next.dsl\"]", i.current().toString());
		// once_GET_DSL_PROGRAM_FILENAME_0_0
		b = HybridInterpreterHelper.safeInvoke(i, "once_GET_DSL_PROGRAM_FILENAME_0_0");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("\"next.dsl\"", i.current().toString());
		b = HybridInterpreterHelper.safeInvoke(i, "once-GET-DSL-PROGRAM-FILENAME");
		Assert.assertFalse(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals("\"next.dsl\"", i.current().toString());
	}
	
	/*
	 * 
	// current term should be a program-fragment aterm
	try-extract(|extract-name) = 
		?aterm;
		if java-exists-extract-strategy(|extract-name) then
			//debug ;
			<java-call-extract-strategy(|extract-name)> aterm // returns DEBUG-INFORMATION
			; SET-CURRENT-DEBUG-INFORMATION; !aterm
			<+ failed-extract(|extract-name); fail
		else 
			// could not find extract transformation
			<debug(!"Could not find extract transformation: ")> extract-name
			; fail
			end
	 */
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
