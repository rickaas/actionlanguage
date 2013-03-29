package org.languages.alng.instrumentation.programs;

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.languages.alng.instrumentation.events.EventInstrumentationBase;

import util.ALTestConstants;

public class ProgramInstrumentBase extends EventInstrumentationBase {

	protected String sourceDSLLocation = null;
	
	protected String getGeneratedLocation()
	{
		return ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR +"/" + sourceDSLLocation;
	}
	
	protected void cleanupGenerated()
	{
		File f = new File(getGeneratedLocation());
		if (f.exists())
		{
			Assert.assertTrue(f.delete());
		}
		Assert.assertFalse(f.exists());
	}
	
	
	@Before
	public void setup()
	{
		initHI();
		
		// register extract and generate strategies:
		registerExtractFunction("extract-statement-info");
		registerExtractFunction("extract-function-info");
		registerExtractFunction("extract-function-argument-info");
		registerExtractFunction("extract-var-info");
		
		registerGenerateFunction("gen-step");
		registerGenerateFunction("gen-enter");
		registerGenerateFunction("gen-exit");
		registerGenerateFunction("gen-function-argument");
		registerGenerateFunction("gen-var");
		
		loadDSLDI(ALTestConstants.ALNG_SCRIPTS_DIR + "/dsldis/ActionLanguage.dsldi");
		this.setOutputBasePath(ALTestConstants.ALNG_SCRIPTS_GENERATED_DIR);
		this.setLanguageID("ActionLanguage");
		
		this.setPostInstrumentation("actionlanguage-post-instrumentation");
		//this.addWriteFile("ActionLanguage", "write-actionlanguage-to-file");
		this.addWriteFile("ActionLanguage", "write-actionlanguage-as-dsl");
		//this.addWriteFile("ActionLanguage", "write-actionlanguage-as-ast");
	}
}
