--load the data from S3 and define the schema
raw = LOAD '/user/hadoop/HFER_e.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray, gender:chararray, occupation:chararray, party:chararray, votes:int, percent:double, elected:int);

--some data entries use the middle name as well, so this way we will catch all of them
fltrd = FILTER raw by lastname == 'Harper' and firstname matches 'Stephen.*';

--project only the needed fields
gen = foreach fltrd generate date, lastname, firstname;

--sort the entries by the election date
odred = order fltrd by date;

--choose only the smallest date
results = limit odred 1;

--print the result tuple to the screen
dump results;