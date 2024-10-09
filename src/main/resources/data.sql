insert into todo (id, username, description, target_date, done) values 
  (1001, 'user', 'Learn AWS', CURRENT_DATE, false),
  (1002, 'user', 'Learn AWS', CURRENT_DATE, false),
  (1003, 'user', 'Learn AWS', CURRENT_DATE, false)
;

insert into user_details (id, name, birth_date) values
 (1001, 'Alice Bart', CURRENT_DATE),
 (1002, 'Bob Charls', CURRENT_DATE),
 (1003, 'Charlie Doof', CURRENT_DATE)
;

--insert into post (id, user_id, description) values
--  (1001, 1001, 'Lorem Ipsum Dolor Sit Amet AWS'),
--  (1002, 1001, 'Lorem Ipsum Dolor Sit Amet Azure'),
--  (1003, 1002, 'Lorem Ipsum Dolor Sit Amet Spring'),
--  (1004, 1003, 'Lorem Ipsum Dolor Sit Amet Certified'),
--  (1005, 1003, 'Lorem Ipsum Dolor Sit Amet Multi')
--;

select 1 from dual;
