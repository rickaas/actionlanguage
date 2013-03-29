package alng_parsing;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import util.ALTestConstants;


public class JustPrintParsing {

	/**
	 * Check if the file exists
	 */
	@Test
	public void openFileInJava()
	{
		File file = new File(ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng");
		Assert.assertTrue(file.exists());
	}
	



	
	@Test
	public void executeStrategy_CompileAndRunJavaCode() {
		//compile-and-run-java-code = ?(java-filename, bin-directory, classname, java-code, stdout-filename);
	}
	
	@Test
	public void executeStrategy_RunProgramAst()
	{
		/*
		  //Current term is an ActionLanguage ast
		  run-program-ast =
		      ?ast ;
		      */
	}
}
