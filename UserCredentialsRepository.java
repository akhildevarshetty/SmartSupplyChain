package org.thingsboard.server.dao.sql.user;

import org.springframework.data.repository.CrudRepository;
import org.thingsboard.server.dao.model.sql.UserCredentialsEntity;
import org.thingsboard.server.dao.util.SqlDao;

/**
 * Created by Valerii Sosliuk on 4/22/2017.
 */
@SqlDao
public interface UserCredentialsRepository extends CrudRepository<UserCredentialsEntity, String> {

    UserCredentialsEntity findByUserId(String userId);

    UserCredentialsEntity findByActivateToken(String activateToken);

    UserCredentialsEntity findByResetToken(String resetToken);
}
