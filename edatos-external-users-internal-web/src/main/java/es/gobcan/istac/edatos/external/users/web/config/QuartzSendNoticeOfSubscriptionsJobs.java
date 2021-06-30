package es.gobcan.istac.edatos.external.users.web.config;

import es.gobcan.istac.edatos.external.users.core.service.MetadataConfigurationService;
import es.gobcan.istac.edatos.external.users.web.job.SendNoticeOfSubscriptionsJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzSendNoticeOfSubscriptionsJobs {

    private static final String DATASETS_SUBSCRIPTIONS_CRON = "0 0 6 * * ? *";

    private final MetadataConfigurationService metadataService;
    private final ApplicationProperties applicationProperties;

    public QuartzSendNoticeOfSubscriptionsJobs(MetadataConfigurationService metadataService, ApplicationProperties applicationProperties) {
        this.metadataService = metadataService;
        this.applicationProperties = applicationProperties;
    }

    @Bean(name = "sendNoticeOfSubscriptions")
    public JobDetailFactoryBean jobCreateNoticeSubscription() {
        return QuartzConfiguration.createJobDetail(SendNoticeOfSubscriptionsJob.class, QuartzConstants.DATASETS_SUBSCRIPTIONS_JOB, QuartzConstants.DATASETS_SUBSCRIPTIONS_JOB_DESCRIPTION);
    }

    @Bean(name = "sendNoticeOfSubscriptionsTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("sendNoticeOfSubscriptions") JobDetail jobDetail) {
        return QuartzConfiguration.createCronTrigger(jobDetail, DATASETS_SUBSCRIPTIONS_CRON
        /* metadataService.findProperty(applicationProperties.getMetadata().getMetamacEUsuariosCronSendNoticeJob()) */, QuartzConstants.DATASETS_SUBSCRIPTIONS_TRIGGER);
    }
}
