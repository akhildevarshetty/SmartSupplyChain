package org.thingsboard.server.dao.device;

import com.datastax.driver.core.querybuilder.Select.Where;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.security.DeviceCredentials;
import org.thingsboard.server.dao.DaoUtil;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.model.nosql.DeviceCredentialsEntity;
import org.thingsboard.server.dao.nosql.CassandraAbstractModelDao;
import org.thingsboard.server.dao.util.NoSqlDao;

import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;

@Component
@Slf4j
@NoSqlDao
public class CassandraDeviceCredentialsDao extends CassandraAbstractModelDao<DeviceCredentialsEntity, DeviceCredentials> implements DeviceCredentialsDao {

    @Override
    protected Class<DeviceCredentialsEntity> getColumnFamilyClass() {
        return DeviceCredentialsEntity.class;
    }

    @Override
    protected String getColumnFamilyName() {
        return ModelConstants.DEVICE_CREDENTIALS_COLUMN_FAMILY_NAME;
    }

    @Override
    public DeviceCredentials findByDeviceId(TenantId tenantId, UUID deviceId) {
        log.debug("Try to find device credentials by deviceId [{}] ", deviceId);
        Where query = select().from(ModelConstants.DEVICE_CREDENTIALS_BY_DEVICE_COLUMN_FAMILY_NAME)
                .where(eq(ModelConstants.DEVICE_CREDENTIALS_DEVICE_ID_PROPERTY, deviceId));
        log.trace("Execute query {}", query);
        DeviceCredentialsEntity deviceCredentialsEntity = findOneByStatement(tenantId, query);
        log.trace("Found device credentials [{}] by deviceId [{}]", deviceCredentialsEntity, deviceId);
        return DaoUtil.getData(deviceCredentialsEntity);
    }
    
    @Override
    public DeviceCredentials findByCredentialsId(TenantId tenantId, String credentialsId) {
        log.debug("Try to find device credentials by credentialsId [{}] ", credentialsId);
        Where query = select().from(ModelConstants.DEVICE_CREDENTIALS_BY_CREDENTIALS_ID_COLUMN_FAMILY_NAME)
                .where(eq(ModelConstants.DEVICE_CREDENTIALS_CREDENTIALS_ID_PROPERTY, credentialsId));
        log.trace("Execute query {}", query);
        DeviceCredentialsEntity deviceCredentialsEntity = findOneByStatement(tenantId, query);
        log.trace("Found device credentials [{}] by credentialsId [{}]", deviceCredentialsEntity, credentialsId);
        return DaoUtil.getData(deviceCredentialsEntity);
    }
}
