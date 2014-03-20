ALTER TABLE Book ADD CHECK(stock>=0)

ALTER TABLE Reviews ADD CHECK (stars>=1 and stars<=5)

INSERT INTO Book VALUES ('911-0316015844','Random Book','Random book that should not be inserted.', 'No genre', -1)

INSERT INTO Reviews VALUES(11, '2012-12-01 21:21:00', 'Should not be able to assign 0 stars.', 0)
