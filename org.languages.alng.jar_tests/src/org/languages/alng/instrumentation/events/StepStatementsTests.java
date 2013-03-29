package org.languages.alng.instrumentation.events;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;

import junit.framework.Assert;
import util.ALTestConstants;
import util.HybridInterpreterHelper;

public class StepStatementsTests extends EventInstrumentationBase {

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
		registerExtractFunction("extract-statement-info");
		registerGenerateFunction("gen-step");
		loadDSLDI(ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/Statements.dsldi");
		this.setOutputBasePath(ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR);
		this.setLanguageID("ActionLanguage");
		
		this.setPostInstrumentation("actionlanguage-post-instrumentation");
		//this.addWriteFile("ActionLanguage", "write-actionlanguage-to-file");
		this.addWriteFile("ActionLanguage", "write-actionlanguage-as-dsl");
		//this.addWriteFile("ActionLanguage", "write-actionlanguage-as-ast");
	}
	
	
	@Test
	public void instrumentJustPrint()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng";
		cleanupGenerated();
		
		loadDSLProgram(sourceDSLLocation);
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng(this.getInstrumentedLocation(".step.alng"));
	}
	
	@Test
	public void instrumentVarUse()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/var_use.alng";
		cleanupGenerated();
		
		loadDSLProgram(sourceDSLLocation);
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng(this.getInstrumentedLocation(".step.alng"));
	}
	
	@Test
	public void instrumentIfblock()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/statements/ifblock.alng";
		cleanupGenerated();
		

		loadDSLProgram(this.sourceDSLLocation);
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng(this.getInstrumentedLocation(".step.alng"));
	}
	
	@Test
	public void instrumentInEntity()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/statements/inentity.alng";
		cleanupGenerated();
		

		loadDSLProgram(this.sourceDSLLocation);
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng(this.getInstrumentedLocation(".step.alng"));
	}
	
	@Test
	public void instrumentTryFinally()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/statements/tryfinally.alng";
		cleanupGenerated();
		

		loadDSLProgram(this.sourceDSLLocation);
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng(this.getInstrumentedLocation(".step.alng"));
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
