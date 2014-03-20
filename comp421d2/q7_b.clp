-- Question 7: Views Test --

-- Description:
-- THIS FILE IS A TEST TO SHOW THAT THE VIEWS AND QUERY WORK --
-- Because our bookstore is small, we will change the numbers to
-- test the validity of the VIEWS and QUERY.

-- The code is EXACTLY THE SAME.
-- The ONLY things we are changing are the following:
-- - Last 30 days becomes last 500 days.
-- - A book needs to be bought at least 2 times instead of 3.

-- Notice that the Top Buyers list is updated correctly!

DROP VIEW RecentUserPurchases
DROP VIEW PopularBooksSoldOut

-- View 1 : 500 days instead of 30 --
CREATE VIEW RecentUserPurchases AS (SELECT p.username, b.title, b.isbn, o.orderDate FROM Orders as o JOIN contains as c ON o.orderId = c.orderId JOIN paidForBy as p ON o.orderId = p.orderId JOIN Book as b ON b.isbn = c.isbn WHERE o.orderDate > (current timestamp - 500 days))

SELECT * FROM RecentUserPurchases ORDER BY orderDate DESC

-- View 2 : Book needs to be bought at least 2 times instead of 3 --
CREATE VIEW PopularBooksSoldOut AS (SELECT DISTINCT b.title, b.isbn, b.stock FROM Book as b JOIN RecentUserPurchases as r ON b.isbn = r.isbn WHERE b.stock < 5 AND b.isbn IN (SELECT q.isbn FROM RecentUserPurchases as q GROUP BY q.isbn HAVING COUNT(q.isbn) >= 2)) 

SELECT * FROM PopularBooksSoldOut ORDER BY stock ASC

-- Query On View 1 --
-- Some hackery must take place here:
-- SELECT MAX(column) returns the value and not the count!
-- Basically: Group usernames by occurance
--            Find the count of one MAX username (there may be many)
--            Once we have this count, apply it as a condition to find
--            all usernames with this MIN or MAX (or AVE, or whatever)
SELECT username as "Current Top Buyers" FROM RecentUserPurchases GROUP BY username HAVING count(username) = (SELECT count(username) FROM RecentUserPurchases GROUP BY username HAVING username = (SELECT MAX(username) FROM RecentUserPurchases))

DROP VIEW RecentUserPurchases
DROP VIEW PopularBooksSoldOut
