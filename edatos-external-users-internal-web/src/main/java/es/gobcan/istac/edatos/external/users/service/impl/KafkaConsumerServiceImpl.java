package es.gobcan.istac.edatos.external.users.service.impl;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.siemac.metamac.statistical.operations.core.stream.messages.OperationAvro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.service.ExternalOperationService;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalOperationMapper;
import es.gobcan.istac.edatos.external.users.service.KafkaConsumerService;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

    private final ExternalOperationMapper externalOperationMapper;
    private final ExternalOperationService externalOperationService;

    public KafkaConsumerServiceImpl(ExternalOperationMapper externalOperationMapper, ExternalOperationService externalOperationService) {
        this.externalOperationMapper = externalOperationMapper;
        this.externalOperationService = externalOperationService;
    }

    @KafkaListener(topics = "#{kafkaProperties.getDatasetPublicationTopic()}")
    public void processDatasetPublicationEvent(GenericRecord genericRecord) {
        // TODO EDATOS-3335 - Procesar mensaje
    }

    @KafkaListener(topics = "#{kafkaProperties.getOperationPublicationTopic()}")
    public void processOperationPublicationEvent(ConsumerRecord<String, OperationAvro> consumerRecord) {
        LOGGER.info("Kafka message received. Code: '{}'", consumerRecord.value().getUrn());
        ExternalOperationEntity operation = externalOperationMapper.toEntity(consumerRecord.value());
        if (externalOperationService.findByUrn(operation.getUrn()).isPresent()) {
            externalOperationService.update(operation);
        } else {
            externalOperationService.create(operation);
        }
    }
}
