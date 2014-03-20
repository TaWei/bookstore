--Q1
--Select rows of authors of who have written at least 2 books in the same genre
SELECT * FROM Author WHERE authorid IN (SELECT authorId FROM Author EXCEPT SELECT writtenBy.authorId FROM writtenBy,Book WHERE Book.isbn=writtenBy.isbn GROUP BY writtenby.authorid,book.genre HAVING COUNT(book.genre)<2)

--Q2
--Selecting book titles with a review of greater than 4 stars
SELECT DISTINCT b.title FROM Reviews as r, Book as b, reviewFor as rf WHERE r.stars > 4 AND rf.reviewId=r.reviewId AND rf.isbn=b.isbn

--Q3
--Select all distinct names of clients who have placed an order in March.
SELECT DISTINCT u.firstName, u.lastName FROM User as u, makes as m WHERE m.username=u.username AND m.orderId IN (SELECT O.orderId From Orders as O where O.orderDate >= '2014-03-01 00:00:00' and O.orderDate < '2014-04-01 00:00:00')

--Q4
--Display the book titles that have the most number of coupons that can be applied to it. 
--Also, include in the query, a count of the nubmer of coupons that can be applied to these books. 
SELECT title, COUNT(*) as no_coupons FROM appliesTo AS aT, Book AS b WHERE aT.isbn = b.isbn GROUP BY b.isbn, title HAVING COUNT(*) >= ALL(SELECT COUNT(*) FROM appliesTo GROUP BY isbn)


--Q5
--Display the book titles which have the highest overall average star rating with at least 5 reviews. 
--Also, include in the query, the average star rating for these books.
SELECT  title, AVG(stars) as avg_stars FROM Book as b, Reviews AS r, reviewFor AS rF where b.isbn = rF.isbn AND r.reviewId = rF.reviewId GROUP BY rF.isbn, title HAVING COUNT(*) >= 5 



 
