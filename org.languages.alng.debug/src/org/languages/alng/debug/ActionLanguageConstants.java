package org.languages.alng.debug;

import org.spoofax.debug.core.language.LIConstants;

public class ActionLanguageConstants {

	/**
	 * Unique identifier for the ActionLanguage debug model (value 
	 * <code>org.languages.alng.debug</code>).
	 */
	public static final String ID_ALNG_DEBUG_MODEL = "org.languages.alng.debug";
	
	/**
	 * Launch configuration key. Value is a path to a ActionLanguage
	 * program. The path is a string representing a full path
	 * to a ActionLanguage program in the workspace. 
	 */
	public static final String ATTR_ALNG_PROGRAM = ID_ALNG_DEBUG_MODEL + ".ATTR_ALNG_PROGRAM";
	
	// org.languages.alng.debug.launchConfigurationType
	public static final String ALNG_LAUNCH_CONFIG_TYPE = ID_ALNG_DEBUG_MODEL + ".launchConfigurationType";
	
	public static final String ALNG_LINEBREAKPOINT_MARKER = ID_ALNG_DEBUG_MODEL + ".lineBreakpoint.marker";
	
	public static String getLanguageID() {
		//String languageID = webdsl.Activator.getInstance().getLanguageID();
		return "ActionLanguage";
	}
	
	public static class AlngAttributes implements LIConstants {

		@Override
		public String getProgram() {
			return ATTR_ALNG_PROGRAM;
		}
		
		@Override
		public String getDebugModel() {
			return ID_ALNG_DEBUG_MODEL;
		}

		@Override
		public String getLineBreakpointMarker() {
			return ALNG_LINEBREAKPOINT_MARKER;
		}
	}
}
