package org.languages.alng.instrumentation.events;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.terms.ParseError;

import util.ALTestConstants;
import util.HybridInterpreterHelper;
import util.alng_scripts.ParsedProgramBase;

public class MultipleEnterExitsTests extends EventInstrumentationBase {
	
	private ParsedProgramBase instrumentedProgram = null;
	
	@Before
	public void setupProgram() throws ParseError, IOException
	{
		
		initHI();
		loadDSLDI(ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/FunctionDeclVoid.dsldi");
		loadDSLProgram(ALTestConstants.ALNG_SCRIPTS_DIR + "/var_use.alng");

		
		// register extract and generate strategies:
		registerExtractFunction("extract-function-info");
		registerGenerateFunction("gen-enter");
		registerGenerateFunction("gen-exit");

	}
	
	@Test
	public void testInstrumentProgramAtermWithPost() throws ParseError, IOException
	{
		boolean b = false;
		this.setPostInstrumentation("actionlanguage-post-instrumentation");
		// parse the expected instrumented version
		instrumentedProgram = ParsedProgramBase.createFromFile(i, "alng_scripts/aterm/var_use/var_use_with_enterexit_with_post.aterm");
		
		
		IStrategoTerm tuple = this.getFilenameDSLProgramTuple();
//		instrument-program-aterm = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(tuple);
		b = HybridInterpreterHelper.safeInvoke(i, "instrument-program-aterm");
		Assert.assertTrue(b);
		Assert.assertEquals(instrumentedProgram.getProgramATerm().toString(), i.current().toString());
	}
	
	@Test
	public void testInstrumentProgramAtermNoPost() throws ParseError, IOException
	{
		boolean b = false;
		// parse the expected instrumented version
		instrumentedProgram = ParsedProgramBase.createFromFile(i, "alng_scripts/aterm/var_use/var_use_with_enterexit_no_post.aterm");
		
		
		IStrategoTerm tuple = this.getFilenameDSLProgramTuple();
//		instrument-program-aterm = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(tuple);
		b = HybridInterpreterHelper.safeInvoke(i, "instrument-program-aterm");
		Assert.assertTrue(b);
		Assert.assertEquals(instrumentedProgram.getProgramATerm().toString(), i.current().toString());
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
