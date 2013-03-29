package org.languages.alng.parsing;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.terms.IStrategoConstructor;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.HybridInterpreterHelper;

public class ATermIOTests {

	public static String GENERATED_DIR = "alng_scripts/generated";
	
	private HybridInterpreter i;
	
	@Test
	public void writeATermToString() throws InterpreterException
	{
		i = HybridInterpreterHelper.createHybridInterpreter();

		IStrategoConstructor constructor = i.getFactory().makeConstructor("Foo", 2);
		IStrategoTerm arg1 = i.getFactory().makeInt(5);
		IStrategoTerm arg2 = i.getFactory().makeString("5");
		IStrategoTerm input = i.getFactory().makeAppl(constructor, arg1, arg2);
		i.setCurrent(input);
		Assert.assertEquals("Foo(5,\"5\")", i.current().toString());

		HybridInterpreterHelper.safeInvoke(i, "write-to-string");
		
		Assert.assertEquals("\"Foo(5,\\\"5\\\")\"", i.current().toString()); // aaiii, double escaping...
	}
	
	@Test
	public void readATermFromString()
	{
		i = HybridInterpreterHelper.createHybridInterpreter();
		
		// Foo(5,"5")
		String atermString = "Foo(5,\"5\")";
		i.setCurrent(i.getFactory().makeString(atermString));
		HybridInterpreterHelper.safeInvoke(i, "read-from-string");
		Assert.assertEquals(atermString, i.current().toString());
	}
	
	@Test
	public void readATermFromFile()
	{
		i = HybridInterpreterHelper.createHybridInterpreter();
		Assert.assertTrue((new File("alng_scripts/aterm/justprint/justprint.aterm")).exists());
		IStrategoTerm input = i.getFactory().makeString("alng_scripts/aterm/justprint/justprint.aterm");
		i.setCurrent(input);
		HybridInterpreterHelper.safeInvoke(i, "ReadFromFile");
		
		Assert.assertEquals("Module(\"justprint\"," +
				"[Main(\"bar\"),FunctionDeclVoid(\"bar\",[],[" +
				"ExprStatement(GlobalCall(\"print\",FunctionArguments([String(\"\\\"Global print FFFFFFF g\\\"\")])))])])", i.current().toString());
	}
	
	// WriteToTextFile
	
	@Ignore
	public void writeActionLanguageToFile()
	{
		//File f = new File("alng_scripts/aterm/write_to_file.aterm");
		i = HybridInterpreterHelper.createHybridInterpreter();
		IStrategoTerm targetLocation = i.getFactory().makeString("alng_scripts/generated/aterm/write_to_file.aterm");
		IStrategoTerm ast = i.getFactory().parseFromString("Foo(5,\"5\")");
		IStrategoTerm tuple = i.getFactory().makeTuple(targetLocation, ast);
		
		i.setCurrent(tuple);
		// write-actionlanguage-to-file = ?(output-filename, dsl-ast)
		HybridInterpreterHelper.safeInvoke(i, "write-actionlanguage-to-file");
		
		IStrategoTerm input = i.getFactory().makeString("alng_scripts/generated/aterm/write_to_file.aterm");
		i.setCurrent(input);
		HybridInterpreterHelper.safeInvoke(i, "ReadFromFile");
		i.getCompiledContext().printStackTrace();
		Assert.assertEquals("Foo(5,\"5\")", i.current().toString());
		//i.setCurrent(input);
	}
	
	@Test
	public void WriteDslProgram()
	{
		//<write-dsl-program> (output-filename, instrumented-aterm)
	}
	
	@After
	public void tearDownInterpreter() {
		if (i != null) {
			i.uninit();
			i.shutdown();
		}
	}
}
