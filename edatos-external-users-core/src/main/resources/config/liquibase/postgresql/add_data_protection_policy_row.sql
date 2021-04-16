--liquibase formatted sql
--changeset arte:1618216857047-3
INSERT INTO public.tb_data_protection_policy (id, created_by, created_date, value) VALUES(0, 'admin', now(), '{"texts": []}');