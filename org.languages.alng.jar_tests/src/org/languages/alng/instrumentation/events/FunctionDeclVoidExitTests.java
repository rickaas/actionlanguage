package org.languages.alng.instrumentation.events;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spoofax.interpreter.stratego.Strategy;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.terms.ParseError;

import util.ALTestConstants;
import util.HybridInterpreterHelper;
import util.SELUtil;
import util.alng_scripts.JustPrint;

public class FunctionDeclVoidExitTests extends EventInstrumentationBase {

	public String getALNGScriptLocation()
	{
		String alng_program_location = ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng";
		
		return alng_program_location;
	}
	
	public String getDSLDILocation()
	{
		String dsldiLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/FunctionDeclVoidExit.dsldi";
		
		return dsldiLocation;
	}
	
	private JustPrint program = null;
	
	private JustPrint instrumentedProgram = null;
	
	@Before
	public void setupProgram() throws ParseError, IOException
	{
		
		initHI();
		loadDSLDI(ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/FunctionDeclVoidExit.dsldi");
		loadDSLProgram(ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng");

		// keep a reference to the original source and the expected instrumented version
		// parse justprint.alng
		program = new JustPrint();
		program.init(getOriginalDSLProgramSource());
		// parse the expected instrumented version
		instrumentedProgram = new JustPrint();
		instrumentedProgram.initFromFile(i, "alng_scripts/aterm/justprint/justprint_with_exit_no_post.aterm");
		
		// register extract and generate strategies:
		registerExtractFunction("extract-function-info");
		registerGenerateFunction("gen-exit");
		
	}
	
	
	@Test
	public void testInstrumentSpecificAterm() throws ParseError, IOException
	{
		boolean b = false;
		
		// call: instrument-specific-aterm(|extract-name, generate-name) =  ?aterm
		// It returns the debug-instrumented aterm
		i.getContext().getVarScope().add("t0", i.getFactory().makeString("extract-function-info"));
		i.getContext().getVarScope().add("t1", i.getFactory().makeString("gen-exit"));
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
		
		b = HybridInterpreterHelper.safeInvoke(i, "init-enable-tracking");
		Assert.assertTrue(b);
		
		IStrategoTerm config = i.getFactory().makeTuple(i.getFactory().makeString("--allow-no-instrumentation-failures"), i.getFactory().makeTuple());
		i.setCurrent(config);
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
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
		
		i.setCurrent(program.getFunctionDeclVoid());

//		org.spoofax.interpreter.library.jsglr.origin.SSL_EXT_clone_and_set_parents cloner = new org.spoofax.interpreter.library.jsglr.origin.SSL_EXT_clone_and_set_parents();
//		IStrategoTerm[] args = new IStrategoTerm[] { i.current() };
//		b = cloner.call(i.getContext(), new Strategy[0], args);
//
//		IStrategoTerm after = i.current();

		i.getContext().getVarScope().add("t0", selLinkEnter);
		b = HybridInterpreterHelper.safeInvoke(i, "has_been_matched_0_1");
		Assert.assertTrue(b);

		// attachments are lost...
//		IStrategoTerm current = i.current();
//		Assert.assertEquals(instrumentedProgram.getFunctionDeclVoid().toString(), i.current().toString());
//		Assert.assertEquals("FunctionDecl", SELUtil.getSort(i.current()));
//		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(i.current()));
//		
//		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
//		Assert.assertTrue(b);
//		Assert.assertNotNull(i.current());
//		Assert.assertEquals("(5,0,8,0)", i.current().toString());
	}
	
	@Test
	public void testHandleSyntaxConstruct()
	{
		boolean b = false;
//		// tries to match the current term against the syntax pattern in in link
//		handle-syntax-construct(|link) = ?term
		
		IStrategoTerm config = i.getFactory().makeTuple(i.getFactory().makeString("--allow-no-instrumentation-failures"), i.getFactory().makeTuple());
		i.setCurrent(config);
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
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
		
//		Assert.assertEquals(instrumentedProgram.getFunctionDeclVoid().toString(), i.current().toString());
//		Assert.assertEquals("FunctionDecl", SELUtil.getSort(i.current()));
//		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(i.current()));
//		
//		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
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
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
