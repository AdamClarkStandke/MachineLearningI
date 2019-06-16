package conceptLearning;

import java.util.ArrayList;
import java.util.Iterator;

public class InBetweenHypos 
{
	private ArrayList<String> one_hypothesis;
	
	public InBetweenHypos(ArrayList<String> hypothesis)
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
		Iterator<String> g_h=one_hypothesis.iterator();
		int size=one_hypothesis.size(); 
		while(g_h.hasNext())
		{
			count++; 
			if(count!= size)
			{
				String g_element = g_h.next(); 
				printout+= g_element + ", ";  
			}
			else
			{
				String g_element = g_h.next();
				printout+= g_element + " >"; 
			}
		}
		return printout; 
	}
}
