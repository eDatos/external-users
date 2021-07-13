package es.gobcan.istac.edatos.external.users.service;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.siemac.metamac.statistical.operations.core.stream.messages.OperationAvro;

public interface KafkaConsumerService {

    void processDatasetPublicationEvent(GenericRecord genericRecord);
    void processOperationPublicationEvent(ConsumerRecord<String, OperationAvro> consumerRecord);
}
