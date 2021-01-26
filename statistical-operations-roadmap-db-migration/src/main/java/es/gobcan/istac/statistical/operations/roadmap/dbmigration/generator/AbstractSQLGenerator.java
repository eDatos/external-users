package es.gobcan.istac.statistical.operations.roadmap.dbmigration.generator;

import static es.gobcan.istac.statistical.operations.roadmap.dbmigration.Migration.targetDirectory;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractSQLGenerator {

    protected static final Integer OPT_LOCK = 1;

    private static final String NULL = "null";
    private static final String CHARSET = "UTF-8";

    private static int printWriterCount = 0;

    protected PrintWriter createPrintWriter(String tableName) throws FileNotFoundException, UnsupportedEncodingException {
        String filename = StringUtils.leftPad(String.valueOf(++printWriterCount), 2, "0") + "-" + tableName.toLowerCase() + ".sql";
        return new PrintWriter(targetDirectory + filename, CHARSET);
    }

    protected String toInsertionValue(Long value) {
        if (value == null) {
            return NULL;
        }
        return value.toString();
    }

    protected String toInsertionValue(Integer value) {
        if (value == null) {
            return NULL;
        }
        return value.toString();
    }

    protected String toInsertionValue(Timestamp value) {
        if (value == null) {
            return NULL;
        }
        return "'" + value.toString() + "'";
    }

    protected String toInsertionValue(String value) {
        if (value == null) {
            return NULL;
        }
        return quoteString(value);
    }

    protected String toInsertionValue(Boolean value) {
        if (value == null) {
            return NULL;
        }
        return value.toString();
    }

    protected String toProcStatusInsertionValue(String value) {
        if ("PUBLISH_INTERNALLY".equals(value)) {
            return "'INTERNALLY_PUBLISHED'";
        } else if ("PUBLISH_EXTERNALLY".equals(value)) {
            return "'EXTERNALLY_PUBLISHED'";
        } else if ("DRAFT".equals(value)) {
            return "'DRAFT'";
        }
        return NULL;
    }

    private String quoteString(String value) {
        if (value == null) {
            return null;
        }
        return "'" + escapeSpecialCharacters(value) + "'";
    }

    private String escapeSpecialCharacters(String value) {
        return StringUtils.replace(value, "'", "''");
    }
}
