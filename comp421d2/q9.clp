-- Question 9: EXTRA 10 POINTS --
-- Description: We want to keep a table of book covers to go with the books.
--              To do this, we must have some way of storing the actual
--              book cover images (for eBooks, or for display on a web store).

--              We use the BLOB data type to do this, and use db2's import
--              command to store the book in a new table, linking it to
--              its ISBN as foreign key. These are defined in the 
--              delimitated DEL file.

--              We illustrate this with twilight.jpg. The SELECT command shows
--              the first few bytes of data of the image in the table.
--              These bytes were compared with a hex editor to make sure
--              the picture is stored correctly. The first 0x2000 bytes
--              matched (check the JPG header), the rest was truncated
--              by printing the SELECT command to screen.
--              The missing bytes were still stored correctly in the database.


CREATE TABLE BookCovers(isbn varchar(30) NOT NULL, cover BLOB(1024K), FOREIGN KEY (isbn) REFERENCES Book(isbn), PRIMARY KEY (isbn))

IMPORT FROM ./q9.del OF DEL LOBS FROM ./covers/ MODIFIED BY lobsinfile INSERT INTO BookCovers

SELECT * FROM BookCovers
