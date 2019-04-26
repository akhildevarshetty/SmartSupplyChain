package org.thingsboard.server.dao.widget;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.Tenant;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.id.WidgetTypeId;
import org.thingsboard.server.common.data.widget.WidgetType;
import org.thingsboard.server.common.data.widget.WidgetsBundle;
import org.thingsboard.server.dao.exception.DataValidationException;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.service.DataValidator;
import org.thingsboard.server.dao.service.Validator;
import org.thingsboard.server.dao.tenant.TenantDao;

import java.util.List;

@Service
@Slf4j
public class WidgetTypeServiceImpl implements WidgetTypeService {

    public static final String INCORRECT_TENANT_ID = "Incorrect tenantId ";
    public static final String INCORRECT_BUNDLE_ALIAS = "Incorrect bundleAlias ";
    @Autowired
    private WidgetTypeDao widgetTypeDao;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private WidgetsBundleDao widgetsBundleService;

    @Override
    public WidgetType findWidgetTypeById(TenantId tenantId, WidgetTypeId widgetTypeId) {
        log.trace("Executing findWidgetTypeById [{}]", widgetTypeId);
        Validator.validateId(widgetTypeId, "Incorrect widgetTypeId " + widgetTypeId);
        return widgetTypeDao.findById(tenantId, widgetTypeId.getId());
    }

    @Override
    public WidgetType saveWidgetType(WidgetType widgetType) {
        log.trace("Executing saveWidgetType [{}]", widgetType);
        widgetTypeValidator.validate(widgetType, WidgetType::getTenantId);
        return widgetTypeDao.save(widgetType.getTenantId(), widgetType);
    }

    @Override
    public void deleteWidgetType(TenantId tenantId, WidgetTypeId widgetTypeId) {
        log.trace("Executing deleteWidgetType [{}]", widgetTypeId);
        Validator.validateId(widgetTypeId, "Incorrect widgetTypeId " + widgetTypeId);
        widgetTypeDao.removeById(tenantId, widgetTypeId.getId());
    }

    @Override
    public List<WidgetType> findWidgetTypesByTenantIdAndBundleAlias(TenantId tenantId, String bundleAlias) {
        log.trace("Executing findWidgetTypesByTenantIdAndBundleAlias, tenantId [{}], bundleAlias [{}]", tenantId, bundleAlias);
        Validator.validateId(tenantId, INCORRECT_TENANT_ID + tenantId);
        Validator.validateString(bundleAlias, INCORRECT_BUNDLE_ALIAS + bundleAlias);
        return widgetTypeDao.findWidgetTypesByTenantIdAndBundleAlias(tenantId.getId(), bundleAlias);
    }

    @Override
    public WidgetType findWidgetTypeByTenantIdBundleAliasAndAlias(TenantId tenantId, String bundleAlias, String alias) {
        log.trace("Executing findWidgetTypeByTenantIdBundleAliasAndAlias, tenantId [{}], bundleAlias [{}], alias [{}]", tenantId, bundleAlias, alias);
        Validator.validateId(tenantId, INCORRECT_TENANT_ID + tenantId);
        Validator.validateString(bundleAlias, INCORRECT_BUNDLE_ALIAS + bundleAlias);
        Validator.validateString(alias, "Incorrect alias " + alias);
        return widgetTypeDao.findByTenantIdBundleAliasAndAlias(tenantId.getId(), bundleAlias, alias);
    }

    @Override
    public void deleteWidgetTypesByTenantIdAndBundleAlias(TenantId tenantId, String bundleAlias) {
        log.trace("Executing deleteWidgetTypesByTenantIdAndBundleAlias, tenantId [{}], bundleAlias [{}]", tenantId, bundleAlias);
        Validator.validateId(tenantId, INCORRECT_TENANT_ID + tenantId);
        Validator.validateString(bundleAlias, INCORRECT_BUNDLE_ALIAS + bundleAlias);
        List<WidgetType> widgetTypes = widgetTypeDao.findWidgetTypesByTenantIdAndBundleAlias(tenantId.getId(), bundleAlias);
        for (WidgetType widgetType : widgetTypes) {
            deleteWidgetType(tenantId, new WidgetTypeId(widgetType.getUuidId()));
        }
    }

    private DataValidator<WidgetType> widgetTypeValidator =
            new DataValidator<WidgetType>() {
                @Override
                protected void validateDataImpl(TenantId tenantId, WidgetType widgetType) {
                    if (StringUtils.isEmpty(widgetType.getName())) {
                        throw new DataValidationException("Widgets type name should be specified!");
                    }
                    if (StringUtils.isEmpty(widgetType.getBundleAlias())) {
                        throw new DataValidationException("Widgets type bundle alias should be specified!");
                    }
                    if (widgetType.getDescriptor() == null || widgetType.getDescriptor().size() == 0) {
                        throw new DataValidationException("Widgets type descriptor can't be empty!");
                    }
                    if (widgetType.getTenantId() == null) {
                        widgetType.setTenantId(new TenantId(ModelConstants.NULL_UUID));
                    }
                    if (!widgetType.getTenantId().getId().equals(ModelConstants.NULL_UUID)) {
                        Tenant tenant = tenantDao.findById(tenantId, widgetType.getTenantId().getId());
                        if (tenant == null) {
                            throw new DataValidationException("Widget type is referencing to non-existent tenant!");
                        }
                    }
                }

                @Override
                protected void validateCreate(TenantId tenantId, WidgetType widgetType) {
                    WidgetsBundle widgetsBundle = widgetsBundleService.findWidgetsBundleByTenantIdAndAlias(widgetType.getTenantId().getId(), widgetType.getBundleAlias());
                    if (widgetsBundle == null) {
                        throw new DataValidationException("Widget type is referencing to non-existent widgets bundle!");
                    }
                    String alias = widgetType.getAlias();
                    if (alias == null || alias.trim().isEmpty()) {
                        alias = widgetType.getName().toLowerCase().replaceAll("\\W+", "_");
                    }
                    String originalAlias = alias;
                    int c = 1;
                    WidgetType withSameAlias;
                    do {
                        withSameAlias = widgetTypeDao.findByTenantIdBundleAliasAndAlias(widgetType.getTenantId().getId(), widgetType.getBundleAlias(), alias);
                        if (withSameAlias != null) {
                            alias = originalAlias + (++c);
                        }
                    } while (withSameAlias != null);
                    widgetType.setAlias(alias);
                }

                @Override
                protected void validateUpdate(TenantId tenantId, WidgetType widgetType) {
                    WidgetType storedWidgetType = widgetTypeDao.findById(tenantId, widgetType.getId().getId());
                    if (!storedWidgetType.getTenantId().getId().equals(widgetType.getTenantId().getId())) {
                        throw new DataValidationException("Can't move existing widget type to different tenant!");
                    }
                    if (!storedWidgetType.getBundleAlias().equals(widgetType.getBundleAlias())) {
                        throw new DataValidationException("Update of widget type bundle alias is prohibited!");
                    }
                    if (!storedWidgetType.getAlias().equals(widgetType.getAlias())) {
                        throw new DataValidationException("Update of widget type alias is prohibited!");
                    }
                }
            };
}
