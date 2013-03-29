package org.languages.alng.runner;

import org.strategoxt.HybridInterpreter;
import org.strategoxt.imp.runtime.Environment;

public class MyHybridInterpreterProvider implements IHybridInterpreterProvider {

	@Override
	public HybridInterpreter get() {
		return getHybridInterpreter();
	}

	private static HybridInterpreter getHybridInterpreter()
	{
		HybridInterpreter i = null;
		org.strategoxt.imp.runtime.RuntimeActivator runtime = org.strategoxt.imp.runtime.RuntimeActivator.getInstance(); // not null in plugin
		if (runtime == null) {
			System.out.println("Could not get HybridInterpreter from Environment.");
		} else {
			i = Environment.createInterpreter();
		}
		return i;
	}
	
}
