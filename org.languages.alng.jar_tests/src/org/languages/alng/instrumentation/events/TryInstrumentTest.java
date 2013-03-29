package org.languages.alng.instrumentation.events;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;

import util.ALTestConstants;
import util.HybridInterpreterHelper;

public class TryInstrumentTest extends EventInstrumentationBase {

	private String sourceDSLLocation = null;
	
	private String getGeneratedLocation()
	{
		return ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR +"/" + sourceDSLLocation;
	}
	
	private void cleanupGenerated()
	{
		File f = new File(getGeneratedLocation());
		if (f.exists())
		{
			Assert.assertTrue(f.delete());
		}
		Assert.assertFalse(f.exists());
	}
	
	@Before
	public void setup()
	{
		initHI();
		
		// register extract and generate strategies:
		registerExtractFunction("extract-function-info");
		registerGenerateFunction("gen-enter");
		registerGenerateFunction("gen-exit");
	}
	
	@Test
	public void testTryInstrumentVarUse()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/var_use.alng";
		cleanupGenerated();
		
		boolean b = false;
		
		loadDSLDI(ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/FunctionDeclVoid.dsldi");
		loadDSLProgram(this.sourceDSLLocation);
		
		this.setOutputBasePath(ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR);
		this.setLanguageID("ActionLanguage");
		this.setPostInstrumentation("actionlanguage-post-instrumentation");
		//this.addWriteFile("ActionLanguage", "write-actionlanguage-to-file");
		this.addWriteFile("ActionLanguage", "write-actionlanguage-as-dsl");
		//this.addWriteFile("ActionLanguage", "write-actionlanguage-as-ast");
		

		
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		IStrategoTerm tuple = this.getFilenameDSLProgramTuple();
		i.setCurrent(tuple);
		b = HybridInterpreterHelper.safeInvoke(i, "try-instrument");
		Assert.assertTrue(b);
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		
		// compare with /org.languages.alng.jar_tests/alng_scripts/aterm/var_use/var_use_with_enterexit_with_post.aterm
		compareDslProgramSourceWithATermFile("alng_scripts/aterm/var_use/var_use_with_enterexit_with_post.aterm");
	}
	
	@Test
	public void testTryInstrumentJustPrint()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng";
		cleanupGenerated();
		
		boolean b = false;
		
		loadDSLDI(ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/FunctionDeclVoid.dsldi");
		loadDSLProgram(sourceDSLLocation);
		
		this.setOutputBasePath(ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR);
		this.setLanguageID("ActionLanguage");
		this.setPostInstrumentation("actionlanguage-post-instrumentation");
		this.addWriteFile("ActionLanguage", "write-actionlanguage-as-dsl");

		
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		IStrategoTerm tuple = this.getFilenameDSLProgramTuple();
		i.setCurrent(tuple);
		b = HybridInterpreterHelper.safeInvoke(i, "try-instrument");
		Assert.assertTrue(b);
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		
		compareDslProgramSourceWithATermFile("alng_scripts/aterm/justprint/justprint_with_enterexit_with_post.aterm");
		// compare with /org.languages.alng.jar_tests/alng_scripts/aterm/justprint/justprint_with_enterexit_with_post.aterm
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
