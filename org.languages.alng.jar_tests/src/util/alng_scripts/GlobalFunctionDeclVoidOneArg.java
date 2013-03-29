package util.alng_scripts;

import java.io.File;

import junit.framework.Assert;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.ALTestConstants;
import util.HybridInterpreterHelper;

public class GlobalFunctionDeclVoidOneArg extends ParsedProgramBase {

	@Override
	public void init() {
		String alng_program_location = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/fargs/onearg.alng";
		File alngFile = new File(alng_program_location);
		Assert.assertTrue(alngFile.exists());
		
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		
		i.setCurrent(i.getFactory().makeString(alng_program_location));
		boolean b = HybridInterpreterHelper.safeInvoke(i, "parse-actionlanguage-file");
		Assert.assertTrue(b);
		this.dslProgramAterm = i.current();
		
		i.uninit();
	}

	private void check()
	{
		if (this.dslProgramAterm == null) {
			this.init();
		}
	}

	
	
	public IStrategoTerm getModule()
	{
		check();
		return dslProgramAterm;
	}
	
	public IStrategoTerm getFunctionDeclVoid()
	{
		check();
		IStrategoTerm moduleContent = dslProgramAterm.getSubterm(1); // [Main, FunctionDeclVoid]
		IStrategoTerm functionDeclVoid = moduleContent.getSubterm(1); // FunctionDeclVoid
		return functionDeclVoid;
	}
	
	public IStrategoTerm getFunctionBody()
	{
		check();
		IStrategoTerm functionDeclVoid = getFunctionDeclVoid(); // FunctionDeclVoid("bar",[],[GlobalCall("print",[String("\"Global print FFFFFFF g\"")])])
		IStrategoTerm functionBody = functionDeclVoid.getSubterm(2); // [GlobalCall("print",[String("\"Global print FFFFFFF g\"")])]
		return functionBody;
	}

}
