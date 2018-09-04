package com.all4canid.link.rest;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;

import com.all4canid.link.domain.job.LinkJob;

/**
 * Textura Client Interface to define client adapter api
 * 
 * @author spark
 *
 */
public interface LinkClient {
	/**
	 * Set Textura cpm credential
	 * @param username textura account username
	 * @param password textura account password
	 */
	public void setAuth(String username, String password);

	/**
	 * Set Textura eBIS url
	 * @param baseUrl textura service base url
	 */
	public void setBaseUrl(String baseUrl);

	/**
	 * Set request context path for import and export
	 * @param requstContextPath request root context path
	 */
	public void setRequestContextPath(String requstContextPath);

	/**
	 * Set ERP Adapter Library version
	 * @param erpAdapterVersion erp adapter library version string
	 */
	public void setErpAdapterVersion(String erpAdapterVersion);

	/**
	 * Set Textura domain object as payload
	 * Only import jobs need to be override this
	 * and export jobs can be exempted.
	 * @param payloadObject object to be serialized on requst body
	 */
	public default void setPayloadObject(Object payloadObject) {
	}

	/**
	 * Set Textura ProjectId for request param
	 * @param projectId String type textura projectId
	 */
	public default void setProjectId(Optional<Long> projectId) {
		LoggerHolder.LOGGER.info("setProjectId is not Implemented for this job client, default method is called");
	}

	/**
	 * Send Request API
	 * @return job
	 * @throws Exception exception during sending request
	 */
	public LinkJob sendRequest() throws Exception;

	/**
	 * Get Result API
	 * @param uri call back uri
	 * @return response entity
	 * @throws Exception exception during getting result or audit log
	 */
	public ResponseEntity<?> getResults(String uri) throws Exception;

	/**
	 * Setter to pass the instance of error list on JobContext to client
	 * so client can report any collected error back to JobContext
	 * @param errors list of String type error messages on JobContext
	 */
	public void setJobContextErrors(List<String> errors);

	/**
	 * Logger holder class for TexturaClient interface
	 * 
	 * @author spark
	 *
	 */
	final class LoggerHolder {
		private static final Logger LOGGER = Logger.getLogger(LinkClient.class);

		private LoggerHolder() {}
	}
}
