AMQP Demo for SESAR Joint Undertaking
==============================================

This example demonstrates integration options for SESAR and FAA infrastructure using AMQP protocols.
ActiveMQ supports AMQP as a client transport.  It is configured in the activemq.xml file snippet shown below.

See readme in rabbitmq directory for build directions.

    <transportConnectors>
    ...
    <transportConnector name="amqp" uri="amqp://0.0.0.0:61617?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
    ...
    </transportConnectors>

A simple example of consuming from such an ActiveMQ broker via AMQP is illustrated using the camel-amqp component.

        <route id="SWIM_AMQP_client">
            <from uri="amqp:queue:swim_adsb_amqp"/>
            <to uri="log:SWIM_AMQP_client?showHeaders=true" />
        </route>


While this demonstrates ActiveMQ supoprt for AMQP clients, it does not represent integrating FAA SWIM infrastructure
with corresponding EuroControl infrastructure.

A more realistic example for the latter case would be using a Camel route to connect FAA ActiveMQ JMS destinations to
AMQP exchanges.  This connects FAA publishers to FAA SWIM infrastrcutre and then to EuroControl infrastructure which
then delivers it to EuroControl consumers.

        <route id="SWIM_to_EuroControl">
            <from uri="activemq:queue:swim_adsb?concurrentConsumers=5" />
            <to uri="log:SWIM_to_EuroControl?showHeaders=true" />
            <to uri="rabbitmq://localhost:5672/adsb?routingKey=A&amp;username=tadmin&amp;password=tadmin"/>
        </route>

In the route above, messages published via SWIM to the swim_adsb topic via JMS are routed from ActiveMQ to RabbitMQ.
A EuroControl client would typically connect to the EuroControl infrasructure (represented by RabbitMQ).

        <route id="EuroControl_AMQP_client">
            <from uri="rabbitmq://localhost:5672/junk?queue=adsbQ2&amp;routingKey=A&amp;autoDelete=false&amp;username=tadmin&amp;password=tadmin"/>
            <to uri="log:EuroControl_AMQP_client?showHeaders=true" />
        </route>

Conversely, ADSB messages published from EuroControl can be published to RabbitMQ and then routed to ActiveMQ.

        <route id="EuroControl_to_SWIM">
            <from uri="rabbitmq://localhost:5672/junk?queue=adsbQ1&amp;routingKey=A&amp;autoDelete=false&amp;username=tadmin&amp;password=tadmin"/>
            <to uri="log:EuroControl_to_SWIM?showHeaders=true" />
            <to uri="activemq:queue:eurocontrol_adsb" />
        </route>

