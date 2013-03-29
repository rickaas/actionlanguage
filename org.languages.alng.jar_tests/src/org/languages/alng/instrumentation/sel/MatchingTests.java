package org.languages.alng.instrumentation.sel;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.ALTestConstants;
import util.FileUtil;
import util.HybridInterpreterHelper;
import util.SELUtil;
import util.alng_scripts.JustPrint;

public class MatchingTests {

	private HybridInterpreter i;
	
	@Test
	public void matchAgainstModuleATerm() throws IOException
	{
		String alng_program_location = ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng";
		File file = new File(alng_program_location);
		Assert.assertTrue(file.exists());
		
		i = HybridInterpreterHelper.createHybridInterpreter();
		
		i.setCurrent(i.getFactory().makeString(alng_program_location));
		
		boolean b = HybridInterpreterHelper.safeInvoke(i, "parse-actionlanguage-file");
		Assert.assertTrue(b);
		
		IStrategoTerm termOutput = i.current();
		Assert.assertEquals(2, termOutput.getAllSubterms().length);
		
		String stringRepresentation = termOutput.toString();
		Assert.assertEquals(FileUtil.readFile(ALTestConstants.ALNG_SCRIPTS_DIR+"/aterm/justprint/justprint.aterm"), stringRepresentation);
		
		String consName = null;
		String sortName = null;
		
		consName = SELUtil.getConstructor(termOutput);
		sortName = SELUtil.getSort(termOutput);
		
        Assert.assertEquals("Module", consName);
        Assert.assertEquals("Start", sortName);
        
        IStrategoTerm pattern = SELUtil.createPattern("Start", "Module", i.getFactory());
		// syntax pattern can be one of the following structures
		//	SortAndConstructor(Empty(), Constructor("Fail"))
		//	SortAndConstructor(Sort("Strategy"), Constructor("Fail"))
		//	SortAndConstructor(Sort("StrategyDef"),Empty())
		//(termOutput, syntax-pattern)
		IStrategoTerm tuple = i.getFactory().makeTuple(termOutput, pattern);
		
		org.strategoxt.imp.debug.instrumentation.strategies.java_match_term_against_syntax_pattern_0_0 matcher = org.strategoxt.imp.debug.instrumentation.strategies.java_match_term_against_syntax_pattern_0_0.instance;
		IStrategoTerm matcherOutput = matcher.invoke(i.getCompiledContext(), tuple);
		Assert.assertNotNull(matcherOutput); // returns current 
		Assert.assertEquals(termOutput, matcherOutput);
		
		
		IStrategoTerm content = termOutput.getSubterm(1); // [Main, FunctionDeclVoid]
		IStrategoTerm functionDeclVoid = content.getSubterm(1);
		consName = SELUtil.getConstructor(functionDeclVoid);
		sortName = SELUtil.getSort(functionDeclVoid);
		
        Assert.assertEquals("FunctionDeclVoid", consName);
        Assert.assertEquals("FunctionDecl", sortName);
        matcherOutput = matcher.invoke(i.getCompiledContext(), i.getFactory().makeTuple(functionDeclVoid, SELUtil.createPattern("FunctionDecl", "FunctionDeclVoid", i.getFactory())));
		Assert.assertNotNull(matcherOutput); // returns current 
		Assert.assertEquals(functionDeclVoid, matcherOutput);
	}
	
	@Test
	public void testMatchingViaInvoke()
	{
		JustPrint program = new JustPrint();
		program.init();
		
		i = HybridInterpreterHelper.createHybridInterpreter();
		
		IStrategoTerm pattern = SELUtil.createATermPatternTuple(program.getModule(), "Start", "Module", i.getFactory());
		i.setCurrent(pattern);
		boolean b = HybridInterpreterHelper.safeInvoke(i, "java-match-term-against-syntax-pattern");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
		
		pattern = SELUtil.createATermPatternTuple(program.getModule(), "Foo", "Module", i.getFactory());
		i.setCurrent(pattern);
		b = HybridInterpreterHelper.safeInvoke(i, "java-match-term-against-syntax-pattern");
		Assert.assertFalse(b);
		Assert.assertNotNull(i.current());
		
		pattern = SELUtil.createATermPatternTuple(program.getModule(), "Start", "Mohhhhle", i.getFactory());
		i.setCurrent(pattern);
		b = HybridInterpreterHelper.safeInvoke(i, "java-match-term-against-syntax-pattern");
		Assert.assertFalse(b);
		Assert.assertNotNull(i.current());
		
		pattern = SELUtil.createATermPatternTuple(program.getFunctionDeclVoid(), "FunctionDecl", "FunctionDeclVoid", i.getFactory());
		i.setCurrent(pattern);
		b = HybridInterpreterHelper.safeInvoke(i, "java-match-term-against-syntax-pattern");
		Assert.assertTrue(b);
		Assert.assertNotNull(i.current());
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
