package org.languages.alng.invocation;


public class JarsOnClasspathTests {
	
//	@Test
//	public void loadAlngJarsAnExecuteStrategy_2()
//	{
//		org.strategoxt.lang.Context context = trans.Main.init();
//		Assert.assertNotNull(context);
//		
//		HybridInterpreter interpreter = HybridInterpreter.getInterpreter(context);
//		// This does not work
//		Assert.assertNull(interpreter);
//	}
//	
//	
//	
//	@Test
//	public void loadAlngJarsAnExecuteStrategy_1()
//	{
//		// init the Context
//		org.strategoxt.lang.Context context = trans.Main.init();
//		Assert.assertNotNull(context);
//		
//		
//		/*
//		java.util.List<org.spoofax.interpreter.library.IOperatorRegistry> l = context.getOperatorRegistries();
//		for(org.spoofax.interpreter.library.IOperatorRegistry opRegister : l)
//		{
//			String name = opRegister.getOperatorRegistryName();
//			System.out.println("OpReg: " + name);
//		}
//		*/
//		
//		try {
//
//			HybridInterpreter i = new HybridInterpreter();
//			trans.Main.registerInterop(i.getContext(), i.getCompiledContext());
//			
//			// set the input
//			i.setCurrent(getNewEntityType(i.getFactory()));
//			
//
//			// org.languages.alng/trans/generate.str
//			//     gen-expr: NewEntity(Type(t)) -> $[new [t]()]
//			// input: NewEntity(Type("FooType"))
//			// result: new FooType()
//			boolean b = i.invoke("gen-expr");
//			Assert.assertTrue(b);
//
//			org.spoofax.interpreter.terms.IStrategoTerm termOutput = i.current();
//			
//			
//			Assert.assertNotNull(termOutput);
//			Assert.assertEquals(i.getFactory().makeString("new FooType()"), termOutput);
//			
//		} catch (InterpreterErrorExit e) {
//			e.printStackTrace();
//			Assert.fail(e.getMessage());
//		} catch (InterpreterExit e) {
//			e.printStackTrace();
//			Assert.fail(e.getMessage());
//		} catch (UndefinedStrategyException e) {
//			e.printStackTrace();
//			Assert.fail(e.getMessage());
//		} catch (InterpreterException e) {
//			e.printStackTrace();
//			Assert.fail(e.getMessage());
//		}
//		
//		/*
//		// only use to call java methods, (I think...)
//		org.spoofax.interpreter.terms.IStrategoTerm term0 = context.invokeStrategyCLI(trans.gen_expr_0_0.instance, "module_name", "NewEntity(Type(\"FooType\"))");
//		Assert.assertNotNull(term0);
//		System.out.println(term0);
//		*/
//		
//		/*
//		//     gen-expr: NewEntity(Type(t)) -> $[new [t]()]
//		// input: NewEntity(Type("FooType"))
//		// result: new FooType()
//		org.spoofax.interpreter.terms.IStrategoTerm term1 = context.invokeStrategy("gen-expr", applNewEntity);
//		Assert.assertNotNull(term1);
//		*/
//	}
}
