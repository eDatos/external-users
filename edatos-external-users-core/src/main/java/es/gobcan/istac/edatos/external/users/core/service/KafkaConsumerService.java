package es.gobcan.istac.edatos.external.users.core.service;

import org.apache.avro.generic.GenericRecord;

public interface KafkaConsumerService {

	void processDatasetPublicationEvent(GenericRecord record);
}
