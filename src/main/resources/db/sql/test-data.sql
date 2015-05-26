insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('Avocado', 'Chris@test.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('Apple', 'Scott@test.com', '827ccb0eea8a706c4c34a16891f84e7b', '2015-11-02 00:00:00'); -- pass :'12345'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('Orange', 'John@test.com', 'e10adc3949ba59abbe56e057f20f883e', '2015-02-28 00:00:00'); -- pass :'123456'

insert into IDEA (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID) values ('Nonlinear Optimization by Successive Linear Programming', 'F. Palacios-Gomez & L. Lasdon & M. Engquist', '2015-01-01 03:14:07', '2015-01-02 00:00:00', 2, 1);
insert into IDEA (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID) values ('Interactive fuzzy goal programming approach for bilevel programming problem', 'Arora, S.R. & Gupta, Ritu', '2015-02-02 00:00:00', '2015-02-03 00:00:00', 3, 2);
insert into IDEA (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID) values ('Two-Segment Separable Programming', 'R. R. Meyer', '2014-12-01', '2014-12-12', -2, 3);

insert into ROLE (NAME) values ('ADMIN');
insert into ROLE (NAME) values ('USER');

insert into USER_ROLE (USER_ID, ROLE_ID) values (1, 1);
insert into USER_ROLE (USER_ID, ROLE_ID) values (2, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (3, 2);

insert into TAG (NAME) values ('Sport');
insert into TAG (NAME) values ('Transport');
insert into TAG (NAME) values ('Culture');

insert into IDEA_TAG (IDEA_ID, TAG_ID) values (1, 1);
insert into IDEA_TAG (IDEA_ID, TAG_ID) values (2, 2);
insert into IDEA_TAG (IDEA_ID, TAG_ID) values (3, 1);

insert into COMMENT (USER_ID, IDEA_ID, BODY, CREATION_TIME, MODIFICATION_TIME, RATING) values (1, 1, 'Awesome!', '2014-08-02 00:00:00', '2014-12-24 00:00:00', 5);
insert into COMMENT (USER_ID, IDEA_ID, BODY, CREATION_TIME, MODIFICATION_TIME, RATING) values (2, 2, 'Super!', '2014-06-12 00:00:00', '2014-09-20 00:00:00', 10);
insert into COMMENT (USER_ID, IDEA_ID, BODY, CREATION_TIME, MODIFICATION_TIME, RATING) values (3, 3, 'Genius!', '2015-01-10 00:00:00', '2015-02-03 00:00:00', -6);
