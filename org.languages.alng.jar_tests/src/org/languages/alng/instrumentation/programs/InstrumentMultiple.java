package org.languages.alng.instrumentation.programs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.HybridInterpreter;

import util.ALTestConstants;
import util.HybridInterpreterHelper;

public class InstrumentMultiple {
	
	protected HybridInterpreter i = null;
	protected void initHI()
	{
		// init the HybridInterpreter
		i = HybridInterpreterHelper.createHybridInterpreter();
	}
	
	private String sourceAppDirectory = null;
	
	private String getGeneratedLocation() {
		return ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR +"/" + sourceAppDirectory;
	}
	
	private String getInstrumentedLocation() {
		return ALTestConstants.ALNG_SCRIPTS_INSTRUMENTED_DIR +"/" + sourceAppDirectory;
	}
	
	protected IStrategoTerm makeConfigTuple(String key, String value) {
		return i.getFactory().makeTuple(i.getFactory().makeString(key), i.getFactory().makeString(value));
	}
	
	@Test
	public void instrumentPerformance01_MainScript() {
		this.initHI();
		
		i.setCurrent(i.getFactory().makeString("foo"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "init-config"));
		
		// <set-config> ("--sel",)
		// get-config-sel
		String dsldi = ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/ActionLanguage.dsldi";
		i.setCurrent(makeConfigTuple("--sel", dsldi));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		// <set-config> ("--file-extension",)
		// get-config-file-extension
		i.setCurrent(makeConfigTuple("--file-extension", "alng"));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		// <set-config> ("--input-dir",)
		// get-config-input-dir
		// WebdslTestConstants.WEBDSL_SCRIPTS_TESTCASES_DIR + "/programs/performance01";
		sourceAppDirectory = ALTestConstants.ALNG_SCRIPTS_DIR + "/testcases/programs/apps/group";
		i.setCurrent(makeConfigTuple("--input-dir", sourceAppDirectory));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));
		
		// <set-config> ("--output-dir",)
		// get-config-output-dir
		String output = getGeneratedLocation() + ".alng";
		i.setCurrent(makeConfigTuple("--output-dir", output));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));

		// <set-config> ("--fake-run",)
		// get-config-is-fake-run
		//i.setCurrent(makeConfigTuple("--fake-run", "true"));
		//Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-config"));

		// set-verbosity
		
		// Vomit
		//i.setCurrent(i.getFactory().makeInt(10));
		// verbose-level : Debug()     -> 4
		// Debug
		i.setCurrent(i.getFactory().makeInt(4));
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "set-verbosity"));

		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "execute"));

		// extract filenames, returns a list of strings
		Assert.assertTrue(HybridInterpreterHelper.safeInvoke(i, "getFilenamesWithSuccess"));
		List<String> instrumentedFiles = new ArrayList<String>();

		if (i.current() instanceof IStrategoList) {
			IStrategoList list = (IStrategoList)i.current();
			for(int i = 0; i < list.size(); i++) {
				IStrategoTerm t = list.getSubterm(i);
				if (t.getTermType() == IStrategoTerm.STRING) {
					IStrategoString s = (IStrategoString) t;
					instrumentedFiles.add(s.stringValue());
				}
			}
		}
		Assert.assertEquals(2, instrumentedFiles.size());
		for(String filename : instrumentedFiles) {
			compareFileContents(getInstrumentedLocation() + ".alng/" + filename, output + "/" + filename);
		}
		System.out.println("F");
	}
	
	@After
	public void tearDownInterpreter() {
		i.uninit();
		i.shutdown();
	}
	
	protected void compareFileContents(String expected, String actual) {
		boolean b = false;
		
		Assert.assertTrue("File " + expected + " does not exist.", (new File(expected)).exists());
		Assert.assertTrue("File " + actual + " does not exist.", (new File(actual)).exists());
		

		i.setCurrent(i.getFactory().makeString(expected));
		b = HybridInterpreterHelper.safeInvoke(i, ALTestConstants.PARSE_AL_FILE);
		Assert.assertTrue(b);
		IStrategoTerm expectedATerm = i.current();

		i.setCurrent(i.getFactory().makeString(actual));
		b = HybridInterpreterHelper.safeInvoke(i, ALTestConstants.PARSE_AL_FILE);
		Assert.assertTrue(b);
		IStrategoTerm actualATerm = i.current();

		Assert.assertEquals(expectedATerm.toString(), actualATerm.toString());
	}
}
