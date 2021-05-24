package es.gobcan.istac.edatos.external.users.core.job;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.external.users.core.domain.ExternalItemEntity;
import es.gobcan.istac.edatos.external.users.core.repository.ExternalItemRepository;

/**
 * Our categories make references to external categories though a many-to-many relationship.
 * When a category stops referencing an external category, and this external category does not have
 * any relation to any other of our own categories, then it can be deleted so no unused items are
 * left in the db.
 */
@Component
public class DeleteUnreferencedExternalItems {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteUnreferencedExternalItems.class);
    private final ExternalItemRepository externalItemRepository;

    public DeleteUnreferencedExternalItems(ExternalItemRepository externalItemRepository) {
        this.externalItemRepository = externalItemRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * *") // Every day at 03:00
    public void execute() {
        LOG.info("Starting to clean unreferenced external items...");
        long unreferenced = 0;
        for (ExternalItemEntity externalItem : externalItemRepository.findAll()) {
            if (externalItem.getCategories().isEmpty()) {
                externalItemRepository.delete(externalItem);
                unreferenced++;
            }
        }
        LOG.info("Deleted {} unreferenced external items", unreferenced);
    }
}
