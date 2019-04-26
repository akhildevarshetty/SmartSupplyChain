package org.thingsboard.server.dao.sql.rule;

import org.springframework.data.repository.CrudRepository;
import org.thingsboard.server.dao.model.sql.RuleNodeEntity;
import org.thingsboard.server.dao.util.SqlDao;

@SqlDao
public interface RuleNodeRepository extends CrudRepository<RuleNodeEntity, String> {

}
