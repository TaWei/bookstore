--Q1
--Update the destination address of all of the pending orders for the user 'kpatel' that haven't been delivered yet to any of the the addresses belonging to the user'jamesta'
UPDATE Orders as o set addrId = (SELECT addrID FROM livesAt where username='jamesta' FETCH FIRST ROW ONLY) WHERE estArrivalDate > CURRENT_TIMESTAMP AND orderId IN (SELECT m.orderId FROM makes m WHERE m.orderId = o.orderId AND m.username = 'kpatel') 

--Q2 
--Delete all of the book instances that are not present in any order
DELETE FROM BookInstance AS bi WHERE bi.isbn NOT IN (SELECT DISTINCT isbn FROM contains)

--Q2
--Renew the coupon with id '50OFF' by making it valid for 7 days, but only if it is expired. 
Update Coupons set validUntil=(select current timestamp + 7 days from sysibm.sysdummy1) WHERE couponCode='50OFF' and validUntil < CURRENT_TIMESTAMP  

--Q3
--Create an order with DHL Expedited Shipping to address with addrID=1 and add 1 of each in-stock Sci-Fi novel to the order (and update the stock of the book)
INSERT INTO Orders VALUES((SELECT MAX(orderId) FROM Orders)+1,CURRENT TIMESTAMP,NULL,NULL,'DHL Expedited',0.00,0.00,0.00,1)
INSERT INTO contains SELECT (SELECT MAX(orderId) FROM Orders),MAX(instanceId),isbn FROM BookInstance WHERE BookInstance.isbn IN (SELECT isbn FROM Book WHERE Genre='Sci-Fi' AND Stock>0) GROUP BY isbn
UPDATE Book SET Stock=Stock-1 WHERE isbn IN (SELECT DISTINCT isbn FROM Book WHERE Genre='Sci-Fi' AND Stock>0)

