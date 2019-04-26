package org.thingsboard.server.dao.sql.dashboard;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.thingsboard.server.dao.model.sql.DashboardInfoEntity;
import org.thingsboard.server.dao.util.SqlDao;

import java.util.List;

/**
 * Created by Valerii Sosliuk on 5/6/2017.
 */
@SqlDao
public interface DashboardInfoRepository extends CrudRepository<DashboardInfoEntity, String> {

    @Query("SELECT di FROM DashboardInfoEntity di WHERE di.tenantId = :tenantId " +
            "AND LOWER(di.searchText) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "AND di.id > :idOffset ORDER BY di.id")
    List<DashboardInfoEntity> findByTenantId(@Param("tenantId") String tenantId,
                                             @Param("searchText") String searchText,
                                             @Param("idOffset") String idOffset,
                                             Pageable pageable);

}
