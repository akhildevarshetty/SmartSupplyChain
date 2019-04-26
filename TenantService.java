package org.thingsboard.server.dao.tenant;

import com.google.common.util.concurrent.ListenableFuture;
import org.thingsboard.server.common.data.Tenant;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.page.TextPageData;
import org.thingsboard.server.common.data.page.TextPageLink;

public interface TenantService {

    Tenant findTenantById(TenantId tenantId);

    ListenableFuture<Tenant> findTenantByIdAsync(TenantId callerId, TenantId tenantId);
    
    Tenant saveTenant(Tenant tenant);
    
    void deleteTenant(TenantId tenantId);
    
    TextPageData<Tenant> findTenants(TextPageLink pageLink);
    
    void deleteTenants();
}
