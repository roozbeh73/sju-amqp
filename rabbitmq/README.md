## Prerequisites

## Build

    mvn install

## Deploy

From karaf commandline on the same machine a the build

    karaf@root> features:addurl mvn:com.talend.se.demo/rabbitmq/5.5.1-SNAPSHOT/xml/features
    karaf@root> features:install demo-rabbitmq

The same commands above will work assuming that the bundles and features file
created by the install have been deployed to a Nexus server and Karaf is
pointing at the Nexus repository.

The Nexus server used by Karaf is configured in the
etc/org.ops4j.pax.url.mvn.cfg file as shown below.

    org.ops4j.pax.url.mvn.repositories= \
        http://tadmin:tadmin@localhost:8081/nexus/content/repositories/releases@id=tesb.release,\
        http://tadmin:tadmin@localhost:8081/nexus/content/repositories/snapshots@snapshots@id=tesb.snapshot, \
    ...