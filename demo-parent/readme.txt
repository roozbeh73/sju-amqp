demo-parent

Common parent pom for all demo projects.  Should ideally allow to build the
project for karaf, tomcat, jetty, or spring by justing changing package type and
setting profiles.  This project depends on the apache-platform project.  Note
Note that although provided as apache v2 licensed code, this project is not part
of an Apache Software foundation project.  The "apache platform" just refers to
the fact that the project is intended to compose multiple apache projects into a
logical "platform".  This demo-template uses that platform and adds maven
conventions for targeting multiple runtime container environments.

Profiles are controlled using file flags in the profiles folder,
e.g. spring.profile, tomcat.profile, jetty.profile, karaf.profile,  There is one
non-platform profile for logging.  The properties used by the project are 
designed so that they can be overridden at various stages in the development 
cycle via the container specific properties file.  This is done via coordinated
use of Maven filters and Spring / OSGI / Camel placeholders.

## Property File conventions

### Build Property Files

Platform specific build property files are located in the project root
directory.  Property file conventions are as follows.  First, there must be a
platform  property file in the project directory,
e.g. springContainer.properties.  This file must follow the convention of
<platform>Container.properties.  In addition, there must be an application
properties file following the convention <artifactId>.properties.

### Configuration Property Files

The actual configuration properties "contract" is specified in the 
src/main/resources/container.properties file.  This template is instantiated by
Maven filters with platform specific properties.  It is important to distinguish
here between the maven **build** properties in the project root and the 
**configuration** properties in the resources directory.  Both are platform
specific, but the first is for the maven build environment and the second is
used at runtime.  

When unit tests are run the surefire plugin is configured to use the spring
container container.xml via the containerConfig system property.  However, 
it is still the target platform's properties that are being applied.  These can
be overridden via the container.test.properties which is located in the
src/test/resources directory.

Likewise, the  camel-maven-bundle plugin is configured to use the spring
container via the same containerConfig system property.  However, the platform
properties will still be applied.

### OSGI and Blueprint

By convention this pom looks for a properties file in the resources directory of
the name ${project.groupId}.${project.artifactId}.properties and publishes this
as a stand-alone config file to maven with a classifier of osgi and type of cfg.

By convention this pom looks for a features file in the resource directory of
the name ${project.artifactId}-features.xml and publishes this as a stand-alone
config file to maven with a classifier of features and type of xml.  This
features file may include the osgi cfg file mentioned above as a transitive
dependency link using the configfile child element of the feature.

### Dependency Management

To change which version of Talend is used, change the tesb.version and the four
versions for the sub-products: cxf, camel, activemq, and karaf.  These should
match and be consistent with the versions specified in the appropriate Talend
ESB build which can be found at https://github.com/Talend/tesb-rt-se .