package com.mndwrk.webinar.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mndwrk.webinar.demo.config.KafkaConfig;
import com.mndwrk.webinar.demo.entity.ConsumedEvent;
import com.mndwrk.webinar.demo.entity.ProducedEvent;
import com.mndwrk.webinar.demo.kafka.KafkaStreamsCustomizer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.serializer.JsonSerde;

public class StreamsTesting {

    private TopologyTestDriver testDriver;

    private TestInputTopic<String, ConsumedEvent> inputTopic;
    private TestOutputTopic<String, ProducedEvent> outputTopic;

    /* Change configured stream in configureBuilder!!! */
  //  @BeforeEach
 /*   public void setup() {

        final StreamsBuilder streamsBuilder = new StreamsBuilder();

        final KafkaStreamsCustomizer customInfrastructure = new KafkaStreamsCustomizer();

        customInfrastructure.configureBuilder(streamsBuilder);

        final Topology topology = streamsBuilder.build();

        testDriver = new TopologyTestDriver(topology);

        inputTopic = testDriver.createInputTopic(KafkaConfig.KAFKA_INBOUND_TOPIC_NAME, new Serdes.StringSerde().serializer(),
                new JsonSerde<>(ConsumedEvent.class).serializer());
        outputTopic = testDriver.createOutputTopic(KafkaConfig.KAFKA_OUTBOUND_TOPIC_NAME, new Serdes.StringSerde().deserializer(),
                new JsonSerde<>(ProducedEvent.class).deserializer());


    } */

    //@AfterEach
    public void tearDown() {
        testDriver.close();
    }

   // @Test
    public void shouldAddKey() {

        final ConsumedEvent inboundEvent = ConsumedEvent.builder().source("TLC").summary("Inbound message").build();

        inputTopic.pipeInput(null, inboundEvent);
        assertThat(outputTopic.readKeyValue().key).isEqualTo("TLX");
    }

}
