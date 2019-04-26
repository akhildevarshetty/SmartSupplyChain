package org.thingsboard.server.dao.widget;

import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.id.WidgetsBundleId;
import org.thingsboard.server.common.data.page.TextPageData;
import org.thingsboard.server.common.data.page.TextPageLink;
import org.thingsboard.server.common.data.widget.WidgetsBundle;

import java.util.List;

public interface WidgetsBundleService {

    WidgetsBundle findWidgetsBundleById(TenantId tenantId, WidgetsBundleId widgetsBundleId);

    WidgetsBundle saveWidgetsBundle(WidgetsBundle widgetsBundle);

    void deleteWidgetsBundle(TenantId tenantId, WidgetsBundleId widgetsBundleId);

    WidgetsBundle findWidgetsBundleByTenantIdAndAlias(TenantId tenantId, String alias);

    TextPageData<WidgetsBundle> findSystemWidgetsBundlesByPageLink(TenantId tenantId, TextPageLink pageLink);

    List<WidgetsBundle> findSystemWidgetsBundles(TenantId tenantId);

    TextPageData<WidgetsBundle> findTenantWidgetsBundlesByTenantId(TenantId tenantId, TextPageLink pageLink);

    TextPageData<WidgetsBundle> findAllTenantWidgetsBundlesByTenantIdAndPageLink(TenantId tenantId, TextPageLink pageLink);

    List<WidgetsBundle> findAllTenantWidgetsBundlesByTenantId(TenantId tenantId);

    void deleteWidgetsBundlesByTenantId(TenantId tenantId);

}
