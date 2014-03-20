-- Question 7: Views --

-- Description:
-- View 1: RECENT USER PURCHASES
--         We want to find users who have paid for a book over the past 30 days.
--         We list users who have paid for something versus users who have
--         only ordered an item - active users are customers who complete a
--         transaction. Those are the users we want to target in terms of,
--         say, marketing or promotional purposes (free book signings, etc).
--         The view should show the username, the book they bought, its
--         isbn and the date they bought it. The view gives a quick way to
--         access this information.

-- View 2: POPULAR BOOKS SOLD OUT
--         We want to have a quick way to find out which books need to be
--         restocked immediately. To find this, we refer to the previous
--         view. If a book is 'popular' (has been bought at least 3 times
--         over the past 30 days), we check its stock. We list books that
--         have a stock less than 5 to flag them to be restocked.
--         The view should have columns: title, isbn and current stock.

-- Query : We want to find the user(s) with the most book purchases in the
--         past 30 days to send them a gift.


-- View 1 --
CREATE VIEW RecentUserPurchases AS (SELECT p.username, b.title, b.isbn, o.orderDate FROM Orders as o JOIN contains as c ON o.orderId = c.orderId JOIN paidForBy as p ON o.orderId = p.orderId JOIN Book as b ON b.isbn = c.isbn WHERE o.orderDate > (current timestamp - 30 days))

SELECT * FROM RecentUserPurchases ORDER BY orderDate DESC

-- View 2 --
CREATE VIEW PopularBooksSoldOut AS (SELECT DISTINCT b.title, b.isbn, b.stock FROM Book as b JOIN RecentUserPurchases as r ON b.isbn = r.isbn WHERE b.stock < 5 AND b.isbn IN (SELECT q.isbn FROM RecentUserPurchases as q GROUP BY q.isbn HAVING COUNT(q.isbn) >= 3)) 

SELECT * FROM PopularBooksSoldOut ORDER BY stock ASC

-- Query On View 1 --
-- Some hackery must take place here:
-- SELECT MAX(column) returns the value and not the count!
-- Basically: Group usernames by occurance
--            Find the count of one MAX username (there may be many)
--            Once we have this count, apply it as a condition to find
--            all usernames with this MIN or MAX (or AVE, or whatever)
SELECT username as "Current Top Buyers" FROM RecentUserPurchases GROUP BY username HAVING count(username) = (SELECT count(username) FROM RecentUserPurchases GROUP BY username HAVING username = (SELECT MAX(username) FROM RecentUserPurchases))

