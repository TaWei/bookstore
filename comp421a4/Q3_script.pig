--load the data from S3 and define the schema
raw = LOAD '/user/hadoop/HFER_e.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray,
	gender:chararray, occupation:chararray, party:chararray,
	votes:int, percent:double, elected:int);

--Consider only general election winners.
typeFltrdTups = FILTER raw BY type == 'Gen' and elected == 1;
typeGrpdTups = GROUP typeFltrdTups BY parl;
DESCRIBE typeGrpdTups;

/*
 Dump to the screen a list of (Parliament, count) tuples showing the difference between that Parliament and the previous one sorted ascending by Parliament number.
 */
results = FOREACH typeGrpdTups {
	prevParlTup = FILTER typeFltrdTups by parl == $0 - 1;
	prevParlCnt = COUNT(prevParlTup);
	currentParlCnt = COUNT(typeFltrdTups);
	GENERATE $0 as parl_no, currentParlCnt - prevParlCnt as difference;
};
resultsSorted = ORDER results BY parl_no ASC;
dump resultsSorted;

