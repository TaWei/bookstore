-- Assignment 4 - Question 5 ------------------------------------------------------------------------
-- Words scanned since 1990. ------------------------------------------------------------------------

-- Load the S3 raw data and define the schema
raw = LOAD '/user/hadoop/ngrams421.csv' USING PigStorage(',') AS (word:chararray, year:int, occurrences:int);

-- Filter the raw data by the year field
fltrd = FILTER raw by year == 1990;

-- Generate the occurrence count for each entry in the DB
-- Then, group the relations into a single group for counting
gen = foreach fltrd generate occurrences;
genset = group gen all;

-- Add the occurences of each entry in the set
results = foreach genset generate SUM(genset.gen.occurrences);

-- Print
dump results;