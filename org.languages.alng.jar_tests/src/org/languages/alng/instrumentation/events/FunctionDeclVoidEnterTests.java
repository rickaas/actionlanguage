package org.languages.alng.instrumentation.events;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.terms.ParseError;

import util.ALTestConstants;
import util.HybridInterpreterHelper;
import util.SELUtil;
import util.alng_scripts.JustPrint;

public class FunctionDeclVoidEnterTests extends EventInstrumentationBase {
	
	private JustPrint program = null;
	
	private JustPrint instrumentedProgram = null;
	
	@Before
	public void setupProgram() throws ParseError, IOException
	{
		boolean b = false;
		
		initHI();
		loadDSLDI(ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/FunctionDeclVoidEnter.dsldi");
		loadDSLProgram(ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng");

		// keep a reference to the original source and the expected instrumented version
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// parse justprint.alng
		program = new JustPrint();
		program.init(getOriginalDSLProgramSource());
		// parse the expected instrumented version
		instrumentedProgram = new JustPrint();
		instrumentedProgram.initFromFile(i, "alng_scripts/aterm/justprint/justprint_with_enter_no_post.aterm");
		
		// register extract and generate strategies:
		registerExtractFunction("extract-function-info");
		registerGenerateFunction("gen-enter");

	}
	
	/**
	 * Load justprint.alng, load the dlsdi definition and try to instrument a FunctionDeclVoid
	 * @throws IOException 
	 * @throws ParseError 
	 */
	@Test
	public void testInstrumentSpecificAterm() throws ParseError, IOException
	{
		boolean b = false;
		
		// call: instrument-specific-aterm(|extract-name, generate-name) =  ?aterm
		// It returns the debug-instrumented aterm
		i.getContext().getVarScope().add("t0", i.getFactory().makeString("extract-function-info"));
		i.getContext().getVarScope().add("t1", i.getFactory().makeString("gen-enter"));
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "instrument_specific_aterm_0_2");
		Assert.assertTrue(b);

		Assert.assertEquals(instrumentedProgram.getFunctionDeclVoid().toString(), i.current().toString());
		
		// attachments are lost...
		Assert.assertEquals(null, SELUtil.getSort(i.current()));
		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(i.current()));
		
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertFalse(b);
	}
	
	@Test
	public void testHasBeenMatched()
	{
		boolean b = false;
		
//		// current is matched against the pattern in link, now execute the extract and generation transformations specified in link
//		// current-term is term
//		// output is the debug instrumented term
//		has-been-matched(|link) = ?term
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-SEL-DEFINITION");
		Assert.assertTrue(b);
		
		// get the first sel definition
		i.getContext().getVarScope().add("t0", i.getFactory().makeInt(1));
		b = HybridInterpreterHelper.safeInvoke(i, "get_sel_link_at_index_0_1");
		Assert.assertTrue(b);
		IStrategoTerm selLinkEnter = i.current();
		
		i.getContext().getVarScope().add("t0", selLinkEnter);
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "has_been_matched_0_1");
		Assert.assertTrue(b);
		
		IStrategoTerm current = i.current();
		Assert.assertEquals(instrumentedProgram.getFunctionDeclVoid().toString(), current.toString());
		
		// attachments are lost...
		//Assert.assertEquals("FunctionDecl", SELUtil.getSort(current));
		Assert.assertEquals(null, SELUtil.getSort(current));
		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(current));
		
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertFalse(b);
		//Assert.assertTrue(b);
		//Assert.assertNotNull(i.current());
		//Assert.assertEquals("(5,0,8,0)", i.current().toString());
	}
	
	@Test
	public void testHandleSyntaxConstruct()
	{
		boolean b = false;
//		// tries to match the current term against the syntax pattern in in link
//		handle-syntax-construct(|link) = ?term
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-SEL-DEFINITION");
		Assert.assertTrue(b);
		
		// get the first sel definition
		i.getContext().getVarScope().add("t0", i.getFactory().makeInt(1));
		b = HybridInterpreterHelper.safeInvoke(i, "get_sel_link_at_index_0_1");
		Assert.assertTrue(b);
		IStrategoTerm selLinkEnter = i.current();
		
		i.getContext().getVarScope().add("t0", selLinkEnter);
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "handle_syntax_construct_0_1");
		Assert.assertTrue(b);

		// attachments are lost...

		IStrategoTerm current = i.current();
		Assert.assertEquals(instrumentedProgram.getFunctionDeclVoid().toString(), current.toString());
		Assert.assertEquals(null, SELUtil.getSort(current));
		//Assert.assertEquals("FunctionDecl", SELUtil.getSort(current));
		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(current));
		
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertFalse(b);
//		Assert.assertTrue(b);
//		Assert.assertNotNull(i.current());
//		Assert.assertEquals("(5,0,8,0)", i.current().toString());
	}
	
	@Test
	public void testHandleSelSpec()
	{
		boolean b = false;
		
//		// traverses the dsl program to find an aterm that matches the pattern for the current sel-spec
//		//?Link(event-type, syntax-pattern, gen-transformation, extract-transformation)
//		handle-sel-spec = ?link
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-SEL-DEFINITION");
		Assert.assertTrue(b);
		
		// get the first sel definition
		i.getContext().getVarScope().add("t0", i.getFactory().makeInt(1));
		b = HybridInterpreterHelper.safeInvoke(i, "get_sel_link_at_index_0_1");
		Assert.assertTrue(b);
		
		b = HybridInterpreterHelper.safeInvoke(i, "handle-sel-spec");
		Assert.assertTrue(b);
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		Assert.assertEquals(instrumentedProgram.getModule().toString(), i.current().toString());
	}
	
	@Test
	public void testInstrumentProgramAterm()
	{
		boolean b = false;
		
		// set-verbosity
		
		// Vomit
		//i.setCurrent(i.getFactory().makeInt(10));
		// verbose-level : Debug()     -> 4
		// Debug
		i.setCurrent(i.getFactory().makeInt(4));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-verbosity"));
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-FILENAME");
		Assert.assertTrue(b);
		IStrategoTerm filename = i.current();

		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		IStrategoTerm source = i.current();
		
		
		IStrategoTerm tuple = i.getFactory().makeTuple(filename, source);
//		instrument-program-aterm = ?(dsl-program-filename, dsl-program-aterm)
		i.setCurrent(tuple);
		b = HybridInterpreterHelper.safeInvoke(i, "instrument-program-aterm");
		Assert.assertTrue(b);
		Assert.assertEquals(instrumentedProgram.getModule().toString(), i.current().toString());
	}
	
	@Test
	public void testTryInstrument()
	{
		File f = new File(ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR +"/alng_scripts/justprint.alng");
		f.delete();
		Assert.assertFalse(f.exists());
		
		boolean b = false;
		
		this.setOutputBasePath(ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR);
		this.setLanguageID("ActionLanguage");
		this.setPostInstrumentation("actionlanguage-post-instrumentation");
		this.addWriteFile("ActionLanguage", "write-actionlanguage-to-file");
		
		// try-instrument = ?(dsl-program-filename, dsl-program-aterm)
		IStrategoTerm tuple = this.getFilenameDSLProgramTuple();
		i.setCurrent(tuple);
		b = HybridInterpreterHelper.safeInvoke(i, "try-instrument");
		Assert.assertTrue(b);

		String source = "alng_scripts/justprint.alng";
		String generated = ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR+"/alng_scripts/justprint.alng";
		IStrategoTerm succesReport = makeSuccess(source, generated);
		Assert.assertEquals(succesReport.toString(), i.current().toString());
		
		Assert.assertTrue(new File(ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR +"/alng_scripts/justprint.alng").exists());
		
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
