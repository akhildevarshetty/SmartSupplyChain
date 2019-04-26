package org.thingsboard.server.dao.sql.widget;

import org.springframework.data.repository.CrudRepository;
import org.thingsboard.server.dao.model.sql.WidgetTypeEntity;
import org.thingsboard.server.dao.util.SqlDao;

import java.util.List;

/**
 * Created by Valerii Sosliuk on 4/29/2017.
 */
@SqlDao
public interface WidgetTypeRepository extends CrudRepository<WidgetTypeEntity, String> {

    List<WidgetTypeEntity> findByTenantIdAndBundleAlias(String tenantId, String bundleAlias);

    WidgetTypeEntity findByTenantIdAndBundleAliasAndAlias(String tenantId, String bundleAlias, String alias);
}
