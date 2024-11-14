select * from books;

select count(*) from users;
select count(*) from books;
select count(*) from book_borrow
where is_returned=0;