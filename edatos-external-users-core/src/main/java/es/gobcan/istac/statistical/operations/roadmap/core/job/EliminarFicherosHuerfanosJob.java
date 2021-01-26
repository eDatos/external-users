package es.gobcan.istac.statistical.operations.roadmap.core.job;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.gobcan.istac.statistical.operations.roadmap.core.config.Constants;
import es.gobcan.istac.statistical.operations.roadmap.core.service.FicheroService;

@Component
public class EliminarFicherosHuerfanosJob {

    public static final Logger LOG = LoggerFactory.getLogger(EliminarFicherosHuerfanosJob.class);

    @Autowired
    FicheroService ficheroService;

    @Scheduled(cron = Constants.REMOVE_ORPHAN_FILES_CRON)
    @Transactional
    public void execute() {
        LOG.info("Inicialización job de eliminación de ficheros huérfanos.");
        ficheroService.deleteHuerfanos();
    }
}
