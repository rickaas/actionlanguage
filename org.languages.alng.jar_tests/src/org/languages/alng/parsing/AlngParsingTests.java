package org.languages.alng.parsing;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.strategoxt.HybridInterpreter;

import util.ALTestConstants;
import util.FileUtil;
import util.HybridInterpreterHelper;

public class AlngParsingTests {
	
	private HybridInterpreter i;
	
	@Test
	public void parseFile_justprint() throws IOException
	{
		String alng_program_location = ALTestConstants.ALNG_SCRIPTS_DIR + "/justprint.alng";
		File file = new File(alng_program_location);
		Assert.assertTrue(file.exists());
		
		i = HybridInterpreterHelper.createHybridInterpreter();
		
		i.setCurrent(i.getFactory().makeString(alng_program_location));
		
		boolean b = HybridInterpreterHelper.safeInvoke(i, "parse-actionlanguage-file");
		Assert.assertTrue(b);
		
		org.spoofax.interpreter.terms.IStrategoTerm termOutput = i.current();
		Assert.assertEquals(2, termOutput.getAllSubterms().length);
		
		String stringRepresentation = termOutput.toString();
		Assert.assertEquals(FileUtil.readFile(ALTestConstants.ALNG_SCRIPTS_DIR + "/aterm/justprint/justprint.aterm"), stringRepresentation);
		
		//termOutput.
	}
	
	@Test
	public void parseString_OneLiner()
	{
		i = HybridInterpreterHelper.createHybridInterpreter();
		
		// set the input
		String al_program = "module example entity User { name : String }";
		i.setCurrent(i.getFactory().makeString(al_program));
		
		boolean b = HybridInterpreterHelper.safeInvoke(i, "parse-actionlanguage-string");
		Assert.assertTrue(b);

		org.spoofax.interpreter.terms.IStrategoTerm termOutput = i.current();

		Assert.assertNotNull(termOutput);
		Assert.assertEquals("Module(\"example\",[Entity(\"User\",[Property(\"name\",Type(\"String\"))])])", termOutput.toString());
		
		// lib/editor-common.generated.str
		/*
		  parse-file = parse-actionlanguage-file
		  parse-actionlanguage-file =
		    parse-file(
		      strsglr-perror, strsglr-report-parse-error
		    | <import-term(include/ActionLanguage.tbl)>
		    )

		  parse-string = parse-actionlanguage-string
		  parse-actionlanguage-string =
		    parse-string(
		      strsglr-report-parse-error
		    | <import-term(include/ActionLanguage.tbl)>
		    )
		  
		  parse-stream = parse-actionlanguage-stream
		  parse-actionlanguage-stream =
		    parse-stream(
		      strsglr-report-parse-error
		    | <import-term(include/ActionLanguage.tbl)>
		    )

		  pp-actionlanguage-string =
		    ast2abox(|[<import-term(include/ActionLanguage.generated.pp.af)>,
		               <import-term(include/ActionLanguage.pp.af)>]);
		    box2text-string(|100)
		    */
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
}
