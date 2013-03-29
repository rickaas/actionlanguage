package org.languages.alng.instrumentation.programs;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;

import util.ALTestConstants;
import util.HybridInterpreterHelper;

public class TestCase00xTests extends ProgramInstrumentBase {

	@Test
	public void instrument001()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test001_tiny.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test001_tiny.alng");
	}
	
	@Test
	public void instrument002()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test002_assigninblock.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test002_assigninblock.alng");
	}
	
	@Test
	public void instrument003()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test003_declaration.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test003_declaration.alng");
	}
	
	@Test
	public void instrument004()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test004_entitywithmultiplereturn.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng");
	}
	
	@Test
	public void instrument005()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test005_ifblock.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test005_ifblock.alng");
	}
	
	@Test
	public void instrument006()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test006_inentity.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test006_inentity.alng");
	}
	
	@Test
	public void instrument007()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test007_multiplereturn.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test007_multiplereturn.alng");
	}
	
	@Test
	public void instrument008()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test008_statementblock.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test008_statementblock.alng");
	}
	
	@Test
	public void instrument009()
	{
		sourceDSLLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/test009_tryfinally.alng";
		cleanupGenerated();
		
		loadDSLProgram(this.sourceDSLLocation);
		i.setCurrent(this.getFilenameDSLProgramTuple());
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "try-instrument"));
		
		IStrategoTerm succesReport = makeSuccess(this.sourceDSLLocation, this.getGeneratedLocation());
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(this.getGeneratedLocation()).exists());
		compareDslProgramSourceWithAlng("alng_scripts/instrumented/alng_scripts/testcases/programs/test009_tryfinally.alng");
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
