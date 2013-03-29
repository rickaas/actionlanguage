package util.alng_scripts;

import java.io.File;

import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import junit.framework.Assert;
import util.ALTestConstants;
import util.HybridInterpreterHelper;
import util.SELUtil;

/**
 * Return parts of the JustPrint program.
 * @author rlindeman
 *
 */
public class JustPrint extends ParsedProgramBase {


	
	@Test
	public void testJustPrintParsing()
	{
		this.init();
		Assert.assertNotNull(this.getModule());
		Assert.assertEquals("Module", SELUtil.getConstructor(this.getModule()));
		Assert.assertEquals("Start", SELUtil.getSort(this.getModule()));
		
		Assert.assertNotNull(this.getFunctionDeclVoid());
		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(this.getFunctionDeclVoid()));
		Assert.assertEquals("FunctionDecl", SELUtil.getSort(this.getFunctionDeclVoid()));
		
		Assert.assertNotNull(this.getFunctionBody());
		Assert.assertEquals(null, SELUtil.getConstructor(this.getFunctionBody())); // it is a list
		Assert.assertEquals("Statement*", SELUtil.getSort(this.getFunctionBody()));
		
		Assert.assertNotNull(this.getStatement());
		Assert.assertEquals("ExprStatement", SELUtil.getConstructor(this.getStatement()));
		Assert.assertEquals("Statement", SELUtil.getSort(this.getStatement()));
	}
	
	public void init()
	{
		String alng_program_location = ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng";
		File alngFile = new File(alng_program_location);
		Assert.assertTrue(alngFile.exists());
		
		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
		
		i.setCurrent(i.getFactory().makeString(alng_program_location));
		boolean b = HybridInterpreterHelper.safeInvoke(i, "parse-actionlanguage-file");
		Assert.assertTrue(b);
		this.dslProgramAterm = i.current();
		
		i.uninit();
		i.shutdown();
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
	
	public IStrategoTerm getStatement()
	{
		check();
		IStrategoTerm functionBody = getFunctionBody(); // [GlobalCall("print",[String("\"Global print FFFFFFF g\"")])]
		IStrategoTerm statement = functionBody.getSubterm(0); // GlobalCall("print",[String("\"Global print FFFFFFF g\"")]);
		return statement;
	}
}
