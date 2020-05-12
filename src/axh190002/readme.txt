/**
 * IDSA Short Project 9
 * Team Members:
 * Adarsh Raghupati  NetID: axh190002
 * Keerti Keerti     NetID: kxk190012
 */
 
####Implementation of Boruvka's and Prims minimum spanning tree algorithm.

###### Steps to run the code in IntelliJ IDE
* Create an empty java project 
* Unzip the source code files and paste it under the location "Java Project Name"/src folder
* Open the MST.java and run the program

###### Sample run:
1. With mst-50-140-84950.txt as input test file
Boruvka
84950
Time: 8 msec.
Memory: 3 MB / 121 MB.

Performance of two algorithms for different input size:

Algorithm  No.of Nodes  No.of Edges  Time in msec   Space
Boruvka    50           140          5              3 MB / 121 MB
Boruvka    200          580          12             5 MB / 121 MB
Boruvka    10K          30K          158            28 MB / 153 MB
Boruvka    10K          1M           361            157 MB / 918 MB
Boruvka    100K         30M          7064           2918 MB / 3065 MB
Boruvka    100K         300M         175035         24851 MB / 27330 MB
Boruvka    1M           300M         404960         25542 MB / 28005 MB

Prims      50           140          5              3 MB / 121 MB
Prims      200          580          8              5 MB / 121 MB
Prims      10K          30K          50             8 MB / 121 MB
Prims      10K          1M           96             123 MB / 704 MB
Prims      100K         30M          3889           2500 MB / 3075 MB
Prims      100K         300M         29010          25494 MB / 27116 MB
Prims      1M           300M         40552          26778 MB / 27744 MB