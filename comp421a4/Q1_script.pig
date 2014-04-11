--Load the data from S3 and define the schema.
raw = LOAD '/user/hadoop/HFER_e.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray,
	gender:chararray, occupation:chararray, party:chararray,
	votes:int, percent:double, elected:int);

--Only consider data entries that have percent > 60.
fltrd = FILTER raw BY percent >= 60;

--Project only on nested fields.
gen = foreach fltrd GENERATE CONCAT(firstname, CONCAT(' ', lastname));

--Only keep distinct tuples.
results = DISTINCT gen;

--Store the results into a file (we chose this option instead of storing it into S3 buckets).
 STORE results INTO '/user/hadoop/Q1_results.txt' using PigStorage();