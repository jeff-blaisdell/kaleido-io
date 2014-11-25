kaleido-io
==========
A simple Rat Pack application (http://www.ratpack.io/).

### Technologies
The following are the primary technologies found in this project.
* Ratpack.io (http://www.ratpack.io/)
* Google Guice (https://github.com/google/guice)
* Hibernate (http://hibernate.org/)
* Liquibase (http://www.liquibase.org/)
* Vagrant (https://www.vagrantup.com/)
* Chef (https://www.getchef.com/chef/)

### Getting Started
##### Prerequisites
* Java 8 - _Needed to run Rat Pack._
* Gradle - _Needed for running project build tasks._
* Vagrant - _Needed to a VM containing app dependencies such as database._

##### Running Application
1. Start vagrant by navigating to ```kaleido-io/provision/boxes/local``` and execute ```vagrant up``` from command line.  This will launch the VM and database.
2. After VM has finished booting we need to setup our database.  From the command line execute the following:
    * ```gradle generateChangeLog``` _This will initialize the liquibase change log tables._
    * ```gradle update``` _This will create our database schema._
3. Execute ```gradle run``` to launch our Rat Pack application.
