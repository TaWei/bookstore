DROP PROCEDURE moneySpentOnAuthor
--Given a credit card number and an author id, return the amount of money spent by the card on books by that author.

CREATE PROCEDURE moneySpentOnAuthor(in ccnumber varchar(20), in aid int, out moneyspent DECIMAL(10,2)) \
LANGUAGE SQL \
READS SQL DATA \
P1: BEGIN \
DECLARE spentOnAuthor DECIMAL(20,2) DEFAULT 0; \
DECLARE VAR_ISBN varchar(30); \
DECLARE VAR_InstanceId int; \
DECLARE END_TABLE INT DEFAULT 0; \
DECLARE VAR_Price DECIMAL(10,2); \
DECLARE C1 CURSOR FOR \
SELECT price,contains.ISBN,contains.InstanceId,authorid FROM Contains INNER JOIN writtenBy \
ON contains.isbn=writtenby.isbn and authorid=aid INNER JOIN BookInstance \
ON bookinstance.isbn=contains.isbn and contains.instanceid=bookinstance.instanceid INNER JOIN paidforby \
ON paidforby.orderid=contains.orderid and paidforby.cardnumber=ccnumber; \
DECLARE CONTINUE HANDLER FOR NOT FOUND \
SET END_TABLE = 1; \
OPEN C1; \
FETCH C1 INTO VAR_Price,VAR_ISBN, VAR_InstanceId; \
WHILE END_TABLE = 0 DO \
SET spentOnAuthor=spentOnAuthor+VAR_Price; \
FETCH C1 INTO VAR_Price,VAR_ISBN, VAR_InstanceId; \
END WHILE; \
CLOSE C1; \
SET moneyspent = spentOnAuthor; \
END P1