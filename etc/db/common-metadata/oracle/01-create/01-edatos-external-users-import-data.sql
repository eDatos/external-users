-- --------------
-- DB Connection
-- --------------

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1, 'metamac.edatos_external_users.db.url',
        'jdbc:postgresql://FILL_ME_WITH_HOST:FILL_ME_WITH_PORT/FILL_ME_WITH_DATABASE');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1, 'metamac.edatos_external_users.db.driver_name',
        'org.postgresql.Driver');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1, 'metamac.edatos_external_users.db.username',
        'FILL_ME_WITH_USER');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1, 'metamac.edatos_external_users.db.password',
        'FILL_ME_WITH_ENCRYPTED_PASSWORD');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE, EXTERNALLY_PUBLISHED)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1, 'metamac.edatos_external_users.rest.external', 'FILL_ME',
        1);
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE, EXTERNALLY_PUBLISHED)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1, 'metamac.edatos_external_users.web.external.url',
        'FILL_ME', 1);
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE, EXTERNALLY_PUBLISHED)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1, 'metamac.portal.rest.external.authentication.captcha.url',
        'FILL_ME', 1);
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1,
        'metamac.portal.rest.external.authentication.captcha.provider', 'FILL_ME');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1,
        'metamac.portal.rest.external.authentication.captcha.enable', 'FILL_ME');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1,
        'metamac.portal.rest.external.authentication.captcha.private_key', 'FILL_ME_WITH_PRIVATE_KEY');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1,
        'metamac.portal.rest.external.authentication.captcha.public_key', 'FILL_ME_WITH_PUBLIC_KEY');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1, 'metamac.edatos_external_users.rest.external.base',
        'FILL_ME');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1,
        'metamac.edatos_external_users.rest.internal.job.send_notice.cron_expression', '0 0 6 * * ? *');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1,
        'metamac.edatos_external_users.web.internal.url', 'FILL_ME');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

insert into TB_DATA_CONFIGURATIONS (ID, VERSION, SYSTEM_PROPERTY, CONF_KEY, CONF_VALUE)
values (GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'), 1, 1,
        'metamac.edatos_external_users.web.visualizer_path', 'FILL_ME');
UPDATE TB_SEQUENCES
SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1
WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

commit;

-- Ejemplos para el entorno de tests
-----------------------------------------
-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(1011,1,1,'metamac.edatos_external_users.db.url','jdbc:h2:mem:edatos_external_users;DB_CLOSE_DELAY=-1');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(1012,1,1,'metamac.edatos_external_users.db.driver_name','org.h2.Driver');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(1013,1,1,'metamac.edatos_external_users.db.username',null);
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(1014,1,1,'metamac.edatos_external_users.db.password',null);
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE, EXTERNALLY_PUBLISHED) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.edatos_external_users.rest.external','https://estadisticas.arte-consultores.com/external-users-external', 1);
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE, EXTERNALLY_PUBLISHED) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.edatos_external_users.web.external.url','https://estadisticas.arte-consultores.com/external-users-external', 1);
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE, EXTERNALLY_PUBLISHED) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.authentication.captcha.url','https://estadisticas.arte-consultores.com/external-users-external/api/captcha', 1);
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.authentication.captcha.provider','simple');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.authentication.captcha.enable','true');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.authentication.captcha.private_key','6LfaVO0SAAAAALo19MfJOozDVpnaxMD4e3qj95Xd');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.authentication.captcha.public_key','6LfaVO0SAAAAAI1cFrnsnO8cNmR9iF_ZCEHuDHnd');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.edatos_external_users.web.internal.url','https://estadisticas.arte-consultores.com/external-users-internal');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- commit;

-- Ejemplos para el entorno de demo
-----------------------------------------
-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.edatos_external_users.db.url','jdbc:postgresql://localhost:5432/external_users-bd');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';
--
-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.edatos_external_users.db.driver_name','org.postgresql.Driver');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';
--
-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.edatos_external_users.db.username','external_users-bd');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';
--
-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.edatos_external_users.db.password','Rw7c+q8lrrQiE/8R34KkecdOD/j/1PV5dohCnwlmQbE=');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE, EXTERNALLY_PUBLISHED) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.edatos_external_users.rest.external','https://estadisticas.arte-consultores.com/external-users-external', 1);
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE, EXTERNALLY_PUBLISHED) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.edatos_external_users.web.external.url','https://estadisticas.arte-consultores.com/external-users-external', 1);
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE, EXTERNALLY_PUBLISHED) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.authentication.captcha.url','https://estadisticas.arte-consultores.com/external-users-external/api/captcha', 1);
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.authentication.captcha.provider','simple');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.authentication.captcha.enable','true');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.authentication.captcha.private_key','6LfaVO0SAAAAALo19MfJOozDVpnaxMD4e3qj95Xd');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.portal.rest.external.authentication.captcha.public_key','6LfaVO0SAAAAAI1cFrnsnO8cNmR9iF_ZCEHuDHnd');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.edatos_external_users.web.internal.url','https://estadisticas.arte-consultores.com/external-users-internal');
-- UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';

-- commit;
