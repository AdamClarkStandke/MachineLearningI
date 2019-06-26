# Introduction
Program is an implementation of the Canidadate Elimination Algorithm. This program was a project for my Machine
Learning class taught by Dr. David Mooney of Shippensburg University. 

# Background
The Canidate Elimination Algorithm is a 
machine learning algorithm that falls under a concept learning system. Concept learning can be formulated as a problem
of searching through a predefined space of potential hypotheses for the hypothesis that best fits the training examples[]. 
In this case (and implementation) the hypothesis space is represented by a conjunction of contraints on the instance 
attributes[]. For each attribute, the representation of the hypotesis can be: 1) a "?" which indicates that any 
value is acceptable for an attribute; 2) a specific value; or 3) the empty set (ie., null) that no value is acceptable[].
Since concept learning can be viewed as a search problem, different stratiges have been developed to search a hypothesis space. One such algorithm, is the Candiate Eleimination Algorithm. The key idea of the Candidate Elimination algorithm is to
output a description of the set of all hypotheses consistent with the training examples[]. It does so by constructing a 
version space that keeps track of only two boundary sets G and S, which contains the set of hypotheses consistent with the training examples[].
### Definition: The general boundary G, with respect to hypothesis space H and training data D, is the set of maximally general members of H consistent with D[6]. 
<a href="https://www.codecogs.com/eqnedit.php?latex=G\equiv&space;\left&space;\{&space;g\in&space;H&space;|&space;Consistent(g,&space;D)\wedge&space;(\lnot&space;\exists&space;g^{'}\in&space;H&space;)[g^{'}&space;\gg&space;g&space;\wedge&space;Consistent(g^{'},&space;D)&space;]&space;\right&space;\}" target="_blank"><img src="https://latex.codecogs.com/gif.latex?G\equiv&space;\left&space;\{&space;g\in&space;H&space;|&space;Consistent(g,&space;D)\wedge&space;(\lnot&space;\exists&space;g^{'}\in&space;H&space;)[g^{'}&space;\gg&space;g&space;\wedge&space;Consistent(g^{'},&space;D)&space;]&space;\right&space;\}" title="G\equiv \left \{ g\in H | Consistent(g, D)\wedge (\lnot \exists g^{'}\in H )[g^{'} \gg g \wedge Consistent(g^{'}, D) ] \right \}" /></a>

### Definition: The specific boundary S, with respect to hypothesis space H and training data D, is the set of maximally general members of H consistent with D[6]. 
<a href="https://www.codecogs.com/eqnedit.php?latex=S\equiv&space;\left&space;\{&space;s\in&space;H&space;|&space;Consistent(s,&space;D)\wedge&space;(\lnot&space;\exists&space;s^{'}\in&space;H&space;)[s&space;\gg&space;s^{'}&space;\wedge&space;Consistent(s^{'},&space;D)&space;]&space;\right&space;\}" target="_blank"><img src="https://latex.codecogs.com/gif.latex?S\equiv&space;\left&space;\{&space;s\in&space;H&space;|&space;Consistent(s,&space;D)\wedge&space;(\lnot&space;\exists&space;s^{'}\in&space;H&space;)[s&space;\gg&space;s^{'}&space;\wedge&space;Consistent(s^{'},&space;D)&space;]&space;\right&space;\}" title="S\equiv \left \{ s\in H | Consistent(s, D)\wedge (\lnot \exists s^{'}\in H )[s \gg s^{'} \wedge Consistent(s^{'}, D) ] \right \}" /></a>

Accoringly, the version space is precisely the set of hypotheses contained in G, plus those contained in S, plus those that lie between G and S in the partially ordered hypothesis space (See Theorem 2.1 of [] for a formal proof of this fact).
The Candiate Elimination algorithm using version spaces is described by the following figure:






