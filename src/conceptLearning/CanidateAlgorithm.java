package conceptLearning;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import com.opencsv.CSVReader;

/**						
 * @author Adam Standke
 *
 */
public class CanidateAlgorithm {

	/**
	 * Implementation of Candidate-Elimination Algorithm: This 
	 * implementation of the  Candidate-Elimination Algorithm is
	 * used to create a version space consisting of a 
	 * specific hypothesis boundary and general hypothesis boundary. 	
	 * @param instances each training instance to be passed in (ie., online learning method) 
	 * @param G  ArrayList that contains all of the general hypotheses in the overall hypothesis space
	 * @param S  ArrayList that contains all of the specific hypotheses in the overall hypothesis space
	 * @param positive string that represents a positive class label
	 * @param negative string that represents a negative class label
	 * @param empty ArrayList that represents the empty set if the target concept cannot be found in the hypothesis space
	 */
	static void canidateEliminationAlgo(String[] instances, ArrayList<GeneralHypo> G, ArrayList<SpecificHypo> S, ArrayList<String> empty, String positive, String negative)
	{
			//represents the label for each training instance
			int j= (instances.length-1);
			String d = instances[j];
			
			//checks to see if training example is positive(ie., an edible mushroom)
			if(d.equalsIgnoreCase(positive))
			{
				//Removes from G any hypothesis inconsistent with the training instance
				int inital_value = G.size();
				for(int x=0; x<inital_value; x++)
				{
					if(G.size() != inital_value)
					{
						break; 
					}
					GeneralHypo space = G.get(x);
					ArrayList<String> hypo = space.getHypothesis();
					int initial_value2 = hypo.size(); 
					for (int a=0; a<initial_value2; a++)
					{
						String instance_value = instances[a];
						String hypo_value = hypo.get(a); 
						
						//checks to see that G either contains the instance value or ? which means that
						//any value is acceptable for this attribute 
						if(instance_value.equalsIgnoreCase(hypo_value) || hypo_value.equalsIgnoreCase("?"))
						{
								; 
						}
						else
						{
							//removes hypothesis from general boundary if inconsistent with training instance
							G.remove(x);
							break; 
						}
					}
				}
				//checks to make sure each specific hypothesis is consistent with the training instance
				int initial_value = S.size(); 
				for(int x=0; x<initial_value; x++)
				{
					if(S.size() != initial_value)
					{
						break; 
					}
					boolean flag = false; 
					SpecificHypo space = S.get(x);
					ArrayList<String> hypo = space.getHypothesis();
					
					
					int initial_value2 = hypo.size(); 
					for (int a=0; a<initial_value2; a++)
					{
						String instance_value = instances[a];
						String hypo_value = hypo.get(a); 
						
						//checks to see that S either contains the instance value or ? which means that
						//any value is acceptable for this attribute 
						if(instance_value.equalsIgnoreCase(hypo_value) || hypo_value.equalsIgnoreCase("?"))
						{
								;  
						}
						//else statement that is only entered if specific hypothesis is inconsistent with training instance
						else
						{ 
							 
							if(!flag)
							{
								//removes specific hypothesis from S
								if(!S.isEmpty())
								{
									S.remove(x);
									//if both S and G are empty, the target concept cannot be found in Hypothesis space
									if (S.isEmpty() && G.isEmpty())
									{
										System.out.println("The True concept cannot be represented by the given Hypothesis Space, Sorry");
										System.exit(1);
									}
									flag=true; 
								}
								else
								{
									hypo=empty; 
								}

							}
							//adds minimal generalizations to the most specific hypothesis 
							if(hypo.contains("0"))
							{
									hypo.remove(a);
									hypo.add(a, instance_value);
							}
							//checks to see if hypothesis is empty
							else if(hypo.containsAll(empty))
							{
								; 
							}
							//adds minimal generalizations to new specific hypothesis to be consistent with the training instance
							else
							{
									hypo.remove(a); 
									hypo.add(a, "?");
							}
						}
					}
					if (flag)
					{
						//check to see that some member in G is more general than new specific hypothesis
						//if so, the specific hypothesis is added to the S boundary
						long question_marks_g=0, question_marks_s_h=0;;
						Iterator<GeneralHypo> member_hypo_g = G.iterator();
						while(member_hypo_g.hasNext())
						{
							GeneralHypo generalmember = member_hypo_g.next();
							List<String> hypo_member_g = generalmember.getHypothesis(); 
							List<String> s_h= hypo;
							question_marks_s_h= s_h.stream().filter(quest->"?".equals(quest)).count();
							question_marks_g= hypo_member_g.stream().filter(quest->"?".equals(quest)).count(); 
							if(question_marks_s_h>question_marks_g)
							{
								break; 
							}
							else
							{
								//adds the specific hypothesis to the S boundary 
								SpecificHypo additional_specific_hypo = new SpecificHypo(hypo);
								S.add(additional_specific_hypo);
								break; 
							}
							
						}
					}
					//check that will remove from S any hypothesis that is more
					//general than another hypothesis in S
					initial_value=S.size(); 
					if(initial_value>1)
					{
						Iterator<SpecificHypo> space_specific = S.iterator();
						HashMap<String, Integer> count = new HashMap<String, Integer>();
						int question_marks=0, z=0;
						while(space_specific.hasNext())
						{
							SpecificHypo h = space_specific.next(); 
							ArrayList<String> h1 = h.getHypothesis(); 
							Iterator<String> ss_element= h1.iterator();
							while(ss_element.hasNext())
							{
								if(ss_element.next().equalsIgnoreCase("?"))
								{
									question_marks++; 
								}	
							}
							count.put(Integer.toString(z), question_marks);
							z++;
							question_marks=0; 
						}
					
						List<Integer> min_values= new ArrayList<Integer>(); 
						for(int a=0; a<count.size(); a++)
						{
							Integer temp =count.get(Integer.toString(a));
							min_values.add(a, temp); 	
						}
					
						boolean allEqual= min_values.stream().distinct().limit(2).count()<=1;
					
						if (allEqual)
						{
							; 
						}
						else
						{
							int minvalue = Integer.MIN_VALUE, index=0;
						
							for(int a=0; a<count.size(); a++)
							{
								Integer value = count.get(a); 
								int temp2 = value.intValue(); 
								if(temp2>minvalue)
								{
									index=a; 
								}
							}
							if(!S.isEmpty())
							{
								S.remove(index); 
							}
						}
					}
				
				}
			}
			//checks to see if training example is negative(ie., a poisonous mushroom)
			if(d.equalsIgnoreCase(negative))
			{
				///Removes from S any hypothesis inconsistent with the training instance
				int inital_value = S.size();
				for(int x=0; x<inital_value; x++)
				{
					if(S.size() != inital_value)
					{
						break; 
					}
					int count=0; 
					SpecificHypo space = S.get(x);
					ArrayList<String> hypo = space.getHypothesis();
				
					for (int a=0; a<hypo.size(); a++)
					{
						String instance_value = instances[a];
						String hypo_value = hypo.get(a);
						
						//if a specific hypothesis accepts a negative instance the hypothesis will be removed
						if(instance_value.equalsIgnoreCase(hypo_value) || hypo_value.equalsIgnoreCase("?"))
						{
							count++; 
						}
					}
					if(count==hypo.size())
					{
						S.remove(x); 		
					}
				}
				inital_value = G.size();
				Iterator<SpecificHypo> sd=S.iterator();
				for(int x=0; x<inital_value; x++)
				{
					if(G.size() != inital_value)
					{
						break; 
					}
					int count=0; 
					GeneralHypo space = G.get(x);
					ArrayList<String> hypo = space.getHypothesis();
					SpecificHypo space2 = null;
					ArrayList<String> s_hypo= null;
					
					//checks to see that S boundary is not empty
					if(S.isEmpty())
					{
						s_hypo= empty; 
					}
					else
					{
						//if S boundary is not empty get a specific hypothesis
						if(sd.hasNext())
						{
							 space2 = sd.next();
							 s_hypo = space2.getHypothesis(); 
						}
						
					}
					//checks to make sure each general hypothesis is consistent with the training instance
					//(ie., rejecting a negative instance)
					for (int a=0; a<hypo.size(); a++)
					{
						String instance_value = instances[a];
						String hypo_value = hypo.get(a);
						
						if(instance_value.equalsIgnoreCase(hypo_value) || hypo_value.equalsIgnoreCase("?"))
						{
							count++; 
						}
					}
					//checks to see if a general hypothesis accepts a negative example
					if(count==hypo.size())
					{
						if(!G.isEmpty())
						{
							//removes the general hypothesis that accepts a negative example
							G.remove(x);
							
							//if both S and G are empty, the target concept cannot be found in Hypothesis space
							if(S.isEmpty() && G.isEmpty())
							{
								System.out.println("The True concept cannot be represented by the given Hypothesis Space, Sorry ");
								System.exit(1);
							}
						}
						//adds to G all minimal specializations of a general hypothesis 
						//so that it is able to reject the negative instance 
						for(int a=0; a<hypo.size(); a++)
						{
							String instance_value = instances[a];
							String specific_value = s_hypo.get(a);
							
							//crafts general hypothesis that is minimal in its specialization
							// by comparing it to a specific hypothesis
							if(!specific_value.equalsIgnoreCase(instance_value))
							{
									if(specific_value.equalsIgnoreCase("?"))
									{
										; 
									}
									else if (specific_value.equalsIgnoreCase("empty")) //empty set
									{
										; 
									}
									else
									{
										ArrayList<String> n_hypo = new ArrayList<String>(); 
										for(int z=0; z<hypo.size(); z++)
										{
											if(z==a)
											{
												n_hypo.add(specific_value); 
											}
											else
											{
												n_hypo.add("?"); 
											}
										}
										//checks to see that some specific hypothesis is more specific than the newly crafted general hypothesis
										long question_marks_s=0, question_marks_h=0;;
										Iterator<SpecificHypo> member_hypo_s = S.iterator();
										while(member_hypo_s.hasNext())
										{
											SpecificHypo specificmember = member_hypo_s.next();
											List<String> hypo_member_s = specificmember.getHypothesis(); 
											List<String> h= n_hypo;
											question_marks_h= h.stream().filter(quest->"?".equals(quest)).count();
											question_marks_s= hypo_member_s.stream().filter(quest->"?".equals(quest)).count(); 
											if(question_marks_h>question_marks_s)
											{
												//adds the general hypothesis to the G boundary
												GeneralHypo additional_hypo = new GeneralHypo(n_hypo); 
												G.add(additional_hypo);
												break; 
											}
											else
											{
												break; 
											}
										}
									}
										
							 }
						  }
					   }
					  //check that removes from G any hypothesis that is less general than another hypothesis in G
					  int initial_value=G.size(); 
					  if(initial_value>1)
					  {
						  Iterator<GeneralHypo> space_general = G.iterator();
						  HashMap<String, Integer> count_question_marks = new HashMap<String, Integer>();
						  int question_marks=0, z=0;
						  while(space_general.hasNext())
						  {
							  GeneralHypo h = space_general.next(); 
							  ArrayList<String> h1 = h.getHypothesis(); 
							  Iterator<String> ss= h1.iterator();
							  while(ss.hasNext())
							  {
								  if(ss.next().equalsIgnoreCase("?"))
								  {
									  question_marks++; 
								  }	
							  }
							  count_question_marks.put(Integer.toString(z), question_marks);
							  z++;
							  question_marks=0; 
						  }
						  List<Integer> min_values= new ArrayList<Integer>(); 
						  for(int a=0; a<count_question_marks.size(); a++)
						  {
							  Integer temp =count_question_marks.get(Integer.toString(a));
							  min_values.add(a, temp); 	
						  }
					
						  boolean allEqual= min_values.stream().distinct().limit(2).count()<=1;
					
						  if (allEqual)
						  {
							  ; 
						  }
						  else
						  {
							  int minvalue = Integer.MAX_VALUE, index=0;
							  for(int a=0; a<count_question_marks.size(); a++)
							  {
								  Integer value = count_question_marks.get(a); 
								  int temp2 = value.intValue(); 
								  if(temp2<minvalue)
								  {
									  index=a; 
								  }
							  }
							  G.remove(index); 
						  }
				
					  }
					  else
					  {
						  ; 
					  }
				}
				
			}
		
	}
	
	/**
	 * Prediction function that takes in the version space constructed by
	 * the Candidate-Elimination algorithm and outputs classifications of new instances
	 * that were not used in the original construction of the version space
	 * @param testing_set a new instance that has not yet been observed
	 * @param G ArrayList that contains all of the general hypotheses in the overall hypothesis space
	 * @param S ArrayList that contains all of the specific hypotheses in the overall hypothesis space
	 * @param positive string that represents a positive class label
	 * @param negative string that represents a negative class label
	 * @return a string that represents a classification of an unobserved instance (ie., testing example)
	 */
	static String prediction(String[] testing_set, ArrayList<GeneralHypo> G, ArrayList<SpecificHypo> S,  String positive, String negative)
	{
			int j= (testing_set.length-1);
			
			//holds predicted scores of each specific hypothesis in S
			String[] specific_predictedValues_scores = new String[S.size()];
			//holds predicted scores of each general hypothesis in G
			String[] general_predictedValues_scores = new String[G.size()];
		
			String predicted_values = null; 
		
			//tests each unobserved instance against each specific hypotheses in S
			int s_h=0; 
			Iterator<SpecificHypo> specific_space_hypos = S.iterator();
			//retrieves each specific hypo from S 
			while(specific_space_hypos.hasNext())
			{
				SpecificHypo specific_hypo = specific_space_hypos.next(); 
				List<String> specific_elements= specific_hypo.getHypothesis();
				
				int specific_count=0; 
				int specific_size = specific_elements.size(); 
				for(int a=0; a<specific_size; a++)
				{
					String unkown = testing_set[a];
					String hypo_value = specific_elements.get(a);
					//checks to see if unobserved instance satisfies a specific hypo
					if(unkown.equalsIgnoreCase(hypo_value) || hypo_value.equalsIgnoreCase("?"))
					{
						specific_count++; 
					}
				}
				//unobserved instance will be classified as positive(ie., edible) if it satisfies a member of S
				if(specific_count==specific_size)
				{
					specific_predictedValues_scores[s_h++]=positive; 
				}
				//unobserved instance will be classified as negative (ie., poisonous) if it not satisfied by a member of S
				else
				{
					specific_predictedValues_scores[s_h++]=negative; 
				}
				
			}
			//tests each unobserved instance against each general hypotheses in G
			int g_h=0; 
			Iterator<GeneralHypo> general_space_hypos = G.iterator(); 
			while(general_space_hypos.hasNext())
			{
				GeneralHypo general_hypo = general_space_hypos.next(); 
				List<String> general_elements= general_hypo.getHypothesis();
				
				int general_count=0; 
				int general_size = general_elements.size();
				
				for(int a=0; a<general_size; a++)
				{
					String unkown = testing_set[a];
					String hypo_value = general_elements.get(a);
					//checks to see if unobserved instance satisfies a general hypo
					if(unkown.equalsIgnoreCase(hypo_value) || hypo_value.equalsIgnoreCase("?"))
					{
						general_count++; 
					}
				}
				//unobserved instance will be classified as positive(ie., edible) if it satisfies a member of G
				if(general_count==general_size)
				{
					general_predictedValues_scores[g_h++]=positive; 
				}
				//unobserved instance will be classified as negative (ie., poisonous) if it not satisfied by a member of G
				else
				{
					general_predictedValues_scores[g_h++]=negative;
				}
			
			}

		//part of program that computes a vote of the how each hypothesis in the 
		//version space classified the unobserved instance. The classification with 
		//the most votes wins (ie., majority vote) 
		int positive_values=0, negative_values=0;
		for(int z=0; z<S.size(); z++)
		{	
			String value = specific_predictedValues_scores[z]; 
			if(value.equalsIgnoreCase(positive))
			{
					positive_values++; 
			}
			else if(value.equalsIgnoreCase(negative))
			{
					negative_values++; 
			}
		}
		for(int z=0; z<G.size(); z++)
		{	
			String value = general_predictedValues_scores[z]; 
			if(value.equalsIgnoreCase(positive))
			{
				positive_values++; 
			}
			else if(value.equalsIgnoreCase(negative))
			{
				negative_values++; 
			}
		}
		if(positive_values>negative_values)
		{
			predicted_values=positive; 
		}
		else if(negative_values>positive_values)
		{
			predicted_values=negative; 
		}
		else
		{
			//if no majority vote is determined the instance value will be classified as unknown 
			predicted_values="Unkown"; 
		}
		return predicted_values; 
	}
	
	
	/**
	 * Main interface that calls the Candidate-Elimination
	 * Algorithm and then calls the predicting function
	 * to predict how the version space constructed by the Candidate-Elimination 
	 * Algorithm learns to classify unobserved instances 
	 * @param args represents command line arguments
	 * @throws IOException an input/output exception thrown in case file read error occurs
	 */
	public static void main(String[] args) throws IOException 
	{
		Scanner scan = new Scanner(System.in);
		//asks user to enter target concept to learn
		System.out.print("Please Enter Target Concept to Learn: ");
		String conceptLearn = scan.nextLine();
		//asks user to enter how many attributes are used to describe concept to be learned
		System.out.println("Please Enter number of Attributes:  ");
		String num_attributes = scan.nextLine(); 
		int size= Integer.parseInt(num_attributes);
		System.out.println("Please Enter Positive Class Label ");
		String positive = scan.nextLine();
		System.out.println("Please Enter Negative Class Label  ");
		String negative = scan.nextLine();
		
		//creating the most general and specific hypothesis and empty set just in case concept cannot
		//be represented by hypothesis space
		ArrayList<String> generalHypo = new ArrayList<String>(); 
		ArrayList<String> specificHypo = new ArrayList<String>();
		ArrayList<String> emptySet = new ArrayList<String>(); 
		for(int i=0; i<size; i++)
		{
			specificHypo.add("0"); 
			generalHypo.add("?");
			emptySet.add("empty"); 
		}
		SpecificHypo specific = new SpecificHypo(specificHypo);  
		GeneralHypo general = new GeneralHypo(generalHypo);
		ArrayList<SpecificHypo> specific_hypos = new ArrayList<SpecificHypo>(); 
		ArrayList<GeneralHypo> general_hypos = new ArrayList<GeneralHypo>(); 
		specific_hypos.add(specific); 
		general_hypos.add(general);
		
		//asks user for the file path for the training data and calls the Candidate-Elimination 
		//Algorithm
		System.out.print("Please Enter Input path for training data");
		String filenameAndAddressTraining = scan.nextLine(); 
		@SuppressWarnings("deprecation")
		CSVReader csv_reader = new CSVReader(new FileReader(filenameAndAddressTraining), ',');
		List<String[]> whole_file = csv_reader.readAll(); 
		for(String[] row : whole_file)
		{
			canidateEliminationAlgo(row, general_hypos, specific_hypos, emptySet, positive, negative);
		}
		
		//asks user for the file path for the testing data and calls the prediction function 
		System.out.print("Please Enter Input path for testing data");
		String filenameAndAddressTesting = scan.nextLine();
		ArrayList<String> predicted_values = new ArrayList<String>();
		ArrayList<String> actual_values = new ArrayList<String>();
		int accuracy_of_learnedConcept=0, z=0;
		String prediction, actual; 
		@SuppressWarnings("deprecation")
		CSVReader csv_reader2 = new CSVReader(new FileReader(filenameAndAddressTesting), ',');
		whole_file = csv_reader2.readAll(); 
		for(String[] row : whole_file)
		{
				predicted_values.add(prediction(row, general_hypos, specific_hypos, positive, negative));
				actual_values.add(row[row.length-1]);
				prediction=predicted_values.get(z);
				actual = actual_values.get(z); 
				z++; 
				if (actual.equalsIgnoreCase(prediction))
				{
					accuracy_of_learnedConcept++; 
				}
				
		}
		
		//printout of results to the console
		System.out.print("Target Concept: " + conceptLearn);
		System.out.println("");
		System.out.println("------------------------------------------------");
		System.out.print("Number of Attributes: " + num_attributes);
		System.out.println("");
		System.out.println("------------------------------------------------");
		System.out.print("Positive Class label: " + positive);
		System.out.println("");
		System.out.println("------------------------------------------------");
		System.out.print("Negative Class label: " +  negative);
		System.out.println("");
		System.out.println("------------------------------------------------");
		System.out.print("Specific Hypothesis Space:  ");
		Iterator<SpecificHypo> s_hypo_space= specific_hypos.iterator();
		while(s_hypo_space.hasNext())
		{
			SpecificHypo s_hypo = s_hypo_space.next();
			System.out.print(s_hypo.toString() + "  ");

		}
		System.out.println("");
		System.out.print("General Hypothesis Space:  ");
		Iterator<GeneralHypo> g_hypo_space= general_hypos.iterator();
		while(g_hypo_space.hasNext())
		{
			GeneralHypo g_hypo = g_hypo_space.next();
			System.out.print(g_hypo.toString() + " ");
		}
		System.out.println("");
		System.out.println("------------------------------------------------");
		System.out.println("Accuracy:  " + (((double)accuracy_of_learnedConcept/(double)predicted_values.size())*100)+ "%");
		System.out.println("------------------------------------------------");
		System.out.println("Predicted vs Actual labels:");
		System.out.println("");
		for(int i=0; i<predicted_values.size(); i++)
		{
			System.out.format("%-9s    %-9s",predicted_values.get(i), actual_values.get(i) );
			System.out.println("");	
		}
		System.out.println("");
		System.out.println("");

	}

}
