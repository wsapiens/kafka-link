package com.all4canid.link.rest;

import static com.all4canid.link.consts.LinkConsts.EMPTY_STRING;
import static com.all4canid.link.consts.LinkConsts.ERROR_MAX_RETRY_LIMIT_REACHED;
import static com.all4canid.link.consts.LinkConsts.HEADER_USER_AGENT;
import static com.all4canid.link.consts.LinkConsts.HTML_STATUS_CODE_CONNECTION_TIMEOUT;
import static com.all4canid.link.consts.LinkConsts.LINK_VERSION;
import static com.all4canid.link.consts.LinkConsts.UNKNOWN_CLIENT;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import com.all4canid.link.domain.job.LinkJob;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public abstract class AbstractRestfulClient {

	protected static final Logger log = LoggerFactory.getLogger(AbstractRestfulClient.class);

	protected static final String LOG_TAG_EXPORT_REQUEST = "<<<<<< Send Export Request <<<<<<";
	protected static final String LOG_TAG_IMPORT_REQUEST = "<<<<<< Send Import Request <<<<<<";
	protected static final String LOG_TAG_GET_RESULT     = "<<<<<< GET Result From eBIS <<<<<";
	protected static final String LOG_TAG_CLIENT         = "client: ";
	protected static final String LOG_TAG_CONTEXT        = "context path: ";
	protected static final String LOG_TAG_USERNAME       = "username: ";
	protected static final String LOG_TAG_PAYLOAD        = "payload: ";
	protected static final String LOG_TAG_RESPONSE_CODE  = "- response code:";

	private static final String	QUERY_PARAM_PROJECT_ID = "projectId";

//	@Autowired
	RestClient client = new RestClient(null, null); 

	long retryIntervalSeconds = 60L;
	int retryNumberLimit = 15;
	String baseUrl;
	String username;
	String password;
	String requestContextPath;
	String erpAdapterVersion;
	Optional<Long> projectId = Optional.empty();
	Object payloadObject;
	List<String> errors;		// The list of error from JobContext

	public AbstractRestfulClient() {
	    client = new RestClient(null, null);
	}

	public AbstractRestfulClient(String proxyHost, String proxyPort) {
	    this();
	    if(StringUtils.isNotBlank(proxyHost) && StringUtils.isNotBlank(proxyPort)) {
	        client = new RestClient(proxyHost, proxyPort);
	    }
	}

	/**
	 * Send request to given contextPath
	 * @param callerName the name of caller
	 * @return Textura job domain object
	 * @throws Exception http exception
	 */
	LinkJob sendExportRequest(Optional<String>callerName) throws Exception {
		return sendRequestWithRetry(() -> {
			ResponseEntity<LinkJob> response = null;
			try {
				if(log.isDebugEnabled()) {
					log.debug(LOG_TAG_EXPORT_REQUEST);
					log.debug(LOG_TAG_CLIENT.concat(this.toString()));
					log.debug(LOG_TAG_USERNAME.concat(username));
					log.debug(LOG_TAG_CONTEXT.concat(requestContextPath));
				}

				// Set auth header
				client.setBasicAuth(username, password);
				client.getHeaders().set(HEADER_USER_AGENT, getUserAgentValue());

				// Send export request with empty body
				client.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> requestEntity = new HttpEntity<>("{}", client.getHeaders());

				UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
				uriBuilder.path(requestContextPath);
				projectId.ifPresent(id -> uriBuilder.queryParam(QUERY_PARAM_PROJECT_ID, id));

				response = client.getTemplate().postForEntity(uriBuilder.toUriString(), requestEntity, LinkJob.class);
				LinkJob job = response.getBody();
				log.info("sendExportRequest - response code: ".concat(response.getStatusCode().toString()));
				return job;
			} catch (Exception e) {
				String errorMessage = errorMessageBuilder(e, callerName);
				log.error(errorMessage);
				throw new Exception(errorMessage, e);
			}
		});
	}

	/**
	 * Get import / export result as json by job uri
	 * @param uri call back uri for resource
	 * @param callerName the name of caller
	 * @return response entity
	 * @throws Exception http exception
	 */
	ResponseEntity<?> getJsonResult(String uri, Optional<String> callerName) throws Exception {
		return getResultWithRetry(() -> {
			ResponseEntity<String> response = null;
			try {
				if(log.isDebugEnabled()) {
					log.debug(LOG_TAG_GET_RESULT);
					log.debug(LOG_TAG_CLIENT.concat(this.toString()));
					log.debug(LOG_TAG_USERNAME.concat(username));
				}

				if(StringUtils.isNotEmpty(uri)) {
					client.setBasicAuth(username, password);
					client.getHeaders().set(HEADER_USER_AGENT, getUserAgentValue());
					client.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
					client.addJsonConverter();

					HttpEntity<String> requestEntity = new HttpEntity<>(client.getHeaders());
					response = client.getTemplate().exchange(uri, HttpMethod.GET, requestEntity, String.class);
					log.info(String.join(" ", callerName.orElse(UNKNOWN_CLIENT), LOG_TAG_RESPONSE_CODE, response.getStatusCode().toString()));
					if( response.getStatusCode().is3xxRedirection() ) {
						log.info(callerName.orElse(UNKNOWN_CLIENT).concat(" - follow redirect"));
						response = client.getTemplate().exchange(response.getHeaders().getLocation().toString(), HttpMethod.GET, requestEntity, String.class);
						log.info(String.join(" ", callerName.orElse(UNKNOWN_CLIENT), LOG_TAG_RESPONSE_CODE, response.getStatusCode().toString()));
					}
				} else {
					log.warn("Job Uri is empty");
				}
				return response;
			} catch (Exception e) {
				String errorMessage = errorMessageBuilder(e, callerName);
				log.error(errorMessage);
				throw new Exception(errorMessage, e);
			}
		});
	}

	/**
	 * Send import request with json payload
	 * @param callerName the name of caller
	 * @return TexturaJob domain object
	 * @throws Exception http exception
	 */
	LinkJob sendImportRequest(Optional<String> callerName) throws Exception {
		return sendRequestWithRetry(() -> {
			ResponseEntity<LinkJob> response = null;
			Gson gson = new GsonBuilder().serializeNulls().create();
			String jsonPayload = gson.toJson(payloadObject);
			try {
				if(log.isDebugEnabled()) {
					log.debug(LOG_TAG_IMPORT_REQUEST);
					log.debug(LOG_TAG_CLIENT.concat(this.toString()));
					log.debug(LOG_TAG_USERNAME.concat(username));
					log.debug(LOG_TAG_CONTEXT.concat(requestContextPath));
					log.debug(LOG_TAG_PAYLOAD.concat(jsonPayload));
				}
				// Set auth header
				client.setBasicAuth(username, password);
				client.getHeaders().set(HEADER_USER_AGENT, getUserAgentValue());
				client.setEncoding();

				// Send import request with payload
				client.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, client.getHeaders());

				UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
				uriBuilder.path(requestContextPath);
				response = client.getTemplate().postForEntity(uriBuilder.toUriString(), requestEntity, LinkJob.class);
				LinkJob job = response.getBody();
				log.info(String.join(" ", callerName.orElse(UNKNOWN_CLIENT), LOG_TAG_RESPONSE_CODE, response.getStatusCode().toString()));
				return job;
			} catch (Exception e) {
				String errorMessage = errorMessageBuilder(e, callerName);
				log.error(errorMessage);
				if(null != e.getCause()
					&& null != e.getCause().getClass()
					&& e.getCause().getClass().equals(HttpClientErrorException.class)
					&& ((HttpClientErrorException)e.getCause()).getStatusCode().equals(HttpStatus.BAD_REQUEST) ) {
					log.error("[ Payload ] ".concat(jsonPayload));
				}
				throw new Exception(errorMessage, e);
			}
		});
	}

	/**
	 * Send import request with json payload as synchronous call
	 * So it doesn't expect to have TexturaJob in response
	 * so it will return null TexturaJob
	 * @param callerName the name of caller
	 * @return null
	 * @throws Exception http exception
	 */
	LinkJob sendImportRequestForNoTexturaJobId(Optional<String> callerName) throws Exception {
		return sendRequestWithRetry(() -> {
			Gson gson = new GsonBuilder().serializeNulls().create();
			String jsonPayload = gson.toJson(payloadObject);
			ResponseEntity<String> response = null;
			try {
				if(log.isDebugEnabled()) {
					log.debug(LOG_TAG_IMPORT_REQUEST);
					log.debug(LOG_TAG_CLIENT.concat(this.toString()));
					log.debug(LOG_TAG_USERNAME.concat(username));
					log.debug(LOG_TAG_CONTEXT.concat(requestContextPath));
					log.debug(LOG_TAG_PAYLOAD.concat(jsonPayload));
				}
				// Set AUTH header
				client.setBasicAuth(username, password);
				client.getHeaders().set(HEADER_USER_AGENT, getUserAgentValue());
				client.setEncoding();

				// Send error report request
				client.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, client.getHeaders());
				UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
				uriBuilder.path(requestContextPath);
				response = client.getTemplate().postForEntity(uriBuilder.toUriString(), requestEntity, String.class);
				log.info(String.join(" ", callerName.orElse(UNKNOWN_CLIENT), "- response code:", response != null ? response.getStatusCode().toString() : EMPTY_STRING));
				return null;
			} catch (Exception e) {
				String errorMessage = String.join(", ", callerName.orElse(UNKNOWN_CLIENT), requestContextPath, e.getMessage());
				log.error(errorMessage);
				throw new Exception(errorMessage, e);
			}
		});
	}

	/**
	 * Build Error message by using response and exception
	 * @param e error 
	 * @param methodName caller method 
	 * @return errorMessage
	 */
	String errorMessageBuilder(Throwable e, Optional<String> methodName) {
		StringJoiner errorMessage = new StringJoiner("");
		errorMessage.add(methodName.orElse(UNKNOWN_CLIENT)).add(" - ");
		if(null != e) {
			errorMessage.add(e.getClass().getSimpleName()).add(":").add(StringEscapeUtils.escapeHtml4(e.getMessage()));
		}
		return errorMessage.toString();
	}


	/**
	 * Function wrapper for sending job request with retry
	 * @param func function to be called and also retried
	 * @return return Textura job from called function
	 * @throws Exception HttpServerErrorException, TexturaClientException
	 */
	public LinkJob sendRequestWithRetry(Callable<LinkJob> func) throws Exception {
		int retry = 0;
		do {
			retry++;
			try {
				return func.call();
			} catch (Exception e) {
				if( isExceptionNotForRetry(e) ) {
					throw e; //we don't want to retry for some Http status codes, so re-throw
				}
				if(null != errors
					&& !errors.contains(e.getMessage())) {
					errors.add(e.getMessage()); // add error message to JobContext's errors
				}
				Thread.sleep( 1000L * retryIntervalSeconds );
				retryIntervalSeconds += 60;
			}
		} while(retry <= Integer.valueOf(retryNumberLimit));
		throw new Exception(ERROR_MAX_RETRY_LIMIT_REACHED);
	}

	/**
	 * Check exception is not for retrying or not
	 * if error is Http status code 599 or any of the codes specified in EnumSet, we don't want to retry
	 * @param e - exception object
	 * @return true if not for retry, otherwise false
	 */
	public boolean isExceptionNotForRetry(Exception e) {
		EnumSet<HttpStatus> codesNotForRetry = EnumSet.of(	HttpStatus.UNAUTHORIZED,						//401
															HttpStatus.METHOD_NOT_ALLOWED,					//405   
															HttpStatus.NOT_ACCEPTABLE, 						//406
															HttpStatus.CONFLICT, 							//409
															HttpStatus.GONE,								//410
															HttpStatus.LENGTH_REQUIRED, 					//411	
															HttpStatus.PRECONDITION_FAILED, 				//412
															HttpStatus.PAYLOAD_TOO_LARGE, 					//413
															HttpStatus.URI_TOO_LONG, 						//414
															HttpStatus.UNSUPPORTED_MEDIA_TYPE,				//415
															HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, 	//416
															HttpStatus.EXPECTATION_FAILED, 					//417
															HttpStatus.NOT_IMPLEMENTED);					//501		
		
		if( (e instanceof HttpClientErrorException && codesNotForRetry.contains(((HttpClientErrorException)e).getStatusCode())) 
			||	(e instanceof HttpServerErrorException && codesNotForRetry.contains(((HttpServerErrorException)e).getStatusCode())) 
			||	(e instanceof UnknownHttpStatusCodeException && ((UnknownHttpStatusCodeException)e).getRawStatusCode() == HTML_STATUS_CODE_CONNECTION_TIMEOUT) ) {
				return true;
		}
		// for case wrapped by other exception
		if( null != e.getCause() 
			&& null != e.getCause().getClass() 
			&& ( (e.getCause().getClass().equals(HttpClientErrorException.class) && codesNotForRetry.contains(((HttpClientErrorException)e.getCause()).getStatusCode()))
				||	(e.getCause().getClass().equals(HttpServerErrorException.class) && codesNotForRetry.contains(((HttpServerErrorException)e.getCause()).getStatusCode()))
				||	( e.getCause().getClass().equals(UnknownHttpStatusCodeException.class) && ((UnknownHttpStatusCodeException)e.getCause()).getRawStatusCode() == HTML_STATUS_CODE_CONNECTION_TIMEOUT)) ) {
			return true;
		}
		return false;
	}

	/**
	 * Function wrapper for getting result with retry
	 * @param func function to be called and also retried
	 * @return return response entity from called function
	 * @throws Exception HttpServerErrorException, TexturaClientException
	 */
	public ResponseEntity<?> getResultWithRetry(Callable<ResponseEntity<?>> func) throws Exception {
		int retry = 0;
		do {
			retry++;
			try {
				return func.call();
			} catch (Exception e) {
				if( isExceptionNotForRetry(e) ) {
					throw e; //we don't want to retry for some Http codes, so re-throw
				}
				if(null != errors
					&& !errors.contains(e.getMessage())) {
					errors.add(e.getMessage()); // add error message to JobContext's errors
				}
				Thread.sleep( 1000L * retryIntervalSeconds );
				retryIntervalSeconds += 60;
			}
		} while(retry <= Integer.valueOf(retryNumberLimit));
		throw new Exception(ERROR_MAX_RETRY_LIMIT_REACHED);
	}

	/**
	 * Get User Agent Header Value
	 * 
	 * Example:
	 * TexturaLink/1.0 (TexturaSapLib/1.0)
	 * @return user agent header value in string
	 */
	public String getUserAgentValue() {
		return String.join("", LINK_VERSION, " (", erpAdapterVersion, ")");
	}
}
