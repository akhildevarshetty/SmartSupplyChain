package org.thingsboard.server.dao.sql.entityview;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.thingsboard.server.dao.model.sql.EntityViewEntity;
import org.thingsboard.server.dao.util.SqlDao;

import java.util.List;

/**
 * Created by Victor Basanets on 8/31/2017.
 */
@SqlDao
public interface EntityViewRepository extends CrudRepository<EntityViewEntity, String> {

    @Query("SELECT e FROM EntityViewEntity e WHERE e.tenantId = :tenantId " +
            "AND LOWER(e.searchText) LIKE LOWER(CONCAT(:textSearch, '%')) " +
            "AND e.id > :idOffset ORDER BY e.id")
    List<EntityViewEntity> findByTenantId(@Param("tenantId") String tenantId,
                                          @Param("textSearch") String textSearch,
                                          @Param("idOffset") String idOffset,
                                          Pageable pageable);

    @Query("SELECT e FROM EntityViewEntity e WHERE e.tenantId = :tenantId " +
            "AND e.type = :type " +
            "AND LOWER(e.searchText) LIKE LOWER(CONCAT(:textSearch, '%')) " +
            "AND e.id > :idOffset ORDER BY e.id")
    List<EntityViewEntity> findByTenantIdAndType(@Param("tenantId") String tenantId,
                                                 @Param("type") String type,
                                                 @Param("textSearch") String textSearch,
                                                 @Param("idOffset") String idOffset,
                                                 Pageable pageable);

    @Query("SELECT e FROM EntityViewEntity e WHERE e.tenantId = :tenantId " +
            "AND e.customerId = :customerId " +
            "AND LOWER(e.searchText) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "AND e.id > :idOffset ORDER BY e.id")
    List<EntityViewEntity> findByTenantIdAndCustomerId(@Param("tenantId") String tenantId,
                                                       @Param("customerId") String customerId,
                                                       @Param("searchText") String searchText,
                                                       @Param("idOffset") String idOffset,
                                                       Pageable pageable);

    @Query("SELECT e FROM EntityViewEntity e WHERE e.tenantId = :tenantId " +
            "AND e.customerId = :customerId " +
            "AND e.type = :type " +
            "AND LOWER(e.searchText) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "AND e.id > :idOffset ORDER BY e.id")
    List<EntityViewEntity> findByTenantIdAndCustomerIdAndType(@Param("tenantId") String tenantId,
                                                              @Param("customerId") String customerId,
                                                              @Param("type") String type,
                                                              @Param("searchText") String searchText,
                                                              @Param("idOffset") String idOffset,
                                                              Pageable pageable);

    EntityViewEntity findByTenantIdAndName(String tenantId, String name);

    List<EntityViewEntity> findAllByTenantIdAndEntityId(String tenantId, String entityId);

    @Query("SELECT DISTINCT ev.type FROM EntityViewEntity ev WHERE ev.tenantId = :tenantId")
    List<String> findTenantEntityViewTypes(@Param("tenantId") String tenantId);
}
