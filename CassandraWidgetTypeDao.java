package org.thingsboard.server.dao.widget;

import com.datastax.driver.core.querybuilder.Select.Where;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.widget.WidgetType;
import org.thingsboard.server.dao.DaoUtil;
import org.thingsboard.server.dao.model.nosql.WidgetTypeEntity;
import org.thingsboard.server.dao.nosql.CassandraAbstractModelDao;
import org.thingsboard.server.dao.util.NoSqlDao;

import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.thingsboard.server.dao.model.ModelConstants.WIDGET_TYPE_ALIAS_PROPERTY;
import static org.thingsboard.server.dao.model.ModelConstants.WIDGET_TYPE_BUNDLE_ALIAS_PROPERTY;
import static org.thingsboard.server.dao.model.ModelConstants.WIDGET_TYPE_BY_TENANT_AND_ALIASES_COLUMN_FAMILY_NAME;
import static org.thingsboard.server.dao.model.ModelConstants.WIDGET_TYPE_COLUMN_FAMILY_NAME;
import static org.thingsboard.server.dao.model.ModelConstants.WIDGET_TYPE_TENANT_ID_PROPERTY;

@Component
@Slf4j
@NoSqlDao
public class CassandraWidgetTypeDao extends CassandraAbstractModelDao<WidgetTypeEntity, WidgetType> implements WidgetTypeDao {

    @Override
    protected Class<WidgetTypeEntity> getColumnFamilyClass() {
        return WidgetTypeEntity.class;
    }

    @Override
    protected String getColumnFamilyName() {
        return WIDGET_TYPE_COLUMN_FAMILY_NAME;
    }

    @Override
    public List<WidgetType> findWidgetTypesByTenantIdAndBundleAlias(UUID tenantId, String bundleAlias) {
        log.debug("Try to find widget types by tenantId [{}] and bundleAlias [{}]", tenantId, bundleAlias);
        Where query = select().from(WIDGET_TYPE_BY_TENANT_AND_ALIASES_COLUMN_FAMILY_NAME)
                .where()
                .and(eq(WIDGET_TYPE_TENANT_ID_PROPERTY, tenantId))
                .and(eq(WIDGET_TYPE_BUNDLE_ALIAS_PROPERTY, bundleAlias));
        List<WidgetTypeEntity> widgetTypesEntities = findListByStatement(new TenantId(tenantId), query);
        log.trace("Found widget types [{}] by tenantId [{}] and bundleAlias [{}]", widgetTypesEntities, tenantId, bundleAlias);
        return DaoUtil.convertDataList(widgetTypesEntities);
    }

    @Override
    public WidgetType findByTenantIdBundleAliasAndAlias(UUID tenantId, String bundleAlias, String alias) {
        log.debug("Try to find widget type by tenantId [{}], bundleAlias [{}] and alias [{}]", tenantId, bundleAlias, alias);
        Where query = select().from(WIDGET_TYPE_BY_TENANT_AND_ALIASES_COLUMN_FAMILY_NAME)
                .where()
                .and(eq(WIDGET_TYPE_TENANT_ID_PROPERTY, tenantId))
                .and(eq(WIDGET_TYPE_BUNDLE_ALIAS_PROPERTY, bundleAlias))
                .and(eq(WIDGET_TYPE_ALIAS_PROPERTY, alias));
        log.trace("Execute query {}", query);
        WidgetTypeEntity widgetTypeEntity = findOneByStatement(new TenantId(tenantId), query);
        log.trace("Found widget type [{}] by tenantId [{}], bundleAlias [{}] and alias [{}]",
                widgetTypeEntity, tenantId, bundleAlias, alias);
        return DaoUtil.getData(widgetTypeEntity);
    }

}
