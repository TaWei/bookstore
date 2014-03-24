--Please run this after running Q3.clp

SELECT ISBN, Title, Stock FROM Book
SELECT * FROM AppliesTo WHERE CouponCode='CLEARANCE'
SELECT * FROM Coupon WHERE CouponCode='CLEARANCE'
--Does not trigger Clearance Special
UPDATE Book SET Stock=9 WHERE title='Twilight'

SELECT * FROM AppliesTo WHERE CouponCode='CLEARANCE'
SELECT * FROM Coupon WHERE CouponCode='CLEARANCE'

--Triggers Clearance Special
UPDATE Book SET Stock=2 WHERE title='Twilight'
SELECT * FROM AppliesTo WHERE CouponCode='CLEARANCE'
SELECT * FROM Coupon WHERE CouponCode='CLEARANCE'