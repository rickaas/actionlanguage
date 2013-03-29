package org.languages.alng.runner;

import org.spoofax.interpreter.core.Tools;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.IStrategoTuple;

/**
 * compile-for-debug and compile-for-run returns a tuple:
 * (result, java-filename, bin-directory, classname, stdout-filename)
 * 
 * This class is a wrapper around that IStrategoTerm, use it to convert it to Java data structures
 * @author rlindeman
 *
 */
public class CompilationResultWrapper {

	private IStrategoTerm term = null;
	
	public CompilationResultWrapper(IStrategoTerm term) {
		this.term = term;
	}
	
	private void checkValid() {
		if (!(term instanceof IStrategoTuple)) throw new RuntimeException("Invalid Compilation Result");
	}
	
	public int getResult() {
		checkValid();
		IStrategoTerm term_result = term.getSubterm(0); // result is an integer
		return Tools.asJavaInt(term_result);
	}
	
	public String getJavaFilename() {
		checkValid();
		IStrategoTerm term_javaFilename = term.getSubterm(1);
		return Tools.asJavaString(term_javaFilename);
	}
	
	public String getBinDirectory() {
		checkValid();
		IStrategoTerm term_binDirectory = term.getSubterm(2);
		return Tools.asJavaString(term_binDirectory);
	}
	
	public String getClassname() {
		checkValid();
		IStrategoTerm term_classname = term.getSubterm(3);
		return Tools.asJavaString(term_classname);
	}
	
	public String getStdoutFilename() {
		checkValid();
		IStrategoTerm term_stdoutFilename = term.getSubterm(4);
		return Tools.asJavaString(term_stdoutFilename);
	}
}
