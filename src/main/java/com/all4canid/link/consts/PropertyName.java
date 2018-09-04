package com.all4canid.link.consts;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Enum class for Registered Property Name
 * 
 * @author spark
 *
 */
public enum PropertyName {
	LINK_RETRY_INTERVAL("link.retry.interval.seconds", false, null),
	LINK_RETRY_LIMIT("link.retry.number.limit", false, null),
	LINK_TEXTURA_REQUEST_TIMEOUT("link.textura.request.timeout", false, null),
	LINK_LOCAL_FILEPATH("link.local.filepath", false, null),
	LINK_LOCAL_DOCPATH("link.local.docpath", false, null),
	LINK_UNZIP_DOC_WITH_SUBFOLDER("link.unzip.doc.with.subfolder", false, null),
	LINK_EXPORT_DOC_FILENAME_RULE("link.export.doc.filename.rule", false, null),
	LINK_IMPORTED_FILTERING_NEW_SUBCONTRACT("link.imported.filtering.new.contract", false, null),
	LINK_IMPORTED_FILTERING_UPDATE_SUBCONTRACT("link.imported.filtering.update.contract", false, null),
	LINK_IMPORTED_FILTERING_CHANGEORDER("link.imported.filtering.changeorder", false, null),
	LINK_IMPORTED_FILTERING_PRIME_CHANGEORDER("link.imported.filtering.prime.changeorder", false, null),
	LINK_IMPORTED_FILTERING_COMPLIANCE("link.imported.filtering.compliance", false, null),
	LINK_IMPORTED_FILTERING_CHANGEORDER_COMPLIANCE("link.imported.filtering.co.compliance", false, null),
	LINK_IMPORTED_FILTERING_USER_STATUS("link.imported.filtering.user.status", false, null),
	LINK_IMPORTED_FILTERING_USER_ROLE("link.imported.filtering.user.role", false, null),
	LINK_IMPORTED_FILTERING_ERP_RECORD("link.imported.filtering.erp.record", false, null),
	LINK_IMPORTED_FILTERING_ERP_PROJECT("link.imported.filtering.erp.project", false, null),
	LINK_AUDIT_FILTERING_YEAR_WINDOW("link.audit.filtering.year.window", false, null),
	LINK_SEND_SUCCESS_CONTRACT_NUMBER_TO_ADAPTER("link.send.success.contract.number.to.adapter", false, null),
	LINK_INSERT_MISSING_PROJECTS_ON_IMPORT("link.insert.missing.projects.on.import", false, null),
	LINK_IMPORT_MAX_RETRY("link.import.max.retry", false, null),
	LINK_SEND_SLDR_RECORDS("link.send.sldr.records", false, null),	
	
	TEXTURA_SERVICE_URL("textura.service.url", false, null),
	TEXTURA_USERNAME("textura.username", false, null),
	TEXTURA_PASSWORD("textura.password", false, null),
	TEXTURA_ERROR_REPORT_ENABLE("textura.error.report.enable", false, null),
	TEXTURA_CONTEXT_EXPORT_PAYMENTS("textura.context.export.payments", false, null),
	TEXTURA_CONTEXT_EXPORT_INVOICES("textura.context.export.invoices", false, null),
	TEXTURA_CONTEXT_EXPORT_DOCUMENTS("textura.context.export.documents", false, null),
	TEXTURA_CONTEXT_EXPORT_COMPLIANCES("textura.context.export.compliances", false, null),
	TEXTURA_CONTEXT_EXPORT_INVOICE_REJECTIONS("textura.context.export.invoice.rejections", false, null),
	TEXTURA_CONTEXT_EXPORT_CHANGEORDERS("textura.context.export.changeorders", false, null),
	TEXTURA_CONTEXT_IMPORT_NEW_SUBCONTRACTS("textura.context.import.new.contract", false, null),
	TEXTURA_CONTEXT_IMPORT_UPDATED_SUBCONTRACTS("textura.context.import.updated.contract", false, null),
	TEXTURA_CONTEXT_IMPORT_COMPLIANCES("textura.context.import.compliances", false, null),
	TEXTURA_CONTEXT_IMPORT_CHANGEORDER_COMPLIANCES("textura.context.import.co.compliances", false, null),
	TEXTURA_CONTEXT_IMPORT_CHANGEORDERS("textura.context.import.changeorders", false, null),
	TEXTURA_CONTEXT_IMPORT_PRIME_CHANGEORDERS("textura.context.import.prime.changeorders", false, null),
	TEXTURA_CONTEXT_IMPORT_USER_ROLES("textura.context.import.user.project.roles", false, null),
	TEXTURA_CONTEXT_IMPORT_USER_STATUSES("textura.context.import.user.statuses", false, null),
	TEXTURA_CONTEXT_IMPORT_SCHEDULE_OF_VALUES("textura.context.import.schedule.of.values", false, null),
	TEXTURA_CONTEXT_IMPORT_OWNER_INVOICES("textura.context.import.owner.invoices", false, null),
	TEXTURA_CONTEXT_IMPORT_ERP_RECORDS("textura.context.import.erp.records", false, null),
	TEXTURA_CONTEXT_IMPORT_ERP_PROJECTS("textura.context.import.erp.projects", false, null),
	TEXTURA_CONTEXT_IMPORT_VENDOR_HOLD("textura.context.import.vendor.hold", false, null),
	TEXTURA_CONTEXT_REPORT_ERRORS("textura.context.report.errors", false, null),
	TEXTURA_CONTEXT_GET_PROJECTS("textura.context.get.projects", false, null),
	TEXTURA_CONTEXT_BULK("textura.context.bulk", false, null),

	CLIENT_EPP_ACCOUNT("client.epp.account", false, null),

	ERP_USERNAME("erp.username", false, null),
	ERP_PASSWORD("erp.password", false, null),
	ERP_SERVICE_LOCALE("erp.service.locale", false, null),
	ERP_SERVICE_URL("erp.service.url", false, null),
	ERP_SERVICE_PARAMS("erp.service.param", false, null),
	ERP_SERVICE_APPLICATION("erp.service.application", false, null),
	ERP_SERVICE_VERSION("erp.service.version", false, null),
	ERP_CLIENT_NAME("erp.client.name", false, null),
	ERP_CLIENT_PARAMS("erp.client.param", false, null),
	ERP_SERVICE_TYPE("erp.service.type", false, null),
	ERP_SEND_SOV_PHASE("erp.send.sov.phase", false, null),
	ERP_CHANGEORDER_COMPLIANCE_CODE("erp.co.compliance.code", false, null),
	ERP_DAYS_WINDOW("erp.days.window", false, null),

	SCHEDULE_CRON_EXPORT_CHANGEORDERS("schedule.cron.export.changeorders", true, ActionType.EXPORT_CHANGEORDERS),
	SCHEDULE_CRON_EXPORT_INVOICES("schedule.cron.export.invoices", true, ActionType.EXPORT_INVOICES),
	SCHEDULE_CRON_EXPORT_PAYMENTS("schedule.cron.export.payments", true, ActionType.EXPORT_PAYMENTS),
	SCHEDULE_CRON_EXPORT_COMPLIANCES("schedule.cron.export.compliances", true, ActionType.EXPORT_COMPLIANCES),
	SCHEDULE_CRON_EXPORT_DOCUMENTS("schedule.cron.export.documents", true, ActionType.EXPORT_DOCUMENTS),
	SCHEDULE_CRON_EXPORT_INVOICE_REJECTIONS("schedule.cron.export.rejections", true, ActionType.EXPORT_REJECTIONS),
	SCHEDULE_CRON_IMPORT_NEW_SUBCONTRACTS("schedule.cron.import.new.contracts", true, ActionType.IMPORT_NEW_CONTRACTS),
	SCHEDULE_CRON_IMPORT_UPDATED_SUBCONTRACTS("schedule.cron.import.update.contracts", true, ActionType.IMPORT_UPDATED_CONTRACTS),
	SCHEDULE_CRON_IMPORT_CHANGEORDERS("schedule.cron.import.changeorders", true, ActionType.IMPORT_CHANGEORDERS),
	SCHEDULE_CRON_IMPORT_PRIME_CHANGEORDERS("schedule.cron.import.prime.changeorders", true, ActionType.IMPORT_PRIME_CHANGEORDERS),
	SCHEDULE_CRON_IMPORT_COMPLIANCES("schedule.cron.import.compliances", true, ActionType.IMPORT_COMPLIANCES),
	SCHEDULE_CRON_IMPORT_CHANGEORDER_COMPLIANCES("schedule.cron.import.co.compliances", true, ActionType.IMPORT_CHANGEORDER_COMPLIANCES),
	SCHEDULE_CRON_IMPORT_USER_PROJECT_ROLES("schedule.cron.import.user.project.roles", true, ActionType.IMPORT_USER_PROJECT_ROLES),
	SCHEDULE_CRON_IMPORT_USER_STATUSES("schedule.cron.import.user.statuses", true, ActionType.IMPORT_USER_STATUSES),
	SCHEDULE_CRON_IMPORT_OWNER_INVOICES("schedule.cron.import.owner.invoices", true, ActionType.IMPORT_OWNER_INVOICES),
	SCHEDULE_CRON_IMPORT_SCHEDULE_OF_VALUES("schedule.cron.import.schedule.of.values", true, ActionType.IMPORT_SCHEDULE_OF_VALUES),
	SCHEDULE_CRON_IMPORT_ERP_RECORDS("schedule.cron.import.erp.records", true, ActionType.IMPORT_ERP_RECORDS),
	SCHEDULE_CRON_IMPORT_ERP_PROJECTS("schedule.cron.import.erp.projects", true, ActionType.IMPORT_ERP_PROJECTS),
	SCHEDULE_CRON_IMPORT_VENDOR_HOLD("schedule.cron.import.vendor.hold", true, ActionType.IMPORT_VENDOR_HOLD),
	SCHEDULE_CRON_GET_PROJECTS("schedule.cron.get.projects", true, ActionType.GET_PROJECTS),

	PROXY_SETTINGS_HOST("proxy.settings.host", false, null),
	PROXY_SETTINGS_PORT("proxy.settings.port", false, null),
	
	EMAIL_SMTP_HOST("email.smtp.host", false, null),
	EMAIL_SMTP_PORT("email.smtp.port", false, null),
	EMAIL_SMTP_TLS_ENABLE("email.smtp.tls.enable", false, null),
	EMAIL_SMTP_USERNAME("email.smtp.username", false, null),
	EMAIL_SMTP_PASSWORD("email.smtp.password", false, null),
	EMAIL_RECIPIENTS("email.recipients", false, null),
	EMAIL_INCLUDE_PAYLOAD("email.include.payload", false, null),
	EMAIL_INCLUDE_ATTACHMENT("email.include.attachment", false, null);
	
	private final String name;
	private final boolean isCronExpression;
	private final ActionType actionType;
	
	private PropertyName(String name,
							boolean isCronExpression,
							ActionType actionType) {
		this.name = name;
		this.isCronExpression = isCronExpression;
		this.actionType = actionType;
	}
	
	public static List<PropertyName> getCronPropertyNames() {
		return Arrays.asList(PropertyName.values())
						.stream()
						.filter(prop -> prop.isCronPropertyName())
						.collect(Collectors.toList());
	}

	public boolean isCronPropertyName() {
		return this.isCronExpression;
	}
	
	public boolean equalsName(String otherName){
		return (otherName == null)? false:name.equalsIgnoreCase(otherName);
	}
	
	public ActionType getActionType() {
		return this.actionType;
	}

	@Override
	public String toString(){
		return name;
	}
}
