package es.gobcan.istac.edatos.external.users.core.util;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.arte.libs.grammar.antlr.DefaultQueryExprVisitor;
import com.arte.libs.grammar.antlr.QueryExprCompiler;
import com.arte.libs.grammar.domain.QueryRequest;
import com.arte.libs.grammar.orm.jpa.criteria.AbstractCriteriaProcessor;

import es.gobcan.istac.edatos.external.users.core.service.criteria.FamilyCriteriaProcessor;
import es.gobcan.istac.edatos.external.users.core.service.criteria.InstanceCriteriaProcessor;
import es.gobcan.istac.edatos.external.users.core.service.criteria.NeedCriteriaProcessor;
import es.gobcan.istac.edatos.external.users.core.service.criteria.OperationCriteriaProcessor;
import es.gobcan.istac.edatos.external.users.core.service.criteria.UsuarioCriteriaProcessor;

@Component
public class QueryUtil {

    private static final Logger logger = LoggerFactory.getLogger(QueryUtil.class);
    private static final String INCLUDE_DELETED_HINT = "HINT INCLUDE_DELETED SET 'true'";
    private QueryExprCompiler queryExprCompiler = new QueryExprCompiler();

    public DetachedCriteria queryToUserCriteria(Pageable pageable, String query) {
        return queryToCriteria(pageable, query, new UsuarioCriteriaProcessor());
    }

    public String queryIncludingDeleted(String query) {
        return new StringBuilder(query).append(" ").append(INCLUDE_DELETED_HINT).toString();
    }

    public DetachedCriteria queryToFamilyCriteria(Pageable pageable, String query) {
        return queryToCriteria(pageable, query, new FamilyCriteriaProcessor());
    }

    public DetachedCriteria queryToFamilySortCriteria(Sort sort, String query) {
        return queryToCriteria(sort, query, new FamilyCriteriaProcessor());
    }

    public DetachedCriteria queryToOperationCriteria(Pageable pageable, String query) {
        return queryToCriteria(pageable, query, new OperationCriteriaProcessor());
    }

    public DetachedCriteria queryToOperationSortCriteria(Sort sort, String query) {
        return queryToCriteria(sort, query, new OperationCriteriaProcessor());
    }

    public DetachedCriteria queryToInstanceCriteria(Pageable pageable, String query) {
        return queryToCriteria(pageable, query, new InstanceCriteriaProcessor());
    }

    public DetachedCriteria queryToInstanceSortCriteria(Sort sort, String query) {
        return queryToCriteria(sort, query, new InstanceCriteriaProcessor());
    }

    private String pageableSortToQueryString(Pageable pageable) {
        if (pageable == null) {
            return StringUtils.EMPTY;
        }

        return sortToQueryString(pageable.getSort());
    }

    private String sortToQueryString(Sort sort) {
        if (sort == null) {
            return StringUtils.EMPTY;
        }

        StringBuilder result = new StringBuilder();
        for (Order sortOrder : sort) {
            if (!"ID".equalsIgnoreCase(sortOrder.getProperty())) {
                if (result.length() == 0) {
                    result.append(" ORDER BY ");
                } else {
                    result.append(", ");
                }
                result.append(sortOrder.getProperty().toUpperCase());
                if (sortOrder.isAscending()) {
                    result.append(" ").append("ASC");
                } else {
                    result.append(" ").append("DESC");
                }
            }
        }
        return result.toString();
    }

    private DetachedCriteria queryToCriteria(Pageable pageable, String query, AbstractCriteriaProcessor processor) {
        String sortedQuery = pageableSortToQueryString(pageable);
        return queryToCriteria(sortedQuery, query, processor);
    }

    private DetachedCriteria queryToCriteria(Sort sort, String query, AbstractCriteriaProcessor processor) {
        String sortedQuery = sortToQueryString(sort);
        return queryToCriteria(sortedQuery, query, processor);
    }

    private DetachedCriteria queryToCriteria(String sortedQuery, String query, AbstractCriteriaProcessor processor) {
        if (query == null) {
            query = StringUtils.EMPTY;
        }

        query += sortedQuery;

        QueryRequest queryRequest = null;
        logger.debug("Petición para mapear query: {}", query);
        if (StringUtils.isNotBlank(query)) {
            DefaultQueryExprVisitor visitor = new DefaultQueryExprVisitor();
            queryExprCompiler.parse(query, visitor);
            queryRequest = visitor.getQueryRequest();
        }
        return processor.process(queryRequest);
    }

    public DetachedCriteria queryToNeedCriteria(String query, Pageable pageable) {
        return queryToCriteria(pageable, query, new NeedCriteriaProcessor());
    }

    public DetachedCriteria queryToNeedSortCriteria(String query, Sort sort) {
        return queryToCriteria(sort, query, new NeedCriteriaProcessor());
    }

}
