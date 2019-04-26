package org.thingsboard.server.dao.sql.settings;

import org.springframework.data.repository.CrudRepository;
import org.thingsboard.server.dao.model.sql.AdminSettingsEntity;

/**
 * Created by Valerii Sosliuk on 5/6/2017.
 */
public interface AdminSettingsRepository extends CrudRepository<AdminSettingsEntity, String> {

    AdminSettingsEntity findByKey(String key);
}
