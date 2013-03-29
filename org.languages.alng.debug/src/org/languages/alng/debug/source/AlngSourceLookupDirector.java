package org.languages.alng.debug.source;

import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;
import org.eclipse.jdt.launching.sourcelookup.containers.JavaSourceLookupParticipant;

public class AlngSourceLookupDirector extends AbstractSourceLookupDirector {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceLookupDirector#initializeParticipants()
	 */
	public void initializeParticipants() {
		addParticipants(new ISourceLookupParticipant[]{
				new AlngSourceLookupParticipant(),
				new JavaSourceLookupParticipant()
				});
	}

}
