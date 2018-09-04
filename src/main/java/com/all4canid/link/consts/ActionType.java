package com.all4canid.link.consts;

public enum ActionType {
	/** export_invoices for exporting invoices */
	EXPORT_INVOICES("export_invoices", false),
	/** export_payments for exporting payments */
	EXPORT_PAYMENTS("export_payments", false),
	/** export_rejections for exporting invoice rejections */
	EXPORT_REJECTIONS("export_rejections", false),
	/** export_compliances for exporting compliance requirements */
	EXPORT_COMPLIANCES("export_compliances", false), 
	/** export_documents for exporting documents */
	EXPORT_DOCUMENTS("export_documents", false),
	/** export_changeorders for exporting change orders */
	EXPORT_CHANGEORDERS("export_changeorders", false),
	/** import_roles for importing user project roles */
	IMPORT_USER_PROJECT_ROLES("import_user_project_roles", true),	// user-project-roles
	/** import_user_statuses for importing user activation status */
	IMPORT_USER_STATUSES("import_user_statuses", true),			// user-status
	/** import_new_contracts for importing new contracts */
	IMPORT_NEW_CONTRACTS("import_new_contracts", true),			// insert-contracts
	/** import_updated_contracts for importing updated contracts */
	IMPORT_UPDATED_CONTRACTS("import_updated_contracts", true),	// update-contracts
	/** import_changesorders for importing change orders */
	IMPORT_CHANGEORDERS("import_changeorders", true),
	/** import_prime_changeorders for importing prime change orders */
	IMPORT_PRIME_CHANGEORDERS("import_prime_changeorders", true),
	/** import_changeorder_compliances for importing co compliances */
	IMPORT_CHANGEORDER_COMPLIANCES("import_changeorder_compliances", true),
	/** import_compliances for importing compliances */
	IMPORT_COMPLIANCES("import_compliances", true),
	/** import_sov for importing SOV(Phase Code) */
	IMPORT_SCHEDULE_OF_VALUES("import_schedule_of_values", true),
	/** import_owner_invoice for importing owner invoice */
	IMPORT_OWNER_INVOICES("import_owner_invoices", true),
	/** import_erp_records for importing ERP records to Textura */
	IMPORT_ERP_RECORDS("import_erp_records", true),
	/** import_erp_projects for importing ERP projects to Textura */
	IMPORT_ERP_PROJECTS("import_erp_projects", true),
	/** import vendor hold */
	IMPORT_VENDOR_HOLD("import_vendor_hold", true),
	/** report_errors for reporting error to Textura */
	REPORT_ERRORS("report_errors", false),
	/** get projects from Textura */
	GET_PROJECTS("get_projects", false);

	private final String name;
	private final Boolean isImport;

	private ActionType(String name, Boolean isImport) {
		this.name = name;
		this.isImport = isImport;
	}

	public boolean isImport() {
		return isImport;
	}

	public boolean equalsName(String otherName){
		return (otherName == null)? false:name.equalsIgnoreCase(otherName);
	}

	@Override
	public String toString(){
		return name;
	}
}
