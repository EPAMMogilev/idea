USE `ihaveidea`;

SET FOREIGN_KEY_CHECKS = 0;

truncate table ihaveidea.USER;
truncate table ihaveidea.IDEA;
truncate table ihaveidea.ROLE;
truncate table ihaveidea.USER_ROLE;
truncate table ihaveidea.TAG;
truncate table ihaveidea.IDEA_TAG;
truncate table ihaveidea.IDEA_LIKES;
truncate table ihaveidea.COMMENT;

insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', '2015-02-28 00:00:00'); -- pass :'admin'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('FirstUser', 'first@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('SecondUser', 'second@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('ThirdUser', 'third@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('FourthUser', 'fourth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('FifthUser', 'fifth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('SixthUser', 'sixth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('SeventhUser', 'seventh@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('EighthUser', 'eighth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('NinthUser', 'ninth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TenthUser', 'tenth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('EleventhUser', 'eleventhd@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwelfthUser', 'twelfth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('ThirteenthUser', 'thirteenth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('FourteenthUser', 'tfourteenth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('FifteenthUser', 'fifteenth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('SixteenthUser', 'sixteenth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('SeventeenthUser', 'seventeenth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('EighteenthUser', 'eighteenth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('NineteenthUser', 'nineteenth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwentiethUser', 'twentieth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwentyFirstUser', 'twenty_first@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwentySecondUser', 'twenty_second@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwentyThirdUser', 'twenty_third@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwentyFourthUser', 'twenty_fourth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwentyFifthUser', 'twenty_fifth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwentySixthUser', 'twenty_six@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwentySeventhUser', 'twenty_seventh@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwentyEighthUser', 'twenty_eighth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('TwentyNinthUser', 'twenty_ninth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'
insert into USER (USERNAME, EMAIL, PASSWORD, CREATION_TIME) values ('ThirtiethUser', 'thirtieth@idea.com', '81dc9bdb52d04dc20036dbd8313ed055', '2015-05-03 00:00:00'); -- pass :'1234'

insert into IDEA (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID, LATITUDE, LONGITUDE) values ('Велопарковка', 'Офис ТК4 очень ждёт её возращения', '2015-05-13', '2015-05-13', 8, 4, 53.903200, 30.335055);
insert into IDEA (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID, LATITUDE, LONGITUDE) values ('Площадка для аджилити', 'Я думаю,  у многих владельцев собак возникала необходимость в такой площадке. К сожалению, в нашем городе их единицы. Предлагаю оборудовать как минимум ещё одну.', '2015-01-26', '2015-01-26', 2, 3, 53.911445, 30.328036);
insert into IDEA (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID, LATITUDE, LONGITUDE) values ('Видеонаблюдение для зоосада', 'Как насчёт установки системы видеонаблюдения? Могилевчане могли бы наблюдать за жизнью животных из дома в режиме реального времени', '2015-03-30', '2015-03-30', 12, 2, 53.860442, 30.258000);
insert into IDEA (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID, LATITUDE, LONGITUDE) values ('Бесплатный Wi-Fi', 'В Могилеве нужны зоны бесплатного доступа в интернет. Особенно на площади Славы', '2015-06-02', '2015-06-02', 23, 2, 53.894325, 30.330429);
insert into IDEA (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID, LATITUDE, LONGITUDE) values ('Ремонт Пушкинского моста', 'Мост давно требует полной реконструкции', '2015-04-05', '2015-04-05', 31, 4, 53.892096, 30.329662);
insert into IDEA (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID, LATITUDE, LONGITUDE) values ('Могилевский полумарафон', 'Предлагаю организовать городской полумарафон как для любителей, так и для профессионалов. Повысим интерес к бегу у молодёжи!', '2015-04-16', '2015-04-16', 19, 2, 53.895185, 30.330892);
insert into IDEA (TITLE, DESCRIPTION, CREATION_TIME, MODIFICATION_TIME, RATING, USER_ID, LATITUDE, LONGITUDE) values ('Котокафе', 'У нас есть антикафе, но нет котокафе. А ведь это было бы отличным местом для отдыха!', '2014-12-21', '2015-12-21', 0, 3, 53.895815, 30.333220);

insert into ROLE (NAME) values ('ADMIN');
insert into ROLE (NAME) values ('USER');

insert into USER_ROLE (USER_ID, ROLE_ID) values (1, 1);
insert into USER_ROLE (USER_ID, ROLE_ID) values (2, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (3, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (4, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (5, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (6, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (7, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (8, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (9, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (10, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (11, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (12, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (13, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (14, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (15, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (16, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (17, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (18, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (19, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (20, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (21, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (22, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (23, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (24, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (25, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (26, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (27, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (28, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (29, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (30, 2);
insert into USER_ROLE (USER_ID, ROLE_ID) values (31, 2);

insert into TAG (NAME) values ('Спорт');
insert into TAG (NAME) values ('Транспорт');
insert into TAG (NAME) values ('Культура');
insert into TAG (NAME) values ('Животные');

insert into IDEA_TAG (IDEA_ID, TAG_ID) values (1, 2);
insert into IDEA_TAG (IDEA_ID, TAG_ID) values (2, 4);
insert into IDEA_TAG (IDEA_ID, TAG_ID) values (3, 3);
insert into IDEA_TAG (IDEA_ID, TAG_ID) values (4, 3);
insert into IDEA_TAG (IDEA_ID, TAG_ID) values (5, 2);
insert into IDEA_TAG (IDEA_ID, TAG_ID) values (6, 2);
insert into IDEA_TAG (IDEA_ID, TAG_ID) values (7, 4);

insert into IDEA_LIKES (IDEA_ID, USER_ID) values (1, 2);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (1, 3);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (1, 4);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (1, 5);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (1, 6);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (1, 7);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (1, 8);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (1, 9);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (2, 2);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (2, 3);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 2);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 3);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 4);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 5);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 6);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 7);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 8);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 9);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 10);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 11);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 12);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (3, 13);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 2);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 3);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 4);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 5);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 6);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 7);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 8);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 9);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 10);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 11);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 12);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 13);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 14);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 15);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 16);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 17);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 18);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 19);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 20);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 21);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 22);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 23);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (4, 24);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 1);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 2);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 3);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 4);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 5);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 6);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 7);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 8);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 9);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 10);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 11);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 12);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 13);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 14);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 15);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 16);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 17);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 18);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 19);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 20);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 21);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 22);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 23);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 24);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 25);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 26);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 27);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 28);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 29);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 30);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (5, 31);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 2);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 3);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 4);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 5);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 6);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 7);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 8);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 9);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 10);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 11);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 12);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 13);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 14);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 15);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 16);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 17);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 18);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 19);
insert into IDEA_LIKES (IDEA_ID, USER_ID) values (6, 20);

insert into COMMENT (USER_ID, IDEA_ID, BODY, CREATION_TIME, MODIFICATION_TIME, RATING) values (1, 1, 'Awesome!', '2014-08-02 00:00:00', '2014-12-24 00:00:00', 5);
insert into COMMENT (USER_ID, IDEA_ID, BODY, CREATION_TIME, MODIFICATION_TIME, RATING) values (2, 2, 'Super!', '2014-06-12 00:00:00', '2014-09-20 00:00:00', 10);
insert into COMMENT (USER_ID, IDEA_ID, BODY, CREATION_TIME, MODIFICATION_TIME, RATING) values (3, 3, 'Genius!', '2015-01-10 00:00:00', '2015-02-03 00:00:00', -6);
