package org.languages.alng.debug.launching;

import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchManager;
import org.languages.alng.runner.AlngHybridInterpreterProvider;
import org.languages.alng.runner.CompilationResultWrapper;
import org.languages.alng.runner.LaunchProgram;
import org.spoofax.debug.core.control.launching.IJavaProgramLaunchPreparation;
import org.spoofax.debug.core.control.launching.IJavaProgramPrepareResult;
import org.spoofax.debug.core.control.launching.LaunchPreparationException;
import org.spoofax.interpreter.core.InterpreterErrorExit;
import org.spoofax.interpreter.core.InterpreterException;
import org.spoofax.interpreter.core.InterpreterExit;
import org.spoofax.interpreter.core.UndefinedStrategyException;


public class AlngLaunchPreparation implements IJavaProgramLaunchPreparation {

	public IJavaProgramPrepareResult prepare(String projectName, IPath alngProgramPath, String mode) throws LaunchPreparationException {
		LaunchProgram l = new LaunchProgram(new AlngHybridInterpreterProvider());
		
		final CompilationResultWrapper compilationResult;
		IJavaProgramPrepareResult result = null;
		
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			try {
				compilationResult = l.compileForDebug(alngProgramPath.toOSString());
			} catch (InterpreterErrorExit e) {
				throw new LaunchPreparationException("Compiling for debug failed", e);
			} catch (InterpreterExit e) {
				throw new LaunchPreparationException("Compiling for debug failed", e);
			} catch (UndefinedStrategyException e) {
				throw new LaunchPreparationException("Compiling for debug failed", e);
			} catch (InterpreterException e) {
				throw new LaunchPreparationException("Compiling for debug failed", e);
			}
		} else if (mode.equals(ILaunchManager.RUN_MODE)) {
			try {
				compilationResult = l.compileForRun(alngProgramPath.toOSString());

			} catch (InterpreterErrorExit e) {
				throw new LaunchPreparationException("Compiling for run failed", e);
			} catch (InterpreterExit e) {
				throw new LaunchPreparationException("Compiling for run failed", e);
			} catch (UndefinedStrategyException e) {
				throw new LaunchPreparationException("Compiling for run failed", e);
			} catch (InterpreterException e) {
				throw new LaunchPreparationException("Compiling for run failed", e);
			}
		} else {
			compilationResult = null;
		}
		
		if (compilationResult == null) throw new LaunchPreparationException("No compilation result returned while preparing program for launch.");
		
		result = new IJavaProgramPrepareResult() {
			
			@Override
			public String getClassname() {
				return compilationResult.getClassname();
			}
			
			@Override
			public String getBinDirectory() {
				return compilationResult.getBinDirectory();
			}
		};
		
		return result;
	}

}
