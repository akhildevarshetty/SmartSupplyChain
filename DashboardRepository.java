package org.thingsboard.server.dao.sql.dashboard;

import org.springframework.data.repository.CrudRepository;
import org.thingsboard.server.dao.model.sql.DashboardEntity;
import org.thingsboard.server.dao.util.SqlDao;

/**
 * Created by Valerii Sosliuk on 5/6/2017.
 */
@SqlDao
public interface DashboardRepository extends CrudRepository<DashboardEntity, String> {
}
