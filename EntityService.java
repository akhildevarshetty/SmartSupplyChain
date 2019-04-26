package org.thingsboard.server.dao.entity;

import com.google.common.util.concurrent.ListenableFuture;
import org.thingsboard.server.common.data.id.EntityId;
import org.thingsboard.server.common.data.id.TenantId;

public interface EntityService {

    ListenableFuture<String> fetchEntityNameAsync(TenantId tenantId, EntityId entityId);

    void deleteEntityRelations(TenantId tenantId, EntityId entityId);

}
