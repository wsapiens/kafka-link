package com.all4canid.link.consts;

public class LinkConsts {

	private LinkConsts() {
		// hide constructor
	}

	/** Client Version Info sent via User-Agent header on request */
	public static final String LINK_VERSION 	= "TexturaLink/18.7.0";
	public static final String HEADER_USER_AGENT = "User-Agent";
	public static final String UNKNOWN_CLIENT		= "unknown client";
	public static final String EMPTY_STRING		= "";
	public static final String PERCENT_FIND_ALL	= "%";
	public static final String EMPTY_SPACE		= " ";
	public static final String COMMA				= ",";
	public static final String FILE_EXTENSION_SEPARATOR = ".";
	public static final String STATUS_ERROR		= "ERROR";
	public static final String STATUS_FAILURE		= "FAILURE";
	public static final String STATUS_SUCCESS		= "SUCCESS";
	public static final String STATUS_INFO		= "INFO";
	public static final String STATUS_DUPLICATE	= "DUPLICATE";
	
	public static final int MAX_DB_VARCHAR_LENGTH = 3000;
	
	public static final String FONT_SUCCESS 		= "<font color=\"green\">success</font>";
	public static final String FONT_FAIL 		= "<font color=\"red\">fail</font>";
	public static final String FONT_STARTED		= "<font color=\"black\">started</font>";
	public static final String FONT_UNKNOWN 		= "<font color=\"grey\">unknown</font>";
	public static final String VIEW_TEXT_EMPTY 	= "-";
	public static final int VIEW_TEXT_SIZE_LIMIT 		= 200;
	public static final int BUTTON_TEXT_SIZE_LIMIT 	= 50;
	public static final int MAX_LOG_LINE 			= 3000;
	
	public static final String SCHEDULER_TRIGGER_CRON = "0 */1 * * * *";
	public static final String ENVIRONMENT_PROPERTY_LOGGING_FILE = "logging.file";

	// Textura bulk api
	public static final String BULK_REQUEST_KEY_PROJECT_NUMBER			= "projectNumber";
	public static final String BULK_REQUEST_KEY_CHANGE_ORDER_NUMBER 		= "changeOrderNumber";
	public static final String BULK_REQUEST_KEY_CHANGE_ORDER_TITLE		= "changeOrderTitle";
	public static final String BULK_REQUEST_KEY_CHANGE_ORDER_DESCRIPTION	= "changeOrderDescription";
	public static final String BULK_REQUEST_KEY_CHANGE_ORDER_AMOUNT		= "changeOrderAmount";
	public static final String BULK_REQUEST_KEY_CHANGE_ORDER_DATE			= "changeOrderDate";

	// encryption
	/** this is for old TL installation */
	public static final String ENCRYPTION_KEY_VALUE_DEFAULT 		= "t3$tu5@s3c53tc0d";
	public static final String ENCRYPTION_KEY_FILEPATH			= "./link.key";
	public static final String PROPERTY_NAME_KEY_PASSWORD 		= "password";

	// encoding
	public static final String CHARSET_UTF8						= "UTF-8";
	public static final String CHARSET_ASCII						= "US-ASCII";
	
	//html detection
	public static final String HTML_OPEN_BRACKET_1				= "<";
	public static final String HTML_OPEN_BRACKET_2				= "&lt";
	public static final String HTML_OPEN_BRACKET_3				= "&#60";
	public static final String HTML_CLOSE_BRACKET_1				= ">";
	public static final String HTML_CLOSE_BRACKET_2				= "&lt";
	public static final String HTML_CLOSE_BRACKET_3				= "&#62";
	public static final String HTML_NEW_LINE 						= "</br>";
	public static final String HTML_EXCEPTION_MESSAGE				= "Http request is not readable!";
	public static final int HTML_STATUS_CODE_CONNECTION_TIMEOUT	= 599;
	
	//exception handling
	public static final String RUNTIME_EXCEPTION_MESSAGE			= "A run-time error has occurred!";

	// Secret Log Line
	public static final String LOG_LINE_PATTERN_SECRET			= "(<\\w+:Password>.+</\\w+:Password>)|(<\\w+:InterfaceLicense>.+</\\w+:InterfaceLicense>)";
	public static final String LOG_LINE_OBFUSCATED				= "<obfuscated>***</obfuscated>";

	// Error Message 
	public static final String ERROR_MAX_RETRY_LIMIT_REACHED		= "Maximum Retry Limit Reached!";
	
	//email notification
	public static final String TEXTURA_EMAIL_JOB_SUCCESS				= "[job completed]";
	public static final String TEXTURA_EMAIL_JOB_FAILURE				= "[job failed]";
	public static final String TEXTURA_EMAIL_TITLE						= "TexturaLink Job Report";
	public static final String TEXTURA_PACKAGE_PREFIX					= "com.textura";
	
	// jQuery datatables ajax param
	public static final String DATATABLES_AJAX_PARAM_ORDER_COLUMN		= "order[0][column]";
	public static final String DATATABLES_AJAX_PARAM_ORDER_DIRECTION	= "order[0][dir]";
	public static final String DATATABLES_AJAX_PARAM_SEARCH_VALUE		= "search[value]";
	public static final String SORT_ORDER_ASCENDING				= "asc";
	public static final String SORT_ORDER_DESCENDING				= "desc";
	
	//Properties
	public static final String CRON_PROPERTY_SUBSTRING					= "CRON";
	public static final Integer JOB_EXECUTION_LOOK_BACK_WINDOW			= -4;
}
