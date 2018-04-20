# Idea [![Build Status](https://travis-ci.org/EPAMMogilev/idea.svg?branch=develop)](https://travis-ci.org/EPAMMogilev/idea) [![Coverage Status](https://coveralls.io/repos/EPAMMogilev/idea/badge.svg?branch=develop)](https://coveralls.io/r/EPAMMogilev/idea?branch=develop) [![Build Status](https://semaphoreci.com/api/v1/projects/ede55427-05a0-4aac-8855-441fb02570b6/509584/badge.svg)](https://semaphoreci.com/idea_mogilev/idea)

This is a single page web application for submitting your best ideas on how we can improve Mogilev city. It’s also a place where you can discover and vote on other people’s ideas, as well as communicate with them.


###Technology stack on the server side
App uses Java 8 and a number of open source projects to work properly:
* With [Spring IO Platform](http://platform.spring.io/platform/) we don’t have to worry about dependency versions because the Spring IO Platform takes care of that, e.g. we don’t have to worry about incompatibility issues because we know that our dependencies work together like a charm.
* [Spring MVC REST service](http://spring.io/guides/gs/rest-service/) with [JSON payload](https://github.com/FasterXML/jackson). Uses spring-test for integration tests.
* [Gradle](http://gradle.org/) configuration for building and testing
* [Spring Security](http://projects.spring.io/spring-security/)
* [Spring HATEOAS](http://projects.spring.io/spring-hateoas/)
* We use [Hibernate](http://hibernate.org/orm/) because it is the most common JPA provider.
* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/) hides the used JPA provider behind its repository abstraction.
* We use the [HikariCP](https://github.com/brettwooldridge/HikariCP) datasource because it is the fastest datasource on this planet.
* We use the [H2 in-memory database](http://www.h2database.com/html/main.html) because it makes our application easier to run.

###Technology stack on the client side
Single Web page application:
* [AngularJS](https://angularjs.org/) - HTML enhanced for web apps!
* [Twitter Bootstrap](http://twitter.github.com/bootstrap/) - great UI boilerplate for modern web apps
* [jQuery](http://jquery.com) - duh

Easy installation of new JavaScript libraries with [Yarn](https://github.com/srs/gradle-node-plugin/blob/master/docs/node.md).
Build, optimization and live reload with [Grunt](http://gruntjs.com/). Testing with [Karma](http://karma-runner.github.io/0.12/index.html)

### Version
0.0.1

### Installation

You need Gradle installed:

```sh
$ git clone [git-repo-url] idea
$ cd idea
```

--------------------------------------------------------

Build: 

gradlew clean build -Drunway=dev


Local Run with H2 database:

//gradlew bootRun
//http://localhost:9090

//gradlew tomcatRunWar
//http://localhost:9090/Idea


first@idea.com / 1234


e2e-tests (after app is run, in another console window):

gradlew grunt_e2e



Note:

to stop hanging PhantomJS process:

taskkill /f /im phantomjs.exe

--------------------------------------------------------

HEROKU

--------------------------------------------------------

Test locally before pushing to Heroku:

gradlew stage

heroku local -f Procfile.local

http://localhost:9090/

-----------

Deploy to Heroku:

commit changes

run:
git push heroku master


https://idea-app-3.herokuapp.com/


Live logs:
heroku logs --tail

All logs:
heroku logs -n 1500



heroku restart -a idea-app-3

---------------

Jenkins

gradlew stage

java -jar -Dserver.port=9090 -Dspring.profiles.active=dev build/libs/idea-boot.war




