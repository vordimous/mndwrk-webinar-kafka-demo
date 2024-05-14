#### Architecture

![architecture](./architecture/mndwrk-demo.drawio.png)

#### Docker

```sh
docker-compose up -d
```

#### Register Schema

```sh
jq '. | {schema: tojson}' schemas/avro/zilla-request.avsc  | \
    curl -X POST http://localhost:8081/subjects/mndwrk-zilla-request-value/versions \
         -H "Content-Type:application/json" \
         -d @-
jq '. | {schema: tojson}' schemas/avro/zilla-response.avsc  | \
    curl -X POST http://localhost:8081/subjects/mndwrk-zilla-response-value/versions \
         -H "Content-Type:application/json" \
         -d @-
```

#### Send data

```sh
curl --location 'http://localhost:8080/api/v1/sensor-data' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--data '{
    "source": { "string": "TLC"},
    "description": { "string": "Green Signal" }
}'
```

- Get the message key from topic in [console ui](http://localhost:9000/redpanda/topics/mndwrk-zilla-request?p=-1&s=50&o=-1#messages).

#### Get data

```sh
curl --location 'http://localhost:8080/api/v1/sensor-data/<key>' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' -v
```

- see error:

```
org.agrona.concurrent.AgentTerminationException: java.lang.IllegalArgumentException: offset=75 length=1701016181 not valid for capacity=1073741824
    at io.aklivity.zilla.runtime.engine@0.9.79/io.aklivity.zilla.runtime.engine.internal.registry.EngineWorker.doWork(EngineWorker.java:823)
    at org.agrona.core/org.agrona.concurrent.AgentRunner.doDutyCycle(AgentRunner.java:291)
    at org.agrona.core/org.agrona.concurrent.AgentRunner.run(AgentRunner.java:164)
    at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: java.lang.IllegalArgumentException: offset=75 length=1701016181 not valid for capacity=1073741824
    at org.agrona.core/org.agrona.concurrent.UnsafeBuffer.boundsCheckWrap(UnsafeBuffer.java:1674)
    at org.agrona.core/org.agrona.concurrent.UnsafeBuffer.wrap(UnsafeBuffer.java:246)
    at io.aklivity.zilla.runtime.binding.kafka@0.9.79/io.aklivity.zilla.runtime.binding.kafka.internal.types.OctetsFW.wrap(OctetsFW.java:41)
    at io.aklivity.zilla.runtime.binding.kafka@0.9.79/io.aklivity.zilla.runtime.binding.kafka.internal.types.cache.KafkaCachePaddedValueFW.wrap(KafkaCachePaddedValueFW.java:48)
    at io.aklivity.zilla.runtime.binding.kafka@0.9.79/io.aklivity.zilla.runtime.binding.kafka.internal.cache.KafkaCacheFile.readBytes(KafkaCacheFile.java:142)
    at io.aklivity.zilla.runtime.binding.kafka@0.9.79/io.aklivity.zilla.runtime.binding.kafka.internal.cache.KafkaCacheCursorFactory$KafkaCacheCursor.nextConvertedEntry(KafkaCacheCursorFactory.java:311)
    at io.aklivity.zilla.runtime.binding.kafka@0.9.79/io.aklivity.zilla.runtime.binding.kafka.internal.cache.KafkaCacheCursorFactory$KafkaCacheCursor.next(KafkaCacheCursorFactory.java:227)
    at io.aklivity.zilla.runtime.binding.kafka@0.9.79/io.aklivity.zilla.runtime.binding.kafka.internal.stream.KafkaCacheClientFetchFactory$KafkaCacheClientFetchStream.doClientReplyDataIfNecessary(KafkaCacheClientFetchFactory.java:1172)
    at io.aklivity.zilla.runtime.binding.kafka@0.9.79/io.aklivity.zilla.runtime.binding.kafka.internal.stream.KafkaCacheClientFetchFactory$KafkaCacheClientFetchStream.onClientReplyWindow(KafkaCacheClientFetchFactory.java:1659)
    at io.aklivity.zilla.runtime.binding.kafka@0.9.79/io.aklivity.zilla.runtime.binding.kafka.internal.stream.KafkaCacheClientFetchFactory$KafkaCacheClientFetchStream.onClientMessage(KafkaCacheClientFetchFactory.java:981)
    at io.aklivity.zilla.runtime.binding.kafka@0.9.79/io.aklivity.zilla.runtime.binding.kafka.internal.stream.KafkaCacheClientFetchFactory.lambda$newStream$2(KafkaCacheClientFetchFactory.java:248)
    at io.aklivity.zilla.runtime.engine@0.9.79/io.aklivity.zilla.runtime.engine.internal.registry.EngineWorker.handleReadReply(EngineWorker.java:1440)
    at io.aklivity.zilla.runtime.engine@0.9.79/io.aklivity.zilla.runtime.engine.internal.registry.EngineWorker.handleRead(EngineWorker.java:1209)
    at io.aklivity.zilla.runtime.engine@0.9.79/io.aklivity.zilla.runtime.engine.internal.concurent.ManyToOneRingBuffer.read(ManyToOneRingBuffer.java:181)
    at io.aklivity.zilla.runtime.engine@0.9.79/io.aklivity.zilla.runtime.engine.internal.registry.EngineWorker.doWork(EngineWorker.java:817)
    ... 3 more
    Suppressed: java.lang.Exception: [engine/data#1]        [0x010100000000003a] streams=[consumeAt=0x00005678 (0x0000000000005678), produceAt=0x00005778 (0x0000000000005778)]
            at io.aklivity.zilla.runtime.engine@0.9.79/io.aklivity.zilla.runtime.engine.internal.registry.EngineWorker.doWork(EngineWorker.java:821)
            ... 3 more
```
