package org.languages.alng.instrumentation.programs;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;

import util.ALTestConstants;
import util.HybridInterpreterHelper;

public class TestCase01xTests extends ProgramInstrumentBase {

	@Test
	public void instrument010()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test010_whileblock.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test010_whileblock.alng");
	}
	
	@Test
	public void instrument011()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test011_returnwithexpression.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test011_returnwithexpression.alng");
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
