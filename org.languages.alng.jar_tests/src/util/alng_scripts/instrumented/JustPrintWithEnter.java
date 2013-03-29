package util.alng_scripts.instrumented;


/**
 * Instrumented version 
 * @author rlindeman
 *
 */
public class JustPrintWithEnter {

//	public void init()
//	{
//		String alng_program_location = ALTestConstants.ALNG_SCRIPTS_DIR + "/instrumented/justprint_with_enter.alng";
//		File alngFile = new File(alng_program_location);
//		Assert.assertTrue(alngFile.exists());
//		
//		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
//		
//		i.setCurrent(i.getFactory().makeString(alng_program_location));
//		boolean b = HybridInterpreterHelper.safeInvoke(i, "parse-actionlanguage-file");
//		Assert.assertTrue(b);
//		this.dslProgramAterm = i.current();
//		
//		i.uninit();
//	}
//	
//	// returns two statements
//	// public IStrategoTerm getFunctionBody()
//	
//	
//	@Test
//	public void testReadFromAterm() throws ParseError, IOException
//	{
//		HybridInterpreter i = HybridInterpreterHelper.createHybridInterpreter();
//		TermReader reader = new TermReader(i.getFactory());
//		String atermLocation = ALTestConstants.ALNG_SCRIPTS_DIR + "/aterm/justprint_with_enter.aterm";
//		this.initFromFile(reader, atermLocation);
//		
//		// the sorts are unknown
//		
//		Assert.assertNotNull(this.getModule());
//		Assert.assertEquals("Module", SELUtil.getConstructor(this.getModule()));
//		Assert.assertEquals(null, SELUtil.getSort(this.getModule()));
//		
//		Assert.assertNotNull(this.getFunctionDeclVoid());
//		Assert.assertEquals("FunctionDeclVoid", SELUtil.getConstructor(this.getFunctionDeclVoid()));
//		Assert.assertEquals(null, SELUtil.getSort(this.getFunctionDeclVoid()));
//		
//		Assert.assertNotNull(this.getFunctionBody());
//		Assert.assertEquals(null, SELUtil.getConstructor(this.getFunctionBody())); // it is a list
//		Assert.assertEquals(null, SELUtil.getSort(this.getFunctionBody()));
//		
//		Assert.assertNotNull(this.getStatement());
//		Assert.assertEquals("DebugEventCall", SELUtil.getConstructor(this.getStatement()));
//		Assert.assertEquals(null, SELUtil.getSort(this.getStatement()));
//	}
}
