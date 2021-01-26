#!/bin/bash

HOME_PATH=statistical-operations-roadmap
TRANSFER_PATH=$HOME_PATH/tmp
SCRIPTS_PATH=$HOME_PATH/scripts
ENV_CONF=$HOME_PATH/env
DEPLOY_TARGET_PATH=/servers/edatos-external/tomcats/edatos-external01/webapps
DATA_RELATIVE_PATH_FILE=WEB-INF/classes/config/data-location.properties
LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml

RESTART=1

if [ "$1" == "--no-restart" ]; then
    RESTART=0
fi

scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" -r etc/deploy/config/demo/external/* deploy@estadisticas.arte.internal:$ENV_CONF
scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" -r etc/deploy/utils/utilities.sh deploy@estadisticas.arte.internal:$SCRIPTS_PATH
scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" -r statistical-operations-roadmap-external-web/target/statistical-operations-roadmap-external*.war deploy@estadisticas.arte.internal:$TRANSFER_PATH/statistical-operations-roadmap-external.war
ssh -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" deploy@estadisticas.arte.internal <<EOF

    chmod a+x $SCRIPTS_PATH/utilities.sh;
    . $SCRIPTS_PATH/utilities.sh
    
    ###
    # STATISTICAL OPERATIONS ROADMAP EXTERNAL
    ###
    
    if [ $RESTART -eq 1 ]; then
        sudo service edatos-external01 stop
        checkPROC "edatos-external"
    fi
    
    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/statistical-operations-roadmap-external
    sudo mv $TRANSFER_PATH/statistical-operations-roadmap-external.war $DEPLOY_TARGET_PATH/statistical-operations-roadmap-external.war
    sudo unzip $DEPLOY_TARGET_PATH/statistical-operations-roadmap-external.war -d $DEPLOY_TARGET_PATH/statistical-operations-roadmap-external
    sudo rm -rf $DEPLOY_TARGET_PATH/statistical-operations-roadmap-external.war

    # Restore Configuration
    sudo mv $ENV_CONF/logback.xml $DEPLOY_TARGET_PATH/statistical-operations-roadmap-external/$LOGBACK_RELATIVE_PATH_FILE
    sudo rm -f $DEPLOY_TARGET_PATH/statistical-operations-roadmap-external/WEB-INF/classes/config/application-env.yml
    sudo mv $ENV_CONF/data-location.properties $DEPLOY_TARGET_PATH/statistical-operations-roadmap-external/$DATA_RELATIVE_PATH_FILE
    
    if [ $RESTART -eq 1 ]; then
        sudo chown -R edatos-external:edatos-external /servers/edatos-external     
        sudo service edatos-external01 start
    fi
    
    sudo rm -rf $SCRIPTS_PATH/*

    echo "Finished deploy"

EOF