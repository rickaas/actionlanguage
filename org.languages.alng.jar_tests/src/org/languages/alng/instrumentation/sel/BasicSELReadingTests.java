package org.languages.alng.instrumentation.sel;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.stratego.SDefT;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.ALTestConstants;
import util.HybridInterpreterHelper;

/**
 * Basic tests for reading a dsldi file written in the Syntax Event Linking Language.
 * 
 * Such a file describes what parts of the ATerm should be instrumented.
 * 
 * Each SEL-def describes a Pattern a metadata extraction Strategy and a debug-event generation Strategy.
 * @author rlindeman
 *
 */
public class BasicSELReadingTests {

	private HybridInterpreter i;
	
	@Test
	public void readActionLanguageSELDefintion() throws InterpreterException
	{
		String dsldiLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/Example.dsldi";
		File file = new File(dsldiLocation);
		Assert.assertTrue(file.exists());

		i = HybridInterpreterHelper.createHybridInterpreter();
		Assert.assertNotNull(i.lookupUncifiedSVar("read-dsldi-file"));
		
		// parse a dsldi instrumentation declaration
		i.setCurrent(i.getFactory().makeString(dsldiLocation));
		

//rules
//	read-dsldi-file = parse-dsldi-file
		boolean b = HybridInterpreterHelper.safeInvoke(i, "read-dsldi-file");
		Assert.assertTrue(b);
		// dsldi.jar contains "/trans/DSLDI.tbl"
		
		// libdsldi.jar does not contains DSLDI.tbl. Is this a problem? "/org/strategoxt/imp/debug/instrumentation/trans/"
		
		Assert.assertEquals(IStrategoTerm.APPL, i.current().getTermType());
		Assert.assertEquals("SEL", ((IStrategoAppl)i.current()).getName());
		IStrategoAppl sel = ((IStrategoAppl)i.current());
		Assert.assertEquals(1, sel.getSubtermCount());
		Assert.assertEquals(IStrategoTerm.APPL, sel.getSubterm(0).getTermType());
		Assert.assertEquals("InstrBlock", ((IStrategoAppl)sel.getSubterm(0)).getName());
		
		IStrategoAppl instrBlock = ((IStrategoAppl)sel.getSubterm(0));
		Assert.assertEquals("InstrBlock([" +
				"Link(Enter," +
					"SortAndConstructor(Sort(\"FunctionDecl\"),Empty)," +
					"StrategyRef(\"gen-enter\"),StrategyRef(\"extract-function-info\"))" +
				",Link(Exit," +
					"SortAndConstructor(Sort(\"FunctionDecl\"),Empty)," +
					"StrategyRef(\"gen-exit\"),StrategyRef(\"extract-function-info\"))" +
				"])", instrBlock.toString());
		Assert.assertEquals(1, instrBlock.getSubtermCount());
		Assert.assertEquals(IStrategoTerm.LIST, instrBlock.getSubterm(0).getTermType());
		Assert.assertEquals(IStrategoTerm.APPL, instrBlock.getSubterm(0).getSubterm(0).getTermType());
		Assert.assertEquals(IStrategoTerm.APPL, instrBlock.getSubterm(0).getSubterm(1).getTermType());
		Assert.assertEquals("Link(Enter,SortAndConstructor(Sort(\"FunctionDecl\"),Empty),StrategyRef(\"gen-enter\"),StrategyRef(\"extract-function-info\"))", instrBlock.getSubterm(0).getSubterm(0).toString());
		Assert.assertEquals("Link(Exit,SortAndConstructor(Sort(\"FunctionDecl\"),Empty),StrategyRef(\"gen-exit\"),StrategyRef(\"extract-function-info\"))", instrBlock.getSubterm(0).getSubterm(1).toString());
		
		Assert.assertNull(i.getContext().getVarScope().lookupSVar("get-sel-link-at-index"));
		Assert.assertNotNull(i.getContext().getVarScope().lookupSVar("get_sel_link_at_index_0_1"));
		
		SDefT sdeft = i.getContext().getVarScope().lookupSVar("get_sel_link_at_index_0_1");
		Assert.assertTrue(sdeft.isCompiledStrategy());
		
		
		
		// RL: arghhhh 1-index based
		//b = sdeft.getParametrizedBody(new org.spoofax.interpreter.stratego.Strategy[]{} , new IStrategoTerm[] { i.getFactory().makeInt(2) }).evaluate(i.getContext());
		//b = sdeft.getParametrizedBody(new org.spoofax.interpreter.stratego.Strategy[]{} , new IStrategoTerm[] { i.getFactory().makeString("1") }).evaluate(i.getContext());
		//Assert.assertTrue(sdeft instanceof InteropSDefT);
		//InteropSDefT s = (InteropSDefT) sdeft;
		//s.getStrategy().invoke(i.getCompiledContext(), sel, i.getFactory().makeInt(0));

		Assert.assertNotNull(sdeft.getTermParams());
		Assert.assertEquals(1, sdeft.getTermParams().length);
		Assert.assertEquals("t0", sdeft.getTermParams()[0]);
		Assert.assertNull(i.getContext().lookupVar("t0"));
		
//	// returns the Link at the index i
//	// current term should be SEL
//	// return-type: Link
//	get-sel-link-at-index(|i) = get-sel-links-as-list; index(|i)
		i.getContext().getVarScope().add("t0", i.getFactory().makeInt(1));
		Assert.assertNotNull(i.getContext().lookupVar("t0"));

		b = HybridInterpreterHelper.safeInvoke(i, "get_sel_link_at_index_0_1");
		Assert.assertTrue(b);
		
		Assert.assertEquals(IStrategoTerm.APPL, i.current().getTermType());
		Assert.assertEquals("Link", ((IStrategoAppl)i.current()).getName());
		
		IStrategoTerm link_0 = i.current();
		
//	// current term is a Link, returns the event-type
//	// return-type: String
//	get-event-type
		i.setCurrent(link_0);
		b = HybridInterpreterHelper.safeInvoke(i, "get-event-type");
		Assert.assertTrue(b);
		Assert.assertEquals("Enter", i.current().toString());
		 

	
//	// current term is a Link, returns the syntax-pattern
//	// return-type: String
//	get-syntax-pattern
		i.setCurrent(link_0);
		b = HybridInterpreterHelper.safeInvoke(i, "get-syntax-pattern");
		Assert.assertTrue(b);
		Assert.assertEquals("SortAndConstructor(Sort(\"FunctionDecl\"),Empty)", i.current().toString());

		
//	// current term is a Link, returns the gen-transformation (the name of the strategy to use in the event generation)
//	// return-type: String
//	get-gen-transformation
		i.setCurrent(link_0);
		b = HybridInterpreterHelper.safeInvoke(i, "get-gen-transformation");
		Assert.assertTrue(b);
		Assert.assertEquals("\"gen-enter\"", i.current().toString());

	
//	// current term is a Link, returns the extract-transformation (the name of the strategy to use in the metadata-extraction)
//	// return-type: String
//	get-extract-transformation
		i.setCurrent(link_0);
		b = HybridInterpreterHelper.safeInvoke(i, "get-extract-transformation");
		Assert.assertTrue(b);
		Assert.assertEquals("\"extract-function-info\"", i.current().toString());

		String link_0_extractStrategy = i.current().toString();
		link_0_extractStrategy = link_0_extractStrategy.substring(1, link_0_extractStrategy.length()-1);
		Assert.assertEquals("extract-function-info", link_0_extractStrategy);
		
		Assert.assertNotNull(i.lookupUncifiedSVar(link_0_extractStrategy));
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
