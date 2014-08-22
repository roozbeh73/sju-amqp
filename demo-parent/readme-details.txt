The demo-parent provides the following additional functionality:

1.  Establish property file conventions.
2.  Provides profiles for different target platforms: spring, tomcat, jetty, karaf
3.  Provides profiles for different sets of utilities: logging
4.  Relates property conventions to target platforms.

Properties Conventions 
======================

The goal is to be able to use only relative pathnames for all resources at
runtime in a manner that is property driven and invariant across different
platforms.

Project **build** properties used by maven are in the project root:

> ${project.artifactId}.build.properties

These properties are available in the maven pom itself since they are loaded
at the initialize phase by the properties-maven-plugin.

Container specific build properties are located in the project root and follow
the convention of springContainer, tomcatContainer, etc.

> ${container}Container.build.properties

These build properties are applied via maven filters to populate configuration
property files in the resources directory, but they are _not_ loaded into the
maven property space at the initialize phase.  They are strictly for creating
configuration property files via maven filters.

> /src/main/resources/${project.groupId}.${project.artifactId}.properties

To

> src/main/resources/${container}.properties

The path to the container properties file will vary based on whether it is
being referenced from maven at build time or from the application itself at
runtime.  When  accessed at runtime the path will vary based on the platform.
For example, Spring apps will deploy the property to the root of the jar and
hence the root of classpath.  On the other hand a tomcat webapp will deploy
resources to the WEB-INF directory.

Platform Profiles 
=================

Profiles are controlled via flag files names myProfile.profile.  For example, 
spring.profile.  Profiles which are not active typically have a file named  
myProfile.profile.disabled to explicitly indicate the status.  There is one
profile for each platform target, plus utility profiles.

NOTE: Remember that if explicit profiles are specified in the maven commandline 
using the -P option, then _only_ those profiles are activated.  So it is much 
better to use files and environment variables. In general, builds should be 
able to function without any commandline profiles, instead using the profile 
indicator files.  

Test Containers
Builds should always use mvn install without explicit profile flags.
Profiles for running test containers need to explicitly include all profiles.

For Eclipse it is important to use the M2 run configuration
not the external application commands).  

The Jetty and Tomcat builds require changing the packaging in the POM to war.

Test Platforms
==============

The following maven plugins support the different target platforms for testing.

 Spring (Camel):
  From the service project

    mvn camel:run

 Tomcat:
  Note that the target packaging will need to be war.
  To run a tomcat instance from maven go to the Service project.

    mvn tomcat7:run-war
    mvn tomcat7:deploy

 Jetty Build:
  Note that the target packaging will need to be war.
  To run a jetty instance from maven go to the Service project.

    mvn jetty:run-war
    mvn jetty:deploy  

