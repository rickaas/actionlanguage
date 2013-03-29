package org.languages.alng.invocation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.spoofax.interpreter.stratego.SDefT;
import org.strategoxt.HybridInterpreter;
import org.strategoxt.IncompatibleJarException;
import org.strategoxt.NoInteropRegistererJarException;

import util.ALTestConstants;

@Ignore
public class InitializeJarTests {

	/**
	 * NewEntity(Type("FooType"))
	 * @param factory
	 * @return
	 */
	public static org.spoofax.interpreter.terms.IStrategoTerm getNewEntityType(org.spoofax.interpreter.terms.ITermFactory factory)
	{
		org.spoofax.interpreter.terms.IStrategoConstructor consType = factory.makeConstructor("Type", 1);
		org.spoofax.interpreter.terms.IStrategoString stringFooType = factory.makeString("FooType");
		org.spoofax.interpreter.terms.IStrategoAppl fooType = factory.makeAppl(consType, stringFooType);
		Assert.assertNotNull(fooType);

		org.spoofax.interpreter.terms.IStrategoConstructor consNewEntityExpr = factory.makeConstructor("NewEntity", 1);
		org.spoofax.interpreter.terms.IStrategoAppl applNewEntity = factory.makeAppl(consNewEntityExpr, fooType);
		
		return applNewEntity;
		
	}
	
	@Test
	public void loadJar_actionlanguage_fail() throws SecurityException, NoInteropRegistererJarException, MalformedURLException, IncompatibleJarException, IOException
	{
		File actionLanguageFile = new File(ALTestConstants.ACTIONLANGUAGE_JAR);
		Assert.assertTrue(actionLanguageFile.exists());
		File actionLanguageJavaFile = new File(ALTestConstants.ACTIONLANGUAGE_JAVA_JAR);
		Assert.assertTrue(actionLanguageJavaFile.exists());
		
		HybridInterpreter i = new HybridInterpreter();
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(2, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(2, i.getContext().getVarScope().getSVars().size());
		Assert.assertEquals("SRTS_EXT_eq_ignore_annos_0_1", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[0]).getName());
		Assert.assertEquals("SRTS_EXT_newint_0_0", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[1]).getName());

		Assert.assertNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar
		
		// load the actionlanguage-java jar
		i.loadJars(actionLanguageJavaFile.toURI().toURL());
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(3, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(3, i.getContext().getVarScope().getSVars().size());
		
		// load the actionLanguage jar
		try {
		i.loadJars(actionLanguageFile.toURI().toURL());
		Assert.fail("Loading action language jar fails because it requires libdsldi.jar to be loaded.");
		} catch(Exception e)
		{
			//System.out.println(e.getMessage());
			Assert.assertEquals(IncompatibleJarException.class, e.getClass());
			Assert.assertNotNull(e.getCause());
			Assert.assertEquals(NoClassDefFoundError.class, e.getCause().getClass());
			NoClassDefFoundError noClass = (NoClassDefFoundError) e.getCause();
			Assert.assertEquals("org/strategoxt/imp/debug/instrumentation/trans/Main", noClass.getMessage());

		}
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());

		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(3, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(3, i.getContext().getVarScope().getSVars().size());

		
		// Strategy should now exist
		Assert.assertNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(3, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(3, i.getContext().getVarScope().getSVars().size());
	}
	
	@Test
	public void loadJar_actionlanguage() throws SecurityException, NoInteropRegistererJarException, MalformedURLException, IncompatibleJarException, IOException
	{
		File libdsldiFile = new File(ALTestConstants.LIBDSLDI_JAR);
		Assert.assertTrue(libdsldiFile.exists());
		File actionLanguageFile = new File(ALTestConstants.ACTIONLANGUAGE_JAR);
		Assert.assertTrue(actionLanguageFile.exists());
		File actionLanguageJavaFile = new File(ALTestConstants.ACTIONLANGUAGE_JAVA_JAR);
		Assert.assertTrue(actionLanguageJavaFile.exists());
		
		HybridInterpreter i = new HybridInterpreter();
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(2, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(2, i.getContext().getVarScope().getSVars().size());
		Assert.assertEquals("SRTS_EXT_eq_ignore_annos_0_1", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[0]).getName());
		Assert.assertEquals("SRTS_EXT_newint_0_0", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[1]).getName());

		Assert.assertNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar
		
		// load the actionlanguage-java jar
		i.loadJars(actionLanguageJavaFile.toURI().toURL());
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(3, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(3, i.getContext().getVarScope().getSVars().size());
		
		// load the libdsldi jar
		i.loadJars(libdsldiFile.toURI().toURL());
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());

		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(250, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(250, i.getContext().getVarScope().getSVars().size());
		
		// load the actionLanguage jar
		i.loadJars(actionLanguageFile.toURI().toURL());
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());

		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(431, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(431, i.getContext().getVarScope().getSVars().size());

		
		// Strategy should now exist
		Assert.assertNotNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNotNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNotNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(431, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(431, i.getContext().getVarScope().getSVars().size());
	}
	
	@Test
	public void loadJar_dsldi() throws SecurityException, NoInteropRegistererJarException, MalformedURLException, IncompatibleJarException, IOException
	{
		File dsldiFile = new File(ALTestConstants.DSLDI_JAR);
		Assert.assertTrue(dsldiFile.exists());
		
		HybridInterpreter i = new HybridInterpreter();
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(2, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(2, i.getContext().getVarScope().getSVars().size());
		Assert.assertEquals("SRTS_EXT_eq_ignore_annos_0_1", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[0]).getName());
		Assert.assertEquals("SRTS_EXT_newint_0_0", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[1]).getName());


		Assert.assertNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar
		
//		// load the dsldi-java jar
//		i.loadJars(actionLanguageJavaFile.toURI().toURL());
//		Assert.assertNotNull(i.getContext().getVarScope());
//		Assert.assertNull(i.getContext().getVarScope().getParent());
//		
//		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
//		Assert.assertEquals(3, i.getContext().getVarScope().getStrategyDefinitions().size());
//		Assert.assertEquals(3, i.getContext().getVarScope().getSVars().size());
		
		// load the dsldi jar
		i.loadJars(dsldiFile.toURI().toURL());
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());

		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(342, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(342, i.getContext().getVarScope().getSVars().size());

		
		// Strategy should now exist
		Assert.assertNotNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNotNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(342, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(342, i.getContext().getVarScope().getSVars().size());
	}
	
	@Test
	public void loadJar_libdsldi() throws SecurityException, NoInteropRegistererJarException, MalformedURLException, IncompatibleJarException, IOException
	{
		File libdsldiFile = new File(ALTestConstants.LIBDSLDI_JAR);
		Assert.assertTrue(libdsldiFile.exists());
		
		HybridInterpreter i = new HybridInterpreter();
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(2, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(2, i.getContext().getVarScope().getSVars().size());
		Assert.assertEquals("SRTS_EXT_eq_ignore_annos_0_1", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[0]).getName());
		Assert.assertEquals("SRTS_EXT_newint_0_0", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[1]).getName());


		Assert.assertNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar
		
		// load the libdsldi jar
		i.loadJars(libdsldiFile.toURI().toURL());
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());

		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(249, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(249, i.getContext().getVarScope().getSVars().size());

		
		// Strategy should now exist
		Assert.assertNotNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(249, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(249, i.getContext().getVarScope().getSVars().size());
	}
	
	@Test
	public void loadJarInInterpreter() throws SecurityException, NoInteropRegistererJarException, MalformedURLException, IncompatibleJarException, IOException
	{
		File libdsldiFile = new File(ALTestConstants.LIBDSLDI_JAR);
		Assert.assertTrue(libdsldiFile.exists());

		File actionLanguageFile = new File(ALTestConstants.ACTIONLANGUAGE_JAR);
		Assert.assertTrue(actionLanguageFile.exists());


		HybridInterpreter i = new HybridInterpreter();
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(2, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(2, i.getContext().getVarScope().getSVars().size());
		Assert.assertEquals("SRTS_EXT_eq_ignore_annos_0_1", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[0]).getName());
		Assert.assertEquals("SRTS_EXT_newint_0_0", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[1]).getName());

		
		
		Assert.assertNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar
		
		
		// load the libdsldi jar
		i.loadJars(libdsldiFile.toURI().toURL());
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());

		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(249, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(249, i.getContext().getVarScope().getSVars().size());
		//Assert.assertEquals("SRTS_EXT_eq_ignore_annos_0_1", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[0]).getName());
		//Assert.assertEquals("SRTS_EXT_newint_0_0", ((SDefT)i.getContext().getVarScope().getStrategyDefinitions().toArray()[1]).getName());
		
		// Strategy should now exist
		Assert.assertNotNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar
		
		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(249, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(249, i.getContext().getVarScope().getSVars().size());
		
		// load the language specific jar
		i.loadJars(actionLanguageFile.toURI().toURL());
		Assert.assertNotNull(i.getContext().getVarScope());
		Assert.assertNull(i.getContext().getVarScope().getParent());

		Assert.assertEquals(0, i.getContext().getVarScope().getVarNames().size());
		Assert.assertEquals(430, i.getContext().getVarScope().getStrategyDefinitions().size());
		Assert.assertEquals(430, i.getContext().getVarScope().getSVars().size());

		
		Assert.assertNotNull(i.lookupUncifiedSVar("handle-sel-spec")); // from libdsldi.jar
		Assert.assertNotNull(i.lookupUncifiedSVar("parse-actionlanguage-file")); // from actionlanguage.jar
		Assert.assertNotNull(i.lookupUncifiedSVar("extract-function-info")); // from actionlanguage.jar
		Assert.assertNull(i.lookupUncifiedSVar("show-extract-transformations")); // from dsldi.jar

		
	}
	

}
