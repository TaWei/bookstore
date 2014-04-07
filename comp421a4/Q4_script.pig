raw = LOAD '/user/hadoop/HFER_e.csv' USING PigStorage(',') AS  (date, type:chararray, parl:int, prov:chararray, riding:chararray, lastname:chararray, firstname:chararray,
	gender:chararray, occupation:chararray, party:chararray,
	votes:int, percent:double, elected:int);
fltrd = FILTER raw BY elected==1;
D = group fltrd by parl;
E = foreach D {
	parlnumber = $0;
	parlnumMP = COUNT($1);
	generate parlnumber as Parliament, parlnumMP as NumOfMP;
	};
	
X = group fltrd by (parl, party);
F = foreach X {
	generate group.parl as Parliament, group.party as PartyName, COUNT($1) as NumOfPartyMembers;
};

C = join E by Parliament, F by Parliament;
results = foreach C {
	generate E::Parliament as ParliamentNUmber, F::PartyName as PartyName, F::NumOfPartyMembers as PartyMPs, E::NumOfMP as ParliamentMPs;
};

STORE results INTO '/users/hadoop/Q4_results.txt' USING PigStorage();


