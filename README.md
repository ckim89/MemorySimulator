MemorySimulator
===============

2014 Summer Data Structures Final Project
By:
Eddie Kong
Matt Gigliotti 
Daniel Kim

We created a memory simulator that takes in an input file that specifies how
much memory is used and some operations that either allocate or deallocate
memory in a specified chunk. We use three different data structures to do this
in order to determine the pros and cons of using a particular data structure. We
used an AVL tree, a Heap, and a Hash Table. 

To run the code:
compile MemSimMain and run. It will prompt an input file. There is an input file
included within the src directory, but feel free to change the input file as
needed. After finishing, there will be a translog.txt file and an analysis.txt
file that will show up. The translog files will show exactly where the memory
was allocated and deallocated and when defrags occurred. The analysis file will
give a summary of what happened in the whole process.

JUnit Tests:
There are junit tests that are written for each of the three data structures.
They are uncommented.
