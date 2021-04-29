------------------------------------------------------------------
---------------------- EXTERNAL CATEGORIES -----------------------
------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (1, 1, '1', '{
    "texts": [
        {
            "label": "Estadísticas demográficas y sociales",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (1, null);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (2, 1, '1.1', '{
    "texts": [
        {
            "label": "Población y migración",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.1', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (2, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (3, 1, '1.2', '{
    "texts": [
        {
            "label": "Trabajo",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.2', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (3, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (4, 1, '1.3', '{
    "texts": [
        {
            "label": "Educación",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.3', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (4, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (5, 1, '1.4', '{
    "texts": [
        {
            "label": "Salud",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.4', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (5, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (6, 1, '1.5', '{
    "texts": [
        {
            "label": "Ingresos y consumo",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.5', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (6, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (7, 1, '1.6', '{
    "texts": [
        {
            "label": "Protección social",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.6', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (7, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (8, 1, '1.7', '{
    "texts": [
        {
            "label": "Asentamientos y vivienda",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.7', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (8, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (9, 1, '1.8', '{
    "texts": [
        {
            "label": "Justicia y crímenes",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.8', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (9, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (10, 1, '1.9', '{
    "texts": [
        {
            "label": "Cultura",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.9', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (10, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (11, 1, '1.10', '{
    "texts": [
        {
            "label": "Política y otras actividades de la comunidad",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.10', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (11, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (12, 1, '1.11', '{
    "texts": [
        {
            "label": "Ocio",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.11', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (12, 1);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (13, 1, '2', '{
    "texts": [
        {
            "label": "Estadísticas económicas",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (13, null);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (14, 1, '2.1', '{
    "texts": [
        {
            "label": "Estadísticas macroeconómicas",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.1', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (14, 13);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (15, 1, '2.2', '{
    "texts": [
        {
            "label": "Cuentas económicas",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.2', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (15, 13);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (16, 1, '2.3', '{
    "texts": [
        {
            "label": "Estadísticas empresariales",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.3', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (16, 13);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (17, 1, '2.4', '{
    "texts": [
        {
            "label": "Estadísticas sectoriales",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.4', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (17, 13);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (18, 1, '2.4.1', '{
    "texts": [
        {
            "label": "Agricultura, silvicultura y pesca",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.4.1', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (18, 17);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (19, 1, '2.4.2', '{
    "texts": [
        {
            "label": "Energía",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.4.2', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (19, 17);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (20, 1, '2.4.3', '{
    "texts": [
        {
            "label": "Minería, manufactura y construcción",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.4.3', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (20, 17);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (21, 1, '2.4.4', '{
    "texts": [
        {
            "label": "Transporte",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.4.4', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (21, 17);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (22, 1, '2.4.5', '{
    "texts": [
        {
            "label": "Turismo",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.4.5', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (22, 17);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (23, 1, '2.4.6', '{
    "texts": [
        {
            "label": "Banca, seguros y estadísticas financieras",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.4.6', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (23, 17);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (24, 1, '2.5', '{
    "texts": [
        {
            "label": "Finanzas públicas, fiscales y de estadísticas del sector público",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.5', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (24, 13);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (25, 1, '2.6', '{
    "texts": [
        {
            "label": "Comercio internacional y balanza de pagos",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.6', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (25, 13);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (26, 1, '2.7', '{
    "texts": [
        {
            "label": "Precios",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.7', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (26, 13);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (27, 1, '2.8', '{
    "texts": [
        {
            "label": "Coste laboral",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.8', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (27, 13);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (28, 1, '2.9', '{
    "texts": [
        {
            "label": "Ciencia, tecnología e innovación",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.9', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (28, 13);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (29, 1, '3', '{
    "texts": [
        {
            "label": "Medio Ambiente y estadísticas multi-dominio",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (29, null);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (30, 1, '3.1', '{
    "texts": [
        {
            "label": "Medio Ambiente",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.1', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (30, 29);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (31, 1, '3.2', '{
    "texts": [
        {
            "label": "Estadísticas regionales y en áreas pequeñas",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.2', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (31, 29);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (32, 1, '3.3', '{
    "texts": [
        {
            "label": "Estadísticas e indicadores multi-dominio",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.3', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (32, 29);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (33, 1, '3.3.1', '{
    "texts": [
        {
            "label": "Condiciones de vida, pobreza y cuestiones sociales transversales",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.3.1', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (33, 32);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (34, 1, '3.3.2', '{
    "texts": [
        {
            "label": "Género y grupos especiales de población",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.3.2', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (34, 32);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (35, 1, '3.3.3', '{
    "texts": [
        {
            "label": "Sociedad de la información",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.3.3', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (35, 32);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (36, 1, '3.3.4', '{
    "texts": [
        {
            "label": "Globalización",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.3.4', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (36, 32);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (37, 1, '3.3.5', '{
    "texts": [
        {
            "label": "Indicadores relacionados con los Objetivos de Desarrollo del Milenio",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.3.5', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (37, 32);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (38, 1, '3.3.6', '{
    "texts": [
        {
            "label": "Desarrollo sostenible",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.3.6', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (38, 32);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (39, 1, '3.3.7', '{
    "texts": [
        {
            "label": "Espíritu empresarial",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.3.7', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (39, 32);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (40, 1, '3.4', '{
    "texts": [
        {
            "label": "Anuarios y recopilaciones similares",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).3.4', 'structuralResources#category');

INSERT INTO tb_external_categories(external_item_fk, parent_fk)
VALUES (40, 29);

------------------------------------------------------------------
----------------------- EXTERNAL OPERATIONS ----------------------
------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (41, 1, 'op1', '{
    "texts": [
        {
            "label": "Nacimientos",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op1', 'statisticalOperations#operation');

INSERT INTO tb_external_operations (external_category_urn, external_item_fk)
VALUES ('urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.1', 41);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (42, 1, 'op2', '{
    "texts": [
        {
            "label": "Fallecimientos",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op2', 'statisticalOperations#operation');

INSERT INTO tb_external_operations (external_category_urn, external_item_fk)
VALUES ('urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).1.1', 42);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (43, 1, 'op3', '{
    "texts": [
        {
            "label": "Producción de energía",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op3', 'statisticalOperations#operation');

INSERT INTO tb_external_operations (external_category_urn, external_item_fk)
VALUES ('urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.4.2', 43);

------------------------------------------------------------------

INSERT INTO tb_external_items (id, opt_lock, code, name, urn, type)
VALUES (44, 1, 'op4', '{
    "texts": [
        {
            "label": "Consumo de energía",
            "locale": "es"
        }
    ]
}', 'urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).op4', 'statisticalOperations#operation');

INSERT INTO tb_external_operations (external_category_urn, external_item_fk)
VALUES ('urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ISTAC:SDMXStatSubMatDomainsWD1(01.000).2.4.2', 44);

------------------------------------------------------------------
--------------------------- CATEGORIES ---------------------------
------------------------------------------------------------------

INSERT INTO tb_categories (id, created_by, created_date, opt_lock, name, parent_fk)
VALUES (1, 'system', '2021-04-16 10:49:31.000000', 1, '{
    "texts": [
        {
            "label": "Categoría padre (tiene una categoría y una operación externa)",
            "locale": "es"
        }
    ]
}', null);

INSERT INTO tb_categories_external_items(category_fk, external_item_fk)
VALUES (1, 20), (1, 44);

INSERT INTO tb_categories (id, created_by, created_date, opt_lock, name, parent_fk)
VALUES (2, 'system', '2021-04-16 10:49:31.000000', 1, '{
    "texts": [
        {
            "label": "Categoría hija (tiene dos categorías)",
            "locale": "es"
        }
    ]
}', 1);

INSERT INTO tb_categories_external_items(category_fk, external_item_fk)
VALUES (2, 1), (2, 13);
