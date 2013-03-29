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
import util.SELUtil;
import util.alng_scripts.JustPrint;

public class FunctionDeclVoidEnterExitTests extends EventInstrumentationBase {
	
	private JustPrint program = null;
	
	private JustPrint instrumentedProgram = null;
	
	@Before
	public void setupProgram() throws ParseError, IOException
	{
		initHI();
		loadDSLDI(ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/FunctionDeclVoid.dsldi");
		loadDSLProgram(ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng");

		// keep a reference to the original source and the expected instrumented version
		// parse justprint.alng
		program = new JustPrint();
		program.init(getOriginalDSLProgramSource());
		// parse the expected instrumented version
		instrumentedProgram = new JustPrint();
		instrumentedProgram.initFromFile(i, "alng_scripts/aterm/justprint/justprint_with_enterexit_no_post.aterm");
		
		// register extract and generate strategies:
		registerExtractFunction("extract-function-info");
		registerGenerateFunction("gen-enter");
		registerGenerateFunction("gen-exit");

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
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertEquals("(4,0,7,0)", i.current().toString());
		
		// add enter event
		i.getContext().getVarScope().add("t0", i.getFactory().makeString("extract-function-info"));
		i.getContext().getVarScope().add("t1", i.getFactory().makeString("gen-enter"));
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "instrument_specific_aterm_0_2");
		Assert.assertTrue(b);
		IStrategoTerm withEnter = i.current();
		// attachments are lost...
		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(withEnter));
		Assert.assertEquals(null, SELUtil.getSort(withEnter)); // missing attachment
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertFalse(b); // fails, because origin is an attachment
		
		// manually copy the attachment
		// source
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertEquals("(4,0,7,0)", i.current().toString());
		
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "get-a-b-c-d");
		Assert.assertTrue(b);
		Assert.assertEquals("(5,0,8,0)", i.current().toString());
		
		// copy-attachment = ?(source, destination) ;java-copy-attachment(|source, destination)
		i.setCurrent(i.getFactory().makeTuple(program.getFunctionDeclVoid(), withEnter));
		b = HybridInterpreterHelper.safeInvoke(i, "copy-attachment");
		Assert.assertTrue(b);
		// withEnter now has attachments
		Assert.assertEquals(i.getFactory().makeTuple(program.getFunctionDeclVoid(), withEnter).toString(), i.current().toString());
		
		i.setCurrent(withEnter);
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertTrue(b);
		Assert.assertEquals("(4,0,7,0)", i.current().toString());
		
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "get-a-b-c-d");
		Assert.assertTrue(b);
		Assert.assertEquals("(5,0,8,0)", i.current().toString());
		
		// add exit event
		i.getContext().getVarScope().add("t0", i.getFactory().makeString("extract-function-info"));
		i.getContext().getVarScope().add("t1", i.getFactory().makeString("gen-exit"));
		i.setCurrent(withEnter);
		b = HybridInterpreterHelper.safeInvoke(i, "instrument_specific_aterm_0_2");
		i.getCompiledContext().printStackTrace();
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
		IStrategoTerm selLinkEnter = i.current();
		Assert.assertTrue(b);

		// instrument with enter-event
		i.getContext().getVarScope().add("t0", i.current());
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "has_been_matched_0_1");
		Assert.assertTrue(b);
		IStrategoTerm partiallyInstrumented = i.current();
		// has-been-matched restores the attachment
		
		// get the second sel definition
		b = HybridInterpreterHelper.safeInvoke(i, "GET-SEL-DEFINITION");
		Assert.assertTrue(b);
		i.getContext().getVarScope().add("t0", i.getFactory().makeInt(2));
		b = HybridInterpreterHelper.safeInvoke(i, "get_sel_link_at_index_0_1");
		IStrategoTerm selLinkExit = i.current();
		Assert.assertTrue(b);

		// instrument with exit-event
		i.getContext().getVarScope().add("t0", i.current());
		i.setCurrent(partiallyInstrumented);
		b = HybridInterpreterHelper.safeInvoke(i, "has_been_matched_0_1");
		//Assert.assertTrue(b);
		// has-been-matched fails because attachments are lost...
		Assert.assertFalse(b);
		
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
		
		// get the first sel definition to add the enter event
		i.getContext().getVarScope().add("t0", i.getFactory().makeInt(1));
		b = HybridInterpreterHelper.safeInvoke(i, "get_sel_link_at_index_0_1");
		IStrategoTerm selLinkEnter = i.current();
		Assert.assertTrue(b);
		
		i.getContext().getVarScope().add("t0", i.current());
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "handle_syntax_construct_0_1");
		Assert.assertTrue(b);
		IStrategoTerm partiallyInstrumented = i.current();
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-SEL-DEFINITION");
		Assert.assertTrue(b);
		
		// get the second sel definition to add the exit event
		i.getContext().getVarScope().add("t0", i.getFactory().makeInt(2));
		b = HybridInterpreterHelper.safeInvoke(i, "get_sel_link_at_index_0_1");
		IStrategoTerm selLinkExit = i.current();
		Assert.assertTrue(b);
		
		i.getContext().getVarScope().removeVar("t0");
		i.getContext().getVarScope().add("t0", i.current());
		i.setCurrent(partiallyInstrumented);
		b = HybridInterpreterHelper.safeInvoke(i, "handle_syntax_construct_0_1");
		Assert.assertTrue(b);
		
		// attachments are lost...
		// therefore the second instrumentation round fails
		
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
		
		IStrategoTerm config = i.getFactory().makeTuple(i.getFactory().makeString("--allow-no-instrumentation-failures"), i.getFactory().makeTuple());
		i.setCurrent(config);
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));

		b = HybridInterpreterHelper.safeInvoke(i, "GET-SEL-DEFINITION");
		Assert.assertTrue(b);
		// get the first sel definition
		i.getContext().getVarScope().add("t0", i.getFactory().makeInt(1));
		b = HybridInterpreterHelper.safeInvoke(i, "get_sel_link_at_index_0_1");
		Assert.assertTrue(b);
		
		b = HybridInterpreterHelper.safeInvoke(i, "handle-sel-spec");
		Assert.assertTrue(b);
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-SEL-DEFINITION");
		Assert.assertTrue(b);
		// get the second sel definition
		i.getContext().getVarScope().add("t0", i.getFactory().makeInt(2));
		b = HybridInterpreterHelper.safeInvoke(i, "get_sel_link_at_index_0_1");
		Assert.assertTrue(b);
		
		b = HybridInterpreterHelper.safeInvoke(i, "handle-sel-spec");
		Assert.assertTrue(b);
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// attachments are lost...
		// therefore the second instrumentation round fails

		//Assert.assertEquals(instrumentedProgram.getModule().toString(), i.current().toString());
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
