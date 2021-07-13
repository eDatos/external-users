package es.gobcan.istac.edatos.external.users.service.impl;

import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;
import es.gobcan.istac.edatos.external.users.service.KafkaConsumerService;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

    private final MetadataConfigurationService metadataConfigurationService;

    public KafkaConsumerServiceImpl(MetadataConfigurationService metadataConfigurationService) {
        this.metadataConfigurationService = metadataConfigurationService;
    }

    @KafkaListener(topics = "#{kafkaProperties.getDatasetPublicationTopic()}")
    public void processDatasetPublicationEvent(GenericRecord genericRecord) {
        // TODO EDATOS-3335 - Procesar mensaje
    }

    @KafkaListener(topics = "#{kafkaProperties.getOperationPublicationTopic()}")
    public void processOperationPublicationEvent(GenericRecord genericRecord) {
        log.info("{}", genericRecord.get("test"));
    }
}
