--When a book's stock falls below 3, create a clearance 75% OFF coupon applicable to that book for the next 2 days. If there are books already on clearance, remove them from the sale.
--CREATE TRIGGER CLEARANCESPECIAL AFTER UPDATE OF Stock ON Book REFERENCING NEW AS newrow FOR EACH ROW WHEN (newrow.Stock < 3 ) BEGIN ATOMIC IF NOT EXISTS(SELECT 1 FROM Coupons WHERE CouponCode='CLEARANCE') THEN INSERT INTO Coupons VALUES ('CLEARANCE',Current Timestamp,Current Timestamp + 2 Days,75.00); INSERT INTO AppliesTo VALUES ('CLEARANCE',UPDATEDROW.isbn); END
--ELSE UPDATE Coupons SET ValidFrom=(Current Timestamp),ValidUntil=(Current Timestamp + 2 Days),Percent=75.00 END

--#SET TERMINATOR @
CREATE TRIGGER ClearanceSpecial AFTER UPDATE OF Stock ON Book
REFERENCING NEW AS n FOR EACH ROW
WHEN (n.Stock < 3 and n.Stock > 0)
  BEGIN ATOMIC
    IF NOT EXISTS(SELECT 1 FROM Coupons WHERE CouponCode='CLEARANCE') THEN INSERT INTO Coupons VALUES ('CLEARANCE',Current Timestamp,Current Timestamp + 2 Days,75.00);
    ELSE 
	DELETE FROM AppliesTo WHERE CouponCode='CLEARANCE';
	UPDATE Coupons SET ValidFrom=(Current Timestamp),ValidUntil=(Current Timestamp + 2 Days),Percent=75.00 WHERE CouponCode='CLEARANCE';
	END IF;
	INSERT INTO AppliesTo VALUES ('CLEARANCE',n.isbn);
  END
@
--#SET TERMINATOR ;

--UPDATE Book SET Stock=9 WHERE title='Twilight'
--SELECT ISBN, Title, Stock FROM Book
--SELECT * FROM AppliesTo WHERE CouponCode='CLEARANCE'
--SELECT * FROM Coupon WHERE CouponCode='CLEARANCE'

