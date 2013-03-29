package org.languages.alng.runner;

import org.spoofax.interpreter.core.InterpreterErrorExit;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.core.InterpreterExit;
import org.spoofax.interpreter.core.UndefinedStrategyException;
import org.strategoxt.HybridInterpreter;


public class LaunchProgram {

	private IHybridInterpreterProvider provider = null;
	
	public LaunchProgram(IHybridInterpreterProvider provider)
	{
		this.provider = provider;
	}
	

	
	// filename is ActionLanguage program
	// compile ActionLanguage to Java (optional: debug-instrumentation):
	//  * Requires strategoxt.jar
	//  * actionlanguage-java.jar actionlanguage.jar
	//  * libdsldi.jar, dsldi.jar, dsldi-java
	// compile Java to bin. Requires alngruntime.jar (??and org.spoofax.debug.java.library.jar)
	// run Java. Requires bin-directory, alngruntime.jar and org.spoofax.debug.java.library.jar
	
	public void run(String alngLocation) throws InterpreterErrorExit, InterpreterExit, UndefinedStrategyException, InterpreterException
	{
		HybridInterpreter i= provider.get();
		
		i.setCurrent(i.getFactory().makeString(alngLocation));
		// current term should be the path to an Alng program
		i.invoke("run-al-program");
		// returns stdout
		//Assert.assertEquals("\"b\\ntrue\\n\"", i.current().toString());
	}
	
	public void debug(String alngLocation) throws InterpreterErrorExit, InterpreterExit, UndefinedStrategyException, InterpreterException
	{
		HybridInterpreter i= provider.get();
		
		i.setCurrent(i.getFactory().makeString(alngLocation));
		// current term should be the path to an Alng program
		i.invoke("run-al-program");
		// returns stdout
		//Assert.assertEquals("\"b\\ntrue\\n\"", i.current().toString());
	}
	

	public void safeRun(String alngLocation)
	{
		try {
			run(alngLocation);
		} catch (InterpreterErrorExit e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterpreterExit e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndefinedStrategyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterpreterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void safeDebug(String alngLocation)
	{
		try {
			debug(alngLocation);
		} catch (InterpreterErrorExit e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterpreterExit e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndefinedStrategyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterpreterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CompilationResultWrapper compileForRun(String alngLocation) throws InterpreterErrorExit, InterpreterExit, UndefinedStrategyException, InterpreterException
	{
		HybridInterpreter i= provider.get();
		
		i.setCurrent(i.getFactory().makeString(alngLocation));
		i.invoke("compile-for-run");
		// Returns (result, java-filename, bin-directory, classname, stdout-filename)
		
		CompilationResultWrapper r = new CompilationResultWrapper(i.current());
		return r;
	}
	
	/**
	 * Compiles the actionlanguage program to java class files.
	 * The program is instrumented with actionlanguage debug information.
	 * @param alngLocation
	 * @throws InterpreterErrorExit
	 * @throws InterpreterExit
	 * @throws UndefinedStrategyException
	 * @throws InterpreterException
	 */
	public CompilationResultWrapper compileForDebug(String alngLocation) throws InterpreterErrorExit, InterpreterExit, UndefinedStrategyException, InterpreterException
	{
		HybridInterpreter i= provider.get();
		
//		i.invoke("getcwd");
//		System.out.println("GETCWD");
//		System.out.println(i.current().toString());
		
		i.setCurrent(i.getFactory().makeString(alngLocation));
		i.invoke("compile-for-debug");
//		// Returns (result, java-filename, bin-directory, classname, stdout-filename)
		
		CompilationResultWrapper r = new CompilationResultWrapper(i.current());
		return r;
	}
}
