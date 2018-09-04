package com.all4canid.link.rest;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;

import com.all4canid.link.domain.job.LinkJob;

//@Component("importNewContractClient")
//@Scope("prototype")
public class ImportNewContractClientAdapter extends AbstractRestfulClient implements LinkClient {

    public ImportNewContractClientAdapter() {
        super();
    }

    public ImportNewContractClientAdapter(String proxyHost, String proxyPort) {
        super(proxyHost, proxyPort);
    }

	@Override
	public void setAuth(String username, String password) {
		if(StringUtils.isNotBlank(username)) {
			this.username = username;
		}
		if(StringUtils.isNotBlank(password)) {
			this.password = password;
		}
	}

	@Override
	public void setBaseUrl(String baseUrl) {
		if(StringUtils.isNotBlank(baseUrl)) {
			this.baseUrl = baseUrl;
		}
	}

	@Override
	public void setPayloadObject(Object payloadObject) {
		this.payloadObject = payloadObject;
	}

	@Override
	public LinkJob sendRequest() throws Exception {
		return sendImportRequest(Optional.of(this.getClass().getSimpleName()));
	}

	@Override
	public ResponseEntity<?> getResults(String uri) throws Exception {
		return getJsonResult(uri, Optional.of(this.getClass().getSimpleName()));
	}

	@Override
	public void setRequestContextPath(String requstContextPath) {
		if(StringUtils.isNotBlank(requstContextPath)) {
			this.requestContextPath = requstContextPath;
		}
	}

	@Override
	public void setErpAdapterVersion(String erpAdapterVersion) {
		if(StringUtils.isNotBlank(erpAdapterVersion)) {
			this.erpAdapterVersion = erpAdapterVersion;
		}
	}

	@Override
	public void setJobContextErrors(List<String> errors) {
		if(null != errors) {
			this.errors = errors;
		}
	}
}
