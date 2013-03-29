package org.languages.alng.instrumentation.events;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;

import util.ALTestConstants;
import util.HybridInterpreterHelper;

/**
 * Test instrumentation of a function with arguments.
 * The var-events are inserted before the function-body.
 * 
 * @author rlindeman
 *
 */
public class FunctionArgsTests extends EventInstrumentationBase {

	private String sourceDSLLocation = null;
	
	private String getGeneratedLocation()
	{
		return ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR +"/" + sourceDSLLocation;
	}
	private String getInstrumentedLocation(String postfix)
	{
		return ALTestConstants.ALNG_SCRIPTS_INSTRUMENTED_DIR +"/" + sourceDSLLocation + postfix;
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
		registerExtractFunction("extract-function-argument-info");
		registerGenerateFunction("gen-function-argument");
		loadDSLDI(ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/FunctionArgs.dsldi");
		this.setOutputBasePath(ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR);
		this.setLanguageID("ActionLanguage");
		
		this.setPostInstrumentation("actionlanguage-post-instrumentation");
		//this.addWriteFile("ActionLanguage", "write-actionlanguage-to-file");
		this.addWriteFile("ActionLanguage", "write-actionlanguage-as-dsl");
		//this.addWriteFile("ActionLanguage", "write-actionlanguage-as-ast");
	}
	
	
	@Test
	public void instrumentNoargs()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/fargs/noargs.alng";
		cleanupGenerated();
		
		loadDSLProgram(sourceDSLLocation);
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng(this.getInstrumentedLocation(".fargvar.alng"));
	}

	@Test
	public void instrumentOnearg()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/fargs/onearg.alng";
		cleanupGenerated();
		
		loadDSLProgram(sourceDSLLocation);
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng(this.getInstrumentedLocation(".fargvar.alng"));
	}
	
	@Test
	public void instrumentTwoargs()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/fargs/twoargs.alng";
		cleanupGenerated();
		
		loadDSLProgram(sourceDSLLocation);
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng(this.getInstrumentedLocation(".fargvar.alng"));
	}
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
