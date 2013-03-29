package org.languages.alng.runner;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.lang.Context;

/**
 * Overrides the strategy plugin-path in ActionLanguage.
 * The plugin-path directory should contain:
 *  "lib/runtime/alngruntime.jar" and "lib/runtime/org.spoofax.debug.java.library.jar"
 * @author rlindeman
 *
 */
public class PluginPathOverride extends trans.plugin_path_0_0 {

	public static void override()
	{
		trans.plugin_path_0_0.instance = new PluginPathOverride();
	}
	
	public static void undo()
	{
		trans.plugin_path_0_0.instance = new trans.plugin_path_0_0();
	}
	
	
	@Override
	public IStrategoTerm invoke(Context context, IStrategoTerm term) {
		String s = ".";
		IStrategoTerm t = context.getFactory().makeString(s);
		return t;
	}
	
	public static boolean isOverridden()
	{
		return (trans.plugin_path_0_0.instance instanceof PluginPathOverride);
	}
}
