-- DATABASE CLEANUP --
DROP TABLE User
DROP TABLE Book
DROP TABLE BookInstance
DROP TABLE Author
DROP TABLE Publisher
DROP TABLE Orders
DROP TABLE Reviews
DROP TABLE Address
DROP TABLE Coupons
DROP TABLE CreditCard
DROP TABLE reviewFor
DROP TABLE makes
DROP TABLE contains
DROP TABLE writtenBy
DROP TABLE publishedBy
DROP TABLE appliesTo
DROP TABLE reviewedBy
DROP TABLE livesAt
DROP TABLE billsTo
DROP TABLE paidForBy

DROP VIEW RecentUserPurchases
DROP VIEW PopularBooksSoldOut

-- TABLE CREATION --
CREATE TABLE User(username varchar(20) NOT NULL, firstName varchar(30) NOT NULL, lastName varchar(30) NOT NULL, email varchar(40) NOT NULL, password varchar(15) NOT NULL, PRIMARY KEY (username))

CREATE TABLE Book(isbn varchar(30) NOT NULL, title varchar(300) NOT NULL, summary varchar(2000), genre varchar(50) NOT NULL, stock int NOT NULL, PRIMARY KEY (isbn))

CREATE TABLE BookInstance(isbn varchar(30) NOT NULL, instanceId int NOT NULL, condition varchar(30) NOT NULL, price decimal(10,2), FOREIGN KEY (isbn) REFERENCES Book(isbn), PRIMARY KEY (isbn,instanceId))

CREATE TABLE Author(authorId INT NOT NULL, firstName varchar(30) NOT NULL, lastName varchar(30) NOT NULL, PRIMARY KEY (authorId))

CREATE TABLE Publisher(publisherId int NOT NULL, name varchar(100) NOT NULL, country varchar(50) NOT NULL, PRIMARY KEY (publisherId))

CREATE TABLE Address(addrId int NOT NULL, streetAddr varchar(250) NOT NULL, city varchar(50) NOT NULL, province varchar(50) NOT NULL, zip varchar(10) NOT NULL, country varchar(50) NOT NULL, PRIMARY KEY (addrId))

CREATE TABLE Orders(orderId int NOT NULL, orderDate timestamp NOT NULL, estArrivalDate timestamp, trackingNumber varchar(20), shippingMethod varchar(30) NOT NULL, shFees decimal(10,2) NOT NULL, promotionalDiscount decimal(10,2) NOT NULL, finalCost decimal(10,2) NOT NULL, addrId int NOT NULL, PRIMARY KEY (orderId), FOREIGN KEY(addrId) REFERENCES Address)

CREATE TABLE Reviews(reviewId int NOT NULL, reviewDate timestamp NOT NULL, comments varchar(500), stars int NOT NULL, PRIMARY KEY (reviewId))

CREATE TABLE Coupons(couponCode varchar(20) NOT NULL, validFrom timestamp NOT NULL, validUntil timestamp NOT NULL, percent decimal(5,2) NOT NULL, PRIMARY KEY (couponCode))

CREATE TABLE CreditCard(cardNumber varchar(20) NOT NULL, username varchar(20) NOT NULL, cardHolderName varchar(60) NOT NULL, cardType varchar(30) NOT NULL, expiryDate timestamp NOT NULL, ccv2 varchar(5) NOT NULL, FOREIGN KEY (username) REFERENCES User(username), PRIMARY KEY (cardNumber,username))

CREATE TABLE reviewFor(reviewId INT NOT NULL, isbn  VARCHAR(30) NOT NULL, PRIMARY KEY(reviewId, isbn), FOREIGN KEY(reviewId) REFERENCES Reviews, FOREIGN KEY(isbn) REFERENCES Book)

CREATE TABLE makes(username VARCHAR(30) NOT NULL, orderID INT NOT NULL, PRIMARY KEY(username, orderId), FOREIGN KEY(username) REFERENCES User, FOREIGN KEY(orderID) REFERENCES Orders)

CREATE TABLE contains(orderId INT NOT NULL,  instanceId INT NOT NULL, isbn VARCHAR(30) NOT NULL, PRIMARY KEY(orderId, instanceId, isbn), FOREIGN KEY(orderId) REFERENCES Orders, FOREIGN KEY(instanceId, isbn) REFERENCES BookInstance(instanceId,isbn))

CREATE TABLE writtenBy(authorId INT NOT NULL, isbn VARCHAR(30) NOT NULL, PRIMARY KEY(authorId, isbn), FOREIGN KEY(authorId) REFERENCES Author, FOREIGN KEY(isbn) REFERENCES BOOK)

CREATE TABLE publishedBy(publisherId INT NOT NULL, isbn VARCHAR(30) NOT NULL, PRIMARY KEY(publisherId, isbn), FOREIGN KEY(publisherId) REFERENCES Publisher, FOREIGN KEY(isbn) REFERENCES BOOK)

CREATE TABLE appliesTo(couponCode VARCHAR(20) NOT NULL, isbn VARCHAR(30) NOT NULL, PRIMARY KEY(couponCode, isbn), FOREIGN KEY(couponCode) REFERENCES Coupons, FOREIGN KEY(isbn) REFERENCES Book)

CREATE TABLE reviewedBy(username VARCHAR(30) NOT NULL, reviewId INT NOT NULL, PRIMARY KEY(username, reviewId), FOREIGN KEY(username) REFERENCES User, FOREIGN KEY(reviewId) REFERENCES Reviews)

CREATE TABLE livesAt(username VARCHAR(30) NOT NULL, addrId INT NOT NULL, PRIMARY KEY(username, addrId), FOREIGN KEY(username) REFERENCES User, FOREIGN KEY(addrId) REFERENCES Address)

CREATE TABLE billsTo(addrId INT NOT NULL, cardNumber VARCHAR(20) NOT NULL, username varchar(20) NOT NULL, PRIMARY KEY(addrId, cardNumber, username), FOREIGN KEY(addrId) REFERENCES Address, FOREIGN KEY(cardNumber, username) REFERENCES CreditCard)

CREATE TABLE paidForBy(orderId INT NOT NULL, cardNumber VARCHAR(20) NOT NULL, username varchar(20) NOT NULL, PRIMARY KEY(orderId, cardNumber, username), FOREIGN KEY(orderId) REFERENCES Orders, FOREIGN KEY(cardNumber, username) REFERENCES CreditCard)


