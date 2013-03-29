package org.languages.alng.instrumentation.extract;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.ALTestConstants;
import util.HybridInterpreterHelper;

public class OriginWalk {
	
	private HybridInterpreter i;
	
	protected void initHI()
	{
		// init the HybridInterpreter
		i = HybridInterpreterHelper.createHybridInterpreter();
	}
	
	protected IStrategoTerm makeConfigTuple(String key, String value) {
		return i.getFactory().makeTuple(i.getFactory().makeString(key), i.getFactory().makeString(value));
	}
	
	private String sourceDSLDirectory = null;
	
	private String getGeneratedLocation() {
		return ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR +"/" + sourceDSLDirectory;
	}
	
	private String getInstrumentedLocation() {
		return ALTestConstants.ALNG_SCRIPTS_INSTRUMENTED_DIR +"/" + sourceDSLDirectory;
	}
	
	
	@Test
	public void instrument001()
	{
		sourceDSLDirectory = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs";
		String program = "test001_tiny.alng";
		
		boolean b = false;
		this.initHI();
		
		i.setCurrent(i.getFactory().makeString("foo"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "init-config"));
		
		String dsldi = ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/ActionLanguage.dsldi";
		i.setCurrent(makeConfigTuple("--sel", dsldi));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		i.setCurrent(makeConfigTuple("--file-extension", "alng"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		

		i.setCurrent(makeConfigTuple("--input-dir", sourceDSLDirectory));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		i.setCurrent(makeConfigTuple("--input-file", program));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		String output = getGeneratedLocation() + ".alng";
		i.setCurrent(makeConfigTuple("--output-dir", output));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));

//		i.setCurrent(makeConfigTuple("--fake-run", "true"));
//		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));

		// set-verbosity
		
		// Vomit
		//i.setCurrent(i.getFactory().makeInt(10));
		// verbose-level : Debug()     -> 4
		// Debug
		i.setCurrent(i.getFactory().makeInt(4));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-verbosity"));

		b = HybridInterpreterHelper.safeInvoke(i, "execute");
		i.getCompiledContext().printStackTrace();
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
