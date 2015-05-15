CREATE TABLE `USER` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT
  , `USERNAME` VARCHAR(20) NOT NULL
  , `EMAIL` VARCHAR(20) NOT NULL
  , `PASSWORD` VARCHAR(60) NOT NULL
  , `CREATION_TIME` TIMESTAMP NOT NULL
  , PRIMARY KEY (`ID`)
);

CREATE TABLE `IDEA` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT
  , `TITLE` VARCHAR(150) NOT NULL
  , `DESCRIPTION` VARCHAR(300) NOT NULL
  , `CREATION_TIME` TIMESTAMP NOT NULL
  , `MODIFICATION_TIME` TIMESTAMP NOT NULL
  , `RATING` INT NOT NULL
  , `USER_ID` BIGINT NOT NULL
  , PRIMARY KEY (`ID`)
  , CONSTRAINT `IDEAS_fk1` FOREIGN KEY (`USER_ID`) REFERENCES USER(`ID`)
);

CREATE TABLE `ROLE` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT
  , `NAME` VARCHAR(10) NOT NULL
  , PRIMARY KEY (`ID`)
);

CREATE TABLE `USER_ROLE` (
  `USER_ID` BIGINT NOT NULL
  , `ROLE_ID` BIGINT NOT NULL
  , PRIMARY KEY (`USER_ID`,`ROLE_ID`)
  , CONSTRAINT `USER_ROLES_fk1` FOREIGN KEY (`USER_ID`) REFERENCES USER(`ID`)
  , CONSTRAINT `USER_ROLES_fk2` FOREIGN KEY (`ROLE_ID`) REFERENCES ROLE(`ID`)
);

CREATE TABLE `TAG` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT
  , `NAME` VARCHAR(20) NOT NULL
  , PRIMARY KEY (`ID`)
);

CREATE TABLE `IDEA_TAG` (
  `IDEA_ID` BIGINT NOT NULL
  , `TAG_ID` BIGINT NOT NULL
  , PRIMARY KEY (`IDEA_ID`,`TAG_ID`)
  , CONSTRAINT `IDEA_TAGS_fk1` FOREIGN KEY (`IDEA_ID`) REFERENCES IDEA(`ID`)
  , CONSTRAINT `IDEA_TAGS_fk2` FOREIGN KEY (`TAG_ID`) REFERENCES TAG(`ID`)
);

CREATE TABLE `COMMENT` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT
  , `USER_ID` BIGINT NOT NULL
  , `IDEA_ID` BIGINT NOT NULL
  , `BODY` VARCHAR(300) NOT NULL
  , `CREATION_TIME` TIMESTAMP NOT NULL
  , `MODIFICATION_TIME` TIMESTAMP NOT NULL
  , `RATING` INT NOT NULL
  , PRIMARY KEY (`ID`)
  , CONSTRAINT `COMMENTS_fk1` FOREIGN KEY (`USER_ID`) REFERENCES USER(`ID`)
  , CONSTRAINT `COMMENTS_fk2` FOREIGN KEY (`IDEA_ID`) REFERENCES IDEA(`ID`)
);
