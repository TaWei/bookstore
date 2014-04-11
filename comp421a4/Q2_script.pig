--Load the data from S3 and define the schema.
raw = LOAD '/user/hadoop/HFER_e.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray,
	gender:chararray, occupation:chararray, party:chararray,
	votes:int, percent:double, elected:int);

--Start by eliminating all candidates with less than 100 votes.
fltr100 = FILTER raw BY votes >= 100;

--Split the relation into those who won and those who lost.
fltrWon = Filter fltr100 BY elected == 1;
fltrLost = Filter fltr100 BY elected == 0;

/*
Join the all the candidates back together and make tuples with the last names of both candidates (elected and defeated) and the difference between their vote totals. 
Include only tuples where the difference is less than 10.
*/
jnd = JOIN fltrWon BY (date, prov, riding), fltrLost BY (date, prov, riding);
DESCRIBE jnd;
fltr10 = Filter jnd BY (fltrWon::votes - fltrLost::votes) < 10;  
results = FOREACH fltr10 GENERATE fltrWon::lastname AS elected, fltrLost::lastname AS defeated, fltrWon::votes - fltrLost::votes AS vote_difference;

--Store the results into a file (we chose this option instead of storing it into S3 buckets).
 STORE results INTO '/user/hadoop/Q2_results.txt' USING PigStorage();
