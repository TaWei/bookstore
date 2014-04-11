--load the data from S3 and define the schema
raw = LOAD '/user/hadoop/HFER_e.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray,
	gender:chararray, occupation:chararray, party:chararray,
	votes:int, percent:double, elected:int);

--Consider only general election winners.
typeFltrdTups = FILTER raw BY type == 'Gen' and elected == 1;
parlGrpdTups = GROUP typeFltrdTups BY parl;
DESCRIBE parlGrpdTups;

/*
 Dump to the screen a list of (Parliament, count) tuples showing the difference between that Parliament and the previous one sorted ascending by Parliament number.
 */
prevParlCnt = FOREACH parlGrpdTups {
	GENERATE $0 as prev_parl_no, COUNT($1) as prev_parl_cnt;
};
currParlCnt = FOREACH parlGrpdTups {
	GENERATE $0 as curr_parl_no, COUNT($1) as curr_parl_cnt;
};
crss = CROSS currParlCnt, prevParlCnt ;
crssFltrd = FILTER crss BY (curr_parl_no == 1 and prev_parl_no == 1 ) or (prev_parl_no == curr_parl_no - 1);
results = FOREACH crssFltrd {
	GENERATE curr_parl_no as parl_no, (curr_parl_no == 1 ? curr_parl_cnt : curr_parl_cnt - prev_parl_cnt) as difference;
};
resultsSorted = ORDER results BY parl_no ASC;
dump resultsSorted;

