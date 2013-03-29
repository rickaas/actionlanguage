package org.languages.alng.instrumentation.generate;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.stratego.SDefT;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.terms.ParseError;
import org.strategoxt.HybridInterpreter;

import util.ALTestConstants;
import util.DebugInfoBuilder;
import util.HybridInterpreterHelper;
import util.SELUtil;
import util.alng_scripts.JustPrint;

/**
 * Test generate transformations on a set of program fragments.
 * @author rlindeman
 *
 */
public class TryGenerateTests {

	private HybridInterpreter i = null;
	
	/**
	 * Load the FunctionDeclVoid.dsldi.
	 * Load justprint.alng, this is the current term.
	 * The filename is available via GET-DSL-PROGRAM-FILENAME.
	 * The original program aterm is available via GET-DSL-PROGRAM-SOURCE.
	 * The SEL-definition is available via GET-SEL-DEFINITION.
	 */
	@Before
	public void setupProgram()
	{
		String alng_program_location = ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng";
		File alngFile = new File(alng_program_location);
		Assert.assertTrue(alngFile.exists());
		
		String dsldiLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/FunctionDeclVoid.dsldi";
		File selFile = new File(dsldiLocation);
		Assert.assertTrue(selFile.exists());

		// init the HybridInterpreter
		i = HybridInterpreterHelper.createHybridInterpreter();
		
		// load the dlsdi
		// read the SEL-definition and make sure it is available via GET-SEL-DEFINITION
		i.setCurrent(i.getFactory().makeString(dsldiLocation));
		boolean b = HybridInterpreterHelper.safeInvoke(i, "read-dsldi-file");
		Assert.assertTrue(b);
		// is SEL the current term?
		Assert.assertEquals(IStrategoTerm.APPL, i.current().getTermType());
		Assert.assertEquals("SEL", ((IStrategoAppl)i.current()).getName());
		b = HybridInterpreterHelper.safeInvoke(i, "SET-SEL-DEFINITION");
		
		// SET-DSL-PROGRAM-FILENAME
		i.setCurrent(i.getFactory().makeString(alng_program_location));
		b = HybridInterpreterHelper.safeInvoke(i, "SET-DSL-PROGRAM-FILENAME");
		Assert.assertTrue(b);
		
		// load justprint.alng
		i.setCurrent(i.getFactory().makeString(alng_program_location));
		b = HybridInterpreterHelper.safeInvoke(i, "parse-actionlanguage-file");
		Assert.assertTrue(b);
		
		// SET-DSL-PROGRAM-SOURCE
		b = HybridInterpreterHelper.safeInvoke(i, "SET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
	}
	
	/**
	 * Calling the gen-enter transformation fails when DEBUG-INFORMATION is not set.
	 * @throws InterpreterException
	 */
	@Test
	public void callAlngGenEnterWithoutCurrentDebugInformation() throws InterpreterException
	{
		boolean b = false;

		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		JustPrint program = new JustPrint();
		program.init(i.current());
		
//	    gen-enter : 
//        f -> instrumented-f
		Assert.assertNotNull(i.lookupUncifiedSVar("gen-enter"));
		// current term should be a FunctionDeclVoid 
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "gen-enter");
		Assert.assertFalse("Calling the gen-enter transformation fails when DEBUG-INFORMATION is not set.", b);
		Assert.assertNotNull(i.current());
	}
	
	@Test
	public void callAlngGenEnter() throws InterpreterException, ParseError, IOException
	{
		boolean b = false;

		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		JustPrint program = new JustPrint();
		program.init(i.current());

		JustPrint instrumentedProgram = new JustPrint();
		instrumentedProgram.initFromFile(i, "alng_scripts/aterm/justprint/justprint_with_enter_no_post.aterm");
		
		
		// all generate strategies require that GET-CURRENT-DEBUG-INFORMATION is set
		// debug-information := (filename, a, b, c, d, name)
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("alng_scripts/justprint.alng");
		builder.setLocation(5, 0, 8, 0);
		builder.setData("bar");
		IStrategoTerm currentDebugInformation = builder.makeEnter(i.getFactory());
		i.setCurrent(currentDebugInformation);
		b = HybridInterpreterHelper.safeInvoke(i, "SET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);


//	    gen-enter : 
//	        f -> instrumented-f
		Assert.assertNotNull(i.lookupUncifiedSVar("gen-enter"));
		// current term should be a FunctionDeclVoid 
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "gen-enter");
		i.getContext().getStackTracer().printStackTrace();
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		Assert.assertEquals(instrumentedProgram.getFunctionDeclVoid().toString(), i.current().toString());
		
		// missing attachment information
		IStrategoTerm generatedFunc = i.current();
		
		// gen-enter does not copy the attachments, 
		// copying of attachments is handled by: has-been-matched(|link) in instrument.str
		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(generatedFunc));
		Assert.assertEquals(null, SELUtil.getSort(generatedFunc)); // missing attachment
		i.setCurrent(generatedFunc);
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertFalse(b); // fails, because origin is an attachment
	}
	
	@Test
	public void callAlngGenEnterCopyAttachments()
	{
		boolean b = false;

		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		JustPrint program = new JustPrint();
		program.init(i.current());
		
		// all generate strategies require that GET-CURRENT-DEBUG-INFORMATION is set
		// debug-information := (filename, a, b, c, d, name)
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("alng_scripts/justprint.alng");
		builder.setLocation(5, 0, 8, 0);
		builder.setData("bar");
		IStrategoTerm currentDebugInformation = builder.makeEnter(i.getFactory());
		i.setCurrent(currentDebugInformation);
		b = HybridInterpreterHelper.safeInvoke(i, "SET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
//	    gen-enter : 
//        f -> instrumented-f
		Assert.assertNotNull(i.lookupUncifiedSVar("gen-enter"));
		// current term should be a FunctionDeclVoid 
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "gen-enter");
		i.getContext().getStackTracer().printStackTrace();
		Assert.assertTrue(b);
	

		
		// missing attachment information
		IStrategoTerm generatedFunc = i.current();
		
		// gen-enter does not copy the attachments, 
		// copying of attachments is handled by: has-been-matched(|link) in instrument.str
		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(generatedFunc));
		Assert.assertEquals(null, SELUtil.getSort(generatedFunc)); // missing attachment
		i.setCurrent(generatedFunc);
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertFalse(b); // fails, because origin is an attachment
		
		// manually copy the attachment
		// copy-attachment = ?(source, destination) ;java-copy-attachment(|source, destination)
		Assert.assertNotNull(i.lookupUncifiedSVar("copy-attachment"));
		i.setCurrent(i.getFactory().makeTuple(program.getFunctionDeclVoid(), generatedFunc));
		b = HybridInterpreterHelper.safeInvoke(i, "copy-attachment");
		Assert.assertTrue(b);
		// attachments were copied, current term is the tuple, the attachment are added to the original aterm
		// thus generatedFunc now has the attachments
		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(generatedFunc));
		Assert.assertEquals("FunctionDecl", SELUtil.getSort(generatedFunc));
		
		i.setCurrent(generatedFunc);
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertEquals("(4,0,7,0)", i.current().toString());

		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "get-a-b-c-d");
		Assert.assertTrue(b);
		Assert.assertEquals("(5,0,8,0)", i.current().toString());
		
		i.setCurrent(program.getFunctionDeclVoid());
		b = HybridInterpreterHelper.safeInvoke(i, "origin-location");
		Assert.assertEquals("(4,0,7,0)", i.current().toString());
		// copy-attachment-from(|source) = ?destination; !(source, destination); copy-attachment; !destination
	}
	
	@Test
	public void callTryGenerateFails() throws InterpreterException {
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		JustPrint program = new JustPrint();
		program.init(i.current());
		
		// all generate strategies require that GET-CURRENT-DEBUG-INFORMATION is set
		// debug-information := [filename, name, a, b, c, d]
		IStrategoTerm currentDebugInformation = i.getFactory().parseFromString("[\"alng_scripts/justprint.alng\",\"bar\",5,0,8,0]");
		i.setCurrent(currentDebugInformation);
		b = HybridInterpreterHelper.safeInvoke(i, "SET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
		
		// now try to call the generate function via it's name using: try-generate(|generate-name) =  ?aterm;
		Assert.assertNotNull(i.getContext().getVarScope().lookupSVar("try_generate_0_1"));
		SDefT sdeft = i.getContext().getVarScope().lookupSVar("try_generate_0_1");
		Assert.assertTrue(sdeft.isCompiledStrategy());
		Assert.assertNotNull(sdeft.getTermParams());
		Assert.assertEquals(1, sdeft.getTermParams().length);
		Assert.assertEquals("t0", sdeft.getTermParams()[0]);
		Assert.assertNull(i.getContext().lookupVar("t0"));
		i.getContext().getVarScope().add("t0", i.getFactory().makeString("gen-enter"));
		Assert.assertNotNull(i.getContext().lookupVar("t0"));

		
		i.setCurrent(program.getFunctionDeclVoid()); // FunctionDeclVoid should be current term
		b = HybridInterpreterHelper.safeInvoke(i, "try_generate_0_1");
		Assert.assertFalse(b); // Could not find generate transformation: gen-enter, we need to register it
		i.getContext().getVarScope().removeVar("t0");

		

	}
	
	@Test
	public void callTryGenerateOnRegisteredStrategy() throws ParseError, IOException {
		boolean b = false;
		
		b = HybridInterpreterHelper.safeInvoke(i, "GET-DSL-PROGRAM-SOURCE");
		Assert.assertTrue(b);
		
		// parse justprint.alng and get the FunctionDeclVoid aterm
		JustPrint program = new JustPrint();
		program.init(i.current());
		// instrumented version
		JustPrint instrumentedProgram = new JustPrint();
		instrumentedProgram.initFromFile(i, "alng_scripts/aterm/justprint/justprint_with_enter_no_post.aterm");
		
		// all generate strategies require that GET-CURRENT-DEBUG-INFORMATION is set
		// debug-information := (filename, a, b, c, d, name)
		DebugInfoBuilder builder = new DebugInfoBuilder();
		builder.setFilename("alng_scripts/justprint.alng");
		builder.setLocation(5, 0, 8, 0);
		builder.setData("bar");
		IStrategoTerm currentDebugInformation = builder.makeEnter(i.getFactory());
		i.setCurrent(currentDebugInformation);
		b = HybridInterpreterHelper.safeInvoke(i, "SET-CURRENT-DEBUG-INFORMATION");
		Assert.assertTrue(b);
		
		// call ext-register-gen-strategy(gen-strategy|strategy-name)
		// the strategy ignores the current term
		i.getContext().getVarScope().add("t0", i.getFactory().makeString("gen-enter"));
		i.getContext().getVarScope().addSVar("s0", i.lookupUncifiedSVar("gen-enter"));
		b = HybridInterpreterHelper.safeInvoke(i, "ext_register_gen_strategy_1_1");
		Assert.assertTrue(b);
		i.getContext().getVarScope().removeVar("t0");
		i.getContext().getVarScope().removeSVar("s0");
		
		// it should now exist
		i.getContext().getVarScope().add("t0", i.getFactory().makeString("gen-enter"));
		b = HybridInterpreterHelper.safeInvoke(i, "ext_exists_gen_strategy_0_1");
		Assert.assertTrue(b); // Found extract transformation: extract-function-info
		i.getContext().getVarScope().removeVar("t0");
		
		// call try-generate(|generate-name) = ?aterm;
		i.getContext().getVarScope().add("t0", i.getFactory().makeString("gen-enter"));
		i.setCurrent(program.getFunctionDeclVoid()); // FunctionDeclVoid should be current term
		b = HybridInterpreterHelper.safeInvoke(i, "try_generate_0_1");
		Assert.assertTrue(b);
		i.getContext().getVarScope().removeVar("t0");
		Assert.assertEquals(instrumentedProgram.getFunctionDeclVoid().toString(), i.current().toString());

	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
