package conceptLearning;

import java.util.ArrayList;
import java.util.Iterator;

public class SpecificHypo 
{
	private ArrayList<String> one_hypothesis;
	public SpecificHypo(ArrayList<String> hypothesis)
	{
		one_hypothesis=hypothesis; 
	}
	public ArrayList<String> getHypothesis()
	{
		return one_hypothesis; 
	}
	
	public void setHypothesis(int index, String value)
	{
		one_hypothesis.remove(index); 
		one_hypothesis.set(index, value); 
	}
	
	@Override
	public String toString()
	{
		int count=0; 
		String printout= "< ";
		Iterator<String> s_h=one_hypothesis.iterator();
		int size=one_hypothesis.size(); 
		while(s_h.hasNext())
		{
			count++; 
			if(count!= size)
			{
				String s_element = s_h.next(); 
				printout+= s_element + ", ";  
			}
			else
			{
				String s_element = s_h.next();
				printout+= s_element + " >"; 
			}
		}
		return printout; 
	}
	
}
