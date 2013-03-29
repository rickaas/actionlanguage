package org.languages.alng.debug;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.languages.alng.debug.control.AlngDebugServiceFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.spoofax.debug.core.language.IDebugServiceFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.languages.alng.debug"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
		super();
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Starting " + PLUGIN_ID);
		super.start(context);
		//plugin = this; // RL: handled by constructor. Or is it better to do this in start due to performance?
		
		// register our debugger with the spoofax debugger framework
		this.registerDebugger();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private void registerDebugger() {
		String languageID = org.languages.alng.Activator.getInstance().getLanguageID();
		System.out.println("Trying to register debugger for language : " + languageID);
		//Platform.getPlugin(id) // or can we use something like that to lookup the Activator classes
		//Platform.getPlugin(org.spoofax.debug.core.Activator.PLUGIN_ID); // deprecated
		org.spoofax.debug.core.Activator debuggerPluginBase = org.spoofax.debug.core.Activator.getDefault();
		if (debuggerPluginBase == null) {
			System.out.println("Could not get spoofax.debug.core");
		} else {
			System.out.println("Registering with debugger...");
			//debuggerPluginBase.registerDebuggerPlugin(languageID);
			
			debuggerPluginBase.getServiceRegistry().register(languageID, getDebugServiceFactory());
		}
	}
	
	private IDebugServiceFactory debugServiceFactory = null;
	
	public IDebugServiceFactory getDebugServiceFactory() {
		if (debugServiceFactory == null) {
			debugServiceFactory = new AlngDebugServiceFactory();
		}
		return debugServiceFactory;
	}
	
	public static URL getLocation(Bundle bundle, String location) {
	    URL url = FileLocator.find(bundle, new Path(location), null);
	    try {
	        url = FileLocator.resolve(url);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return url;
	}
	
	public static URL getLocation(String location) {
		Bundle bundle = org.languages.alng.debug.Activator.getDefault().getBundle();
	    URL url = FileLocator.find(bundle, new Path(location), null);
	    try {
	        url = FileLocator.resolve(url);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return url;
	}

	public static IPath URLToIPath(URL url) throws IOException {
		URL fileURL = FileLocator.toFileURL(url);
		
		String urlPath = fileURL.getPath();
		IPath path = new Path(urlPath);
		return path;
	}
}
