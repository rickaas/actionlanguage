package org.languages.alng.debug.launching;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IVMConnector;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.languages.alng.debug.ActionLanguageConstants;
import org.languages.alng.debug.control.AlngDebugServiceFactory;
import org.languages.alng.debug.model.AlngDebugTarget;
import org.spoofax.debug.core.control.launching.IJavaProgramLaunchPreparation;
import org.spoofax.debug.core.control.launching.IJavaProgramPrepareResult;
import org.spoofax.debug.core.eclipse.JVMConnector;
import org.spoofax.debug.core.eclipse.JVMConnector.IJVMLaunched;
import org.spoofax.debug.core.language.LIConstants;
import org.spoofax.debug.core.launching.LILaunchDelegate;
import org.spoofax.debug.core.launching.LaunchUtil;

import com.sun.jdi.VirtualMachine;

/**
 * The delegate extends the AbstractJavaLaunchConfigurationDelegate as this simplifies how to use a JVM
 * 
 * @author rlindeman
 *
 */
public class AlngLaunchDelegate extends LILaunchDelegate implements IJVMLaunched{
	

	private ILaunch launch;
	private String mode;
	
	public void launchVMThenAddDebugTargets(IProgressMonitor monitor, 
			String classname, 
			String[] classpath, 
			ILaunch launch,
			String mode) throws CoreException {
		this.launch = launch;
		this.mode = mode;
		
        if (monitor == null){
            monitor = new NullProgressMonitor();
        }
        monitor.beginTask("Starting debugger for "+this.getLanguageName()+" program", IProgressMonitor.UNKNOWN);
        
		for(IVMConnector c : JavaRuntime.getVMConnectors()) {
			System.out.println(c.getIdentifier());
			System.out.println(c.getName());
		}
		
		// no program arguments supported
		String[] alngProgramArguments = new String[] {};
		
		// Initialize the VMRunner
		IVMInstall defaultInstall = JavaRuntime.getDefaultVMInstall();
		IVMRunner vmRunner = defaultInstall.getVMRunner(ILaunchManager.RUN_MODE); // always use RUN, so we can control the debug parameters of the VM
		// configure the vm arguments
		VMRunnerConfiguration vmRunnerConfiguration = new VMRunnerConfiguration(classname, classpath);
		vmRunnerConfiguration.setProgramArguments(alngProgramArguments);
		
		// the started wm will wait for a debugger to connect to this port
		String port = ""+LaunchUtil.findFreePort();
		
		// if we are in DEBUG_MODE also set the debugging parameters for the VM as we previously created an IVMRunner in RUN_MODE
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			// socket listen, vm will wait untill a debugger is attached
			String[] realVMargs = new String[] { "-Xdebug", "-Xrunjdwp:transport=dt_socket,address="+port+",suspend=y", "-Xss8m" };
			vmRunnerConfiguration.setVMArguments(realVMargs);
		}
		
		
		JVMConnector connector = new JVMConnector(this, port, this.getLIConstants().getDebugModel());
		System.out.println("try connecting...");
		connector.tryConnecting(); // socket listen

		System.out.println("THE JVM will only launch if it can connect to the listener...");
		vmRunner.run(vmRunnerConfiguration, launch, monitor);
//		String connectorId = getVMConnectorId(configuration);
//		IVMConnector connector = null;
//		if (connectorId == null) {
//			connector = JavaRuntime.getDefaultVMConnector();
//		} else {
//			connector = JavaRuntime.getVMConnector(connectorId);
//		}
		
		monitor.done();
	}
	
	public void connected(VirtualMachine jvm) {
		if (jvm == null) {
			return; // This method could be called from another thread, we could throw an Exception but we don't know who will catch it...
		}
		//called when connected to vm
		System.out.println("connected: "+ jvm.name());

		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			
			AlngDebugTarget alngTarget = new AlngDebugTarget(null, AlngDebugServiceFactory.getLanguageID(), this.launch, jvm);
			this.launch.addDebugTarget(alngTarget);

//			// RL: experimental, also add a java debug target??
//			monitor.subTask("Attaching to the ActionLanguage VM");
//			LIDebugTarget target = Activator.getDefault().getDebugServiceFactory().createDebugTarget(launch, port);
//			//AlngDebugTarget target = new AlngDebugTarget(launch, port);
//			//(launch,p,requestPort,eventPort );
//			launch.addDebugTarget(target);
//			monitor.worked(1);
//			
//			// JDI debug target requires a vm and process...
//			boolean allowTerminate = false;
//			boolean allowDisconnect = false;
//			boolean resume = false;
			// resume is optional, defaults to true
			//IDebugTarget target = JDIDebugModel.newDebugTarget(launch, jvm, "Java backend for ActionLanguage", null, allowTerminate, allowDisconnect, resume);
//			// newDebugTarget(ILaunch launch, com.sun.jdi.VirtualMachine vm, String name, IProcess process, boolean allowTerminate, boolean allowDisconnect) 
//			// newDebugTarget(ILaunch launch, com.sun.jdi.VirtualMachine vm, String name, IProcess process, boolean allowTerminate, boolean allowDisconnect, boolean resume)
			
		}
	}
	
	public void launchVM(IProgressMonitor monitor, 
			String classname, 
			String[] classpath, 
			ILaunch launch,
			String mode) throws CoreException {
		this.launchVMThenAddDebugTargets(monitor, classname, classpath, launch, mode);
//		boolean s = 1==(2-1);
//		if (s) return;
//		
//		String[] alngProgramArguments = new String[] {};
//		
//		// Initialize the VMRunner
//		IVMInstall defaultInstall = JavaRuntime.getDefaultVMInstall();
//		IVMRunner vmRunner = defaultInstall.getVMRunner(ILaunchManager.RUN_MODE); // always use RUN, so we can control the debug parameters of the VM
//		
//		VMRunnerConfiguration vmRunnerConfiguration = new VMRunnerConfiguration(classname, classpath);
//		vmRunnerConfiguration.setProgramArguments(alngProgramArguments);
//		// TODO: working directory should be the project directory
//		//vmRunnerConfiguration.setWorkingDirectory(path)
//		
//		// the started wm will wait for a debugger to connect to this port
//		String port = ""+LaunchUtil.findFreePort();
//		
//		// if we are in DEBUG_MODE also set the debugging parameters for the VM as we previously created an IVMRunner in RUN_MODE
//		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
//			// socket attach
//			//String[] realVMargs = new String[] { "-Xdebug", "-Xrunjdwp:transport=dt_socket,address="+port+",server=y,suspend=y" };
//			// socket listen, vm will wait untill a debugger is attached
//			String[] realVMargs = new String[] { "-Xdebug", "-Xrunjdwp:transport=dt_socket,address="+port+",suspend=y", "-Xss8m" };
//		//String[] realVMargs = new String[] { "-Xrunjdwp:transport=dt_socket,address=9000,server=y,suspend=y" };
//		//String[] realVMargs = new String[] { "-Xdebug" };
//			vmRunnerConfiguration.setVMArguments(realVMargs);
//		}
//		
//		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
//			monitor.subTask("Attaching to the ActionLanguage VM");
//			LIDebugTarget target = Activator.getDefault().getDebugServiceFactory().createDebugTarget(launch, port);
//			//AlngDebugTarget target = new AlngDebugTarget(launch, port);
//			//(launch,p,requestPort,eventPort );
//			launch.addDebugTarget(target);
//			monitor.worked(1);
//			
//			// RL: experimental, also add a java debug target??
//			// JDI debug target requires a vm and process...
//			// Lets move this to LIDebugTarget
//			//JDIDebugModel.newDebugTarget(launch, vm, name, process, allowTerminate, allowDisconnect)
//			// newDebugTarget(ILaunch launch, com.sun.jdi.VirtualMachine vm, String name, IProcess process, boolean allowTerminate, boolean allowDisconnect) 
//			// newDebugTarget(ILaunch launch, com.sun.jdi.VirtualMachine vm, String name, IProcess process, boolean allowTerminate, boolean allowDisconnect, boolean resume) 
//		}
//		
//		// start the VM with the alng program
//		// using attach, run before the StrategoDebugTarget is initialized
//		// using listen, run after the StrategoDebugTarget is initialized
//		System.out.println("RUN");
//		vmRunner.run(vmRunnerConfiguration, launch, monitor);
//		
//		monitor.worked(1);
	}

	@Override
	public String getLanguageName() {
		return ActionLanguageConstants.getLanguageID();
	}

	@Override
	public LIConstants getLIConstants() {
		return org.languages.alng.debug.Activator.getDefault().getDebugServiceFactory().getLIConstants();
	}

	@Override
	public IJavaProgramLaunchPreparation getLaunchPreparation() {
		return new AlngLaunchPreparation();
	}

	public String[] getClasspaths(IJavaProgramPrepareResult result) {

		try {
			String binDirectory = result.getBinDirectory(); // test/generated_programs/java/release/tiny/bin
			
			// stratego.debug.runtime
			URL url_alngruntime = org.languages.alng.FileUtil.getLocation("lib/runtime/alngruntime.jar");
			File file_alngruntime = org.languages.alng.FileUtil.URLToIPath(url_alngruntime).toFile();

			// spoofax.debug.runtime
			URL url_spoofaxdebug = null;
			url_spoofaxdebug = org.spoofax.debug.instrumentation.util.JavaDebugLibraryJarLocation.getInterfacesPath();
			File file_spoofaxdebug = org.languages.alng.debug.Activator.URLToIPath(url_spoofaxdebug).toFile();
			
			URL url_spoofaxjavadebug = null;
			url_spoofaxjavadebug = org.spoofax.debug.instrumentation.util.JavaDebugLibraryJarLocation.getJavaInterfacesPath();
			File file_spoofaxjavadebug = org.languages.alng.debug.Activator.URLToIPath(url_spoofaxjavadebug).toFile();
		
			// the binDirectory is relative to the project directory "test/generated_programs/java/release/tiny/bin"
			//String binDirectory = "/home/rlindeman/Applications/development/eclipse/helios_sdk/workspace/org.languages.alng.debug.test/test/generated_programs/java/debug/assigninblock/bin";
			IFile binDirectoryFile = this.getProject().getFile(binDirectory);
			System.out.println("bin : " + binDirectoryFile.getLocation().toOSString());
			System.out.println("bin : " + binDirectoryFile.getFullPath().toOSString());
			System.out.println("bin : " + binDirectoryFile.getLocation().toFile().getAbsolutePath());
			
			String[] classpath = new String[] {
					binDirectoryFile.getLocation().toOSString(),
					file_alngruntime.getCanonicalPath(),
					file_spoofaxdebug.getCanonicalPath(),
					file_spoofaxjavadebug.getCanonicalPath()
			};
			return classpath;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		throw new RuntimeException("Could not determine runtime classpath for application");
	}


}
