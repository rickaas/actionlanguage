package org.languages.alng.runner;

import org.languages.alng.ActionLanguageParseController;
import org.strategoxt.HybridInterpreter;
import org.strategoxt.imp.runtime.Environment;
import org.strategoxt.imp.runtime.dynamicloading.BadDescriptorException;
import org.strategoxt.imp.runtime.dynamicloading.Descriptor;
import org.strategoxt.imp.runtime.parser.SGLRParseController;
import org.strategoxt.imp.runtime.services.StrategoObserver;
import org.strategoxt.imp.runtime.stratego.EditorIOAgent;

/**
 * Returns a HybridInterpreter loaded with a actionlanguage-provider configured in the language description.
 * The returned HybridInterpreter is not the same one that is used by the plugin, it is created using that object as its prototype.
 * @author rlindeman
 *
 */
public class AlngHybridInterpreterProvider implements IHybridInterpreterProvider {

	@Override
	public HybridInterpreter get() {
		return getHybridInterpreter();
	}

	private static HybridInterpreter getHybridInterpreter()
	{
		Descriptor d = ActionLanguageParseController.getDescriptor();
		SGLRParseController controller = null;

		StrategoObserver observer = null;
		try {
			observer = d.createService(StrategoObserver.class, controller);
		} catch (BadDescriptorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (observer == null) return null;
		
		//observer.configureRuntime(project, projectPath)
		HybridInterpreter runtime = observer.getRuntime();
		
		HybridInterpreter i = Environment.createInterpreterFromPrototype(runtime);
		
		// Set the language descriptor so the strategy "plugin-path" works.
		// "plugin-path" returns the path to the ActionLanguage editor plugin
		EditorIOAgent ioDerived = (EditorIOAgent) i.getIOAgent();
		ioDerived.setDescriptor(d);
		// PluginPathPrimitive should now work because it uses:
		// Descriptor descriptor = ((EditorIOAgent) agent).getDescriptor();
		// descriptor.getBasePath().toPortableString();

		return i;
	}
}
