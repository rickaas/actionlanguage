package util.alng_scripts;

import org.spoofax.interpreter.terms.IStrategoTerm;

public class DSLProgramAsATerm extends ParsedProgramBase {

	public DSLProgramAsATerm(IStrategoTerm term)
	{
		this.dslProgramAterm = term;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
