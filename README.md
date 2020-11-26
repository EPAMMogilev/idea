# Idea [![Build Status](https://travis-ci.org/EPAMMogilev/idea.svg?branch=develop)](https://travis-ci.org/EPAMMogilev/idea) [![Coverage Status](https://coveralls.io/repos/EPAMMogilev/idea/badge.svg?branch=develop)](https://coveralls.io/r/EPAMMogilev/idea?branch=develop) [![Build Status](https://semaphoreci.com/api/v1/projects/ede55427-05a0-4aac-8855-441fb02570b6/509584/badge.svg)](https://semaphoreci.com/idea_mogilev/idea)

This is a single page web application for submitting your best ideas on how we can improve Mogilev city. It’s also a place where you can discover and vote on other people’s ideas, as well as communicate with them.


### Local Run

Git:
https://github.com/JavaMahileu/idea.git
branch: jenkins

From project root:

Execute all tests:
gradlew stage
Run:
com.epam.idea.Application
or gradlew bootRun

localhost:9090
first@idea.com / 1234


### Centos

#### Pre-requirements:
install jdk 11:
sudo yum -y install java-11-openjdk-devel
java –version

install gradle 5.6.4:
wget https://services.gradle.org/distributions/gradle-5.6.4-bin.zip
export PATH=$PATH:/opt/gradle/gradle-5.6.4/bin
gradle -v

sudo yum -y install bzip2


#### Build and run:

cd /home/admin/IDEA
git clone https://github.com/JavaMahileu/idea.git
cd idea
git checkout jenkins

cd /home/admin/IDEA/idea
//kill $(cat ./idea-pid.file)
gradle wrapper
./gradlew stage
nohup ./gradlew bootRun & echo $! > ./idea-pid.file &


#### Re-run after system restart:
cd /home/admin/IDEA/idea
nohup ./gradlew bootRun & echo $! > ./idea-pid.file &


#### Ubuntu

# install jdk 11
sudo apt-get install -y openjdk-11-jdk
java –version

# install gradle 5.6.4:

wget https://services.gradle.org/distributions/gradle-5.6.4-bin.zip -P /tmp

sudo apt-get install -y zip unzip

sudo unzip -d /opt/gradle /tmp/gradle-5.6.4-bin.zip

sudo ln -s /opt/gradle/gradle-5.6.4 /opt/gradle/latest

# execute to open text editor:
sudo nano /etc/profile.d/gradle.sh
# add lines:
export GRADLE_HOME=/opt/gradle/latest
export PATH=$GRADLE_HOME/bin:$PATH

sudo chmod +x /etc/profile.d/gradle.sh
source /etc/profile.d/gradle.sh


gradle -v


sudo apt-get install -y bzip2

# Build and run - the same as for Centos

--------------------------------------------------------

localhost:9090
first@idea.com / 1234



--------------------------------------------------------


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

Verify locally Jenkins deploy:

gradlew stage

java -jar -Dserver.port=9090 -Dspring.profiles.active=dev build/libs/idea-boot.war




