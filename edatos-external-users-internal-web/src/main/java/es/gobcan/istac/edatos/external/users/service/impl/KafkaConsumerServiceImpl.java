package es.gobcan.istac.edatos.external.users.service.impl;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.siemac.metamac.statistical.operations.core.stream.messages.OperationAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalDatasetEntity;
import es.gobcan.istac.edatos.external.users.core.domain.ExternalOperationEntity;
import es.gobcan.istac.edatos.external.users.core.service.ExternalDatasetService;
import es.gobcan.istac.edatos.external.users.core.service.ExternalOperationService;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalDatasetMapper;
import es.gobcan.istac.edatos.external.users.rest.common.mapper.ExternalOperationMapper;
import es.gobcan.istac.edatos.external.users.service.KafkaConsumerService;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

    private final ExternalOperationMapper externalOperationMapper;
    private final ExternalOperationService externalOperationService;
    private final ExternalDatasetMapper externalDatasetMapper;
    private final ExternalDatasetService externalDatasetService;

    public KafkaConsumerServiceImpl(ExternalOperationMapper externalOperationMapper,
        ExternalOperationService externalOperationService,
        ExternalDatasetMapper externalDatasetMapper,
        ExternalDatasetService externalDatasetService) {
        this.externalOperationMapper = externalOperationMapper;
        this.externalOperationService = externalOperationService;
        this.externalDatasetMapper = externalDatasetMapper;
        this.externalDatasetService = externalDatasetService;
    }

    @KafkaListener(topics = "#{kafkaProperties.getDatasetPublicationTopic()}")
    public void processDatasetPublicationEvent(ConsumerRecord<String, DatasetVersionAvro> consumerRecord) {
        String urn = consumerRecord.value()
                                   .getSiemacMetadataStatisticalResource()
                                   .getLifecycleStatisticalResource()
                                   .getVersionableStatisticalResource()
                                   .getNameableStatisticalResource()
                                   .getIdentifiableStatisticalResource()
                                   .getUrn();
        LOGGER.info("Kafka dataset message received. Code: '{}'", urn);
        ExternalDatasetEntity dataset = externalDatasetMapper.toEntity(consumerRecord.value());
        externalDatasetService.update(dataset);
    }

    @KafkaListener(topics = "#{kafkaProperties.getOperationPublicationTopic()}")
    public void processOperationPublicationEvent(ConsumerRecord<String, OperationAvro> consumerRecord) {
        LOGGER.info("Kafka operation message received. Code: '{}'", consumerRecord.value().getUrn());
        ExternalOperationEntity operation = externalOperationMapper.toEntity(consumerRecord.value());
        Optional<ExternalOperationEntity> inDbOperation = externalOperationService.findByUrn(operation.getUrn());
        if (inDbOperation.isPresent()) {
            operation.setId(inDbOperation.get().getId());
            externalOperationService.update(operation);
        } else {
            externalOperationService.create(operation);
        }
    }
}
