package es.gobcan.istac.edatos.external.users.service;

import org.apache.avro.generic.GenericRecord;

public interface KafkaConsumerService {

	void processDatasetPublicationEvent(GenericRecord record);
}
