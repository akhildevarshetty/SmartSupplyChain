package org.thingsboard.server.dao.sql.rule;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.thingsboard.server.dao.model.sql.RuleChainEntity;
import org.thingsboard.server.dao.util.SqlDao;

import java.util.List;

@SqlDao
public interface RuleChainRepository extends CrudRepository<RuleChainEntity, String> {

    @Query("SELECT rc FROM RuleChainEntity rc WHERE rc.tenantId = :tenantId " +
            "AND LOWER(rc.searchText) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "AND rc.id > :idOffset ORDER BY rc.id")
    List<RuleChainEntity> findByTenantId(@Param("tenantId") String tenantId,
                                         @Param("searchText") String searchText,
                                         @Param("idOffset") String idOffset,
                                         Pageable pageable);

}
