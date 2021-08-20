INSERT INTO tb_filters (id, created_by, created_date, last_modified_by, last_modified_date, opt_lock,
                        last_access_date, name, notes, permalink, external_user_fk, external_operation_urn)
VALUES (1, 'system', '2021-05-06 11:10:17.234000', 'system',
        '2021-05-06 11:23:54.257000', 2, '2021-05-06 11:10:17.219000',
        'Indicador referido al número de visitantes que llegan a las Islas Canarias',
        'Indicador referido al número de visitantes que llegan a las Islas Canarias',
        't25zbzh9nfmcytr19vzt523gd', 1,
        'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op1'),
       (2, 'system', '2021-05-06 11:30:54.247000', 'system',
        '2021-05-06 11:30:54.247000', 0, '2021-05-06 11:30:53.461000', 'Visitantes en las islas',
        'El número de visitantes en las islas',
        '1ofnp6ihvgdv8qx11d8t96092m', 1,
        'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op2'),
       (3, 'system', '2021-05-06 11:10:17.234000', 'system',
        '2021-05-06 11:23:54.257000', 2, '2021-05-06 11:10:17.219000',
        'Índice censal de ocupación según indicadores por islas, provincias y comunidades', 'índice censal',
        'ekfnsqu5ue31woq1vmwhev9xn', 2,
        'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op3'),
       (4, 'system', '2021-05-06 11:30:54.247000', 'system',
        '2021-05-06 11:30:54.247000', 0, '2021-05-06 11:30:53.461000', 'Visitantes en las islas',
        'El número de visitantes en las islas',
        '1ofnp6ihvgdv8qx11d8t96092m', 2,
        'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op2'),
       (5, 'system', '2021-05-06 11:10:17.234000', 'system',
        '2021-05-06 11:23:54.257000', 2, '2021-05-06 11:10:17.219000',
        'Índice censal de ocupación según indicadores por islas, provincias y comunidades', 'índice censal',
        'ekfnsqu5ue31woq1vmwhev9xn', 3,
        'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op3'),
       (6, 'system', '2021-05-06 11:30:54.247000', 'system',
        '2021-05-06 11:30:54.247000', 0, '2021-05-06 11:30:53.461000', 'Visitantes en las islas',
        'El número de visitantes en las islas',
        '1ofnp6ihvgdv8qx11d8t96092m', 3,
        'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op2'),
       (7, 'system', '2021-05-06 11:10:17.234000', 'system',
        '2021-05-06 11:23:54.257000', 2, '2021-05-06 11:10:17.219000',
        'Índice censal de ocupación según indicadores por islas, provincias y comunidades', 'índice censal',
        'ekfnsqu5ue31woq1vmwhev9xn', 4,
        'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op3'),
       (8, 'system', '2021-05-06 11:30:54.247000', 'system',
        '2021-05-06 11:30:54.247000', 0, '2021-05-06 11:30:53.461000', 'Visitantes en las islas',
        'El número de visitantes en las islas',
        '1ofnp6ihvgdv8qx11d8t96092m', 4,
        'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op2');


ALTER SEQUENCE seq_tb_filters RESTART WITH 9;
