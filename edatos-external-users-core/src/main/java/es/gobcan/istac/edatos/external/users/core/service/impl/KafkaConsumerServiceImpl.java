package es.gobcan.istac.edatos.external.users.core.service.impl;

import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.service.KafkaConsumerService;
import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

	public final MetadataConfigurationService metadataConfigurationService;

	public KafkaConsumerServiceImpl(MetadataConfigurationService metadataConfigurationService) {
		this.metadataConfigurationService = metadataConfigurationService;
	}

	@KafkaListener(topics = "#{kafkaProperties.getDatasetPublicationTopic()}")
	public void processDatasetPublicationEvent(GenericRecord record) {
		// TODO EDATOS-3335 - Procesar mensaje
	}
}
