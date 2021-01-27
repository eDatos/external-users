#!/bin/bash

HOME_PATH=edatos-external-users
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
scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" -r edatos-external-users-external-web/target/edatos-external-users-external*.war deploy@estadisticas.arte.internal:$TRANSFER_PATH/edatos-external-users-external.war
ssh -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" deploy@estadisticas.arte.internal <<EOF

    chmod a+x $SCRIPTS_PATH/utilities.sh;
    . $SCRIPTS_PATH/utilities.sh
    
    ###
    # EDATOS EXTERNAL USERS EXTERNAL
    ###
    
    if [ $RESTART -eq 1 ]; then
        sudo service edatos-external01 stop
        checkPROC "edatos-external"
    fi
    
    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/edatos-external-users-external
    sudo mv $TRANSFER_PATH/edatos-external-users-external.war $DEPLOY_TARGET_PATH/edatos-external-users-external.war
    sudo unzip $DEPLOY_TARGET_PATH/edatos-external-users-external.war -d $DEPLOY_TARGET_PATH/edatos-external-users-external
    sudo rm -rf $DEPLOY_TARGET_PATH/edatos-external-users-external.war

    # Restore Configuration
    sudo mv $ENV_CONF/logback.xml $DEPLOY_TARGET_PATH/edatos-external-users-external/$LOGBACK_RELATIVE_PATH_FILE
    sudo rm -f $DEPLOY_TARGET_PATH/edatos-external-users-external/WEB-INF/classes/config/application-env.yml
    sudo mv $ENV_CONF/data-location.properties $DEPLOY_TARGET_PATH/edatos-external-users-external/$DATA_RELATIVE_PATH_FILE
    
    if [ $RESTART -eq 1 ]; then
        sudo chown -R edatos-external:edatos-external /servers/edatos-external     
        sudo service edatos-external01 start
    fi
    
    sudo rm -rf $SCRIPTS_PATH/*

    echo "Finished deploy"

EOF