package util.alng_scripts;

import java.io.IOException;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.terms.ParseError;
import org.spoofax.terms.io.binary.TermReader;
import org.strategoxt.HybridInterpreter;

public abstract class ParsedProgramBase {

	protected IStrategoTerm dslProgramAterm;
	
	public abstract void init();
	
	public void init(IStrategoTerm dslProgramAterm) {
		this.dslProgramAterm = dslProgramAterm;
	}
	
	public void initFromFile(HybridInterpreter i, String atermLocation) throws ParseError, IOException
	{
		IStrategoTerm term = new TermReader(i.getFactory()).parseFromFile(atermLocation);
		this.init(term);
	}
	
	public static ParsedProgramBase createFromFile(TermReader reader, String atermLocation) throws ParseError, IOException
	{
		IStrategoTerm term = reader.parseFromFile(atermLocation);
		return new DSLProgramAsATerm(term);
	}
	
	public static ParsedProgramBase createFromFile(HybridInterpreter i, String atermLocation) throws ParseError, IOException
	{
		return createFromFile(new TermReader(i.getFactory()), atermLocation);
	}
	
	public IStrategoTerm getProgramATerm() {
		return this.dslProgramAterm;
	}
	
}
