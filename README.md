# Introduction
The program is an implementation of the Candidate Elimination Algorithm for learning a target concept(ie., a boolean valued function). This was a project for my machine learning class taught by Dr. David Mooney of Shippensburg University. 

# Background
The Candidate Elimination Algorithm is a 
machine learning algorithm that falls under the topic of concept learning. Concept learning can be formulated as a problem
of searching through a predefined space of potential hypotheses for the hypothesis that best fits the training examples[1]. 
For this implementation, the hypothesis space is represented by a *conjunction* of constraints on the instance 
attributes[1]. For each attribute, its value can either be: 1) a "?" which indicates that any 
value is acceptable for an attribute; 2) a specific value; or 3) the empty set (ie., null) which means that no value is acceptable[1].Since concept learning can be viewed as a search problem, different strategies have been developed to search a hypothesis space. One such algorithm, is the Candidate Elimination Algorithm. The key idea of the Candidate Elimination Algorithm is to
output a description of the set of all hypotheses consistent with the training examples[1]. It does so, by constructing a 
version space that keeps track of only two boundary sets *G* and *S*, which contains the set of hypotheses consistent with the training examples[1].
### Definition: The general boundary *G*, with respect to hypothesis space *H* and training data *D*, is the set of maximally general members of *H* consistent with *D*[1]. 
<a href="https://www.codecogs.com/eqnedit.php?latex=G\equiv&space;\left&space;\{&space;g\in&space;H&space;|&space;Consistent(g,&space;D)\wedge&space;(\lnot&space;\exists&space;g^{'}\in&space;H&space;)[g^{'}&space;\gg&space;g&space;\wedge&space;Consistent(g^{'},&space;D)&space;]&space;\right&space;\}" target="_blank"><img src="https://latex.codecogs.com/gif.latex?G\equiv&space;\left&space;\{&space;g\in&space;H&space;|&space;Consistent(g,&space;D)\wedge&space;(\lnot&space;\exists&space;g^{'}\in&space;H&space;)[g^{'}&space;\gg&space;g&space;\wedge&space;Consistent(g^{'},&space;D)&space;]&space;\right&space;\}" title="G\equiv \left \{ g\in H | Consistent(g, D)\wedge (\lnot \exists g^{'}\in H )[g^{'} \gg g \wedge Consistent(g^{'}, D) ] \right \}" /></a>

### Definition: The specific boundary *S*, with respect to hypothesis space *H* and training data *D*, is the set of maximally general members of *H* consistent with *D*[1]. 
<a href="https://www.codecogs.com/eqnedit.php?latex=S\equiv&space;\left&space;\{&space;s\in&space;H&space;|&space;Consistent(s,&space;D)\wedge&space;(\lnot&space;\exists&space;s^{'}\in&space;H&space;)[s&space;\gg&space;s^{'}&space;\wedge&space;Consistent(s^{'},&space;D)&space;]&space;\right&space;\}" target="_blank"><img src="https://latex.codecogs.com/gif.latex?S\equiv&space;\left&space;\{&space;s\in&space;H&space;|&space;Consistent(s,&space;D)\wedge&space;(\lnot&space;\exists&space;s^{'}\in&space;H&space;)[s&space;\gg&space;s^{'}&space;\wedge&space;Consistent(s^{'},&space;D)&space;]&space;\right&space;\}" title="S\equiv \left \{ s\in H | Consistent(s, D)\wedge (\lnot \exists s^{'}\in H )[s \gg s^{'} \wedge Consistent(s^{'}, D) ] \right \}" /></a>

Accordingly, the version space is precisely the set of hypotheses contained in *G* plus those contained in *S*, plus those that lie between *G* and *S* in the partially ordered hypothesis space (See Theorem 2.1 of [1] for the formal proof of this statement)[1].
The Candidate Elimination Algorithm is described by the following figure:

![](Screen%20Shot%202019-06-26%20at%2011.28.14%20AM.png)

# How to Use the Program
The portion that contains Java's runnable method is contained in the class called CandidateAlgorithm. From there the rest of  the other Java files are called when needed. The class CandidateAlgorithm is composed of three methods.

The first part is the static void main method that acts as an interface for the user. The program will first ask the user to enter a target concept that it would like to learn(eg., Is a mushroom poisonous). Then, the program will ask the user to enter how many attributes describe the target concept. After doing so, the user will enter the positive and negative class labels associated with the training data. Once both class labels have been supplied, the program will ask for the file path of the training data. It must be in csv format and separated by commas.

After which each training instance will be incrementally inputted into the Candidate Elimination Algorithm so that a version space can be constructed for the target concept. The version space of the Candidate Elimination algorithm will converge to the hypothesis that correctly describes the target concept so long as: 1)there are no errors in the training data; and 2)there is a hypothesis in *H* that correctly describes the target concept[1]. While the first point is self explanatory, the second point must be further clarified. 

As mentioned before, the hypothesis space represents a *conjunction* of constraints on the instance attributes. In other words, this implementation of the Candidate Elimination Algorithm is a biased learner in that the hypothesis space *H* can only represent a conjunction of constraints[1]. If the target concept to be learned is not a conjunction of attributes, the program's hypothesis space *H* cannot represent the target concept. When this happens the program will output the following message: "The true target concept cannot be represented by the given Hypothesis Space, Sorry." Thus, so long as the training examples are correct (ie., no errors) and the true target concept is present within the hypothesis space(ie., the target concept is a conjunction of attributes), a final version space will be constructed.

If a final version space has been constructed, the user will be then be asked to enter a test set for classifying new instances that have not been observed. After doing so, the method titled prediction will be called. Even if the version space has not converged to a single, identical hypothesis (ie., *S* and *G* are not identical), the prediction method will still be able to classify unseen instances by comparing each hypothesis in *S* and *G* with an unseen instance, and then outputting a majority vote **(Note: in a future implementation of CEA, I would like for each unseen instance to be also compared with each hypothesis within the version space and not just *S* and *G*; this would potentially offer greater prediction power)**[1].(See [1] sub-section 2.6.3 for more details on this "majority voting" process).

# Citations
[1] Mitchell, Tom Michael. Machine Learning. McGraw-Hill, 1997.





