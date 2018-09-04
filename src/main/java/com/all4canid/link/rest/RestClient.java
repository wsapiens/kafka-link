package com.all4canid.link.rest;

import static com.all4canid.link.consts.LinkConsts.CHARSET_ASCII;
import static com.all4canid.link.consts.LinkConsts.CHARSET_UTF8;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Rest Client
 * Synchronous Restful http client by core spring restTemplate
 * 
 * @author spark
 *
 */
//@Component("client")
//@Scope("prototype")
public class RestClient {

	private RestTemplate restTemplate;
	private HttpHeaders headers;

	/**
	 * Constructor of RestClient
	 * @param proxyHost string type proxy host
	 * @param proxyPort string type proxy port
	 */
	public RestClient(String proxyHost, String proxyPort) {
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();

		// if PROXY settings are available
		if(StringUtils.isNotBlank(proxyHost) 
			&& StringUtils.isNotBlank(proxyPort)
			&& StringUtils.isNumeric(proxyPort)) {
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			Proxy proxy= new Proxy(Type.HTTP, new InetSocketAddress(proxyHost, Integer.parseInt(proxyPort)));
			requestFactory.setProxy(proxy);
			restTemplate = new RestTemplate(requestFactory);
		}
	}

	/**
	 * Set Basic Authorization header
	 * @param username textura account username
	 * @param password textura account password
	 */
	public void setBasicAuth(String username, String password) {
		if (null != headers) {
			String auth = username.concat(":").concat(password);
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName(CHARSET_ASCII)) );
			String authHeader = "Basic ".concat(new String( encodedAuth, Charset.forName(CHARSET_ASCII)));
			headers.set( "Authorization", authHeader );
		}
	}

	/**
	 * HttpHeaders of rest client
	 * @return headers
	 */
	public HttpHeaders getHeaders() {
		return headers;
	}

	/**
	 * updated restTemplate
	 * @return restTemplate
	 */
	public RestTemplate getTemplate() {
		return restTemplate;
	}

	/**
	 * Set encoding for sending request
	 * Default UTF-8
	 */
	public void setEncoding() {
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(CHARSET_UTF8)));
	}

	/**
	 * Add json converter to un-marshall json payload
	 */
	public void addJsonConverter(){
		// add if not exist
		if ( restTemplate.getMessageConverters().stream().noneMatch( cvt -> cvt instanceof MappingJackson2HttpMessageConverter ) ) {
			final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			final List<MediaType> supportedMediaTypes = new LinkedList<>(converter.getSupportedMediaTypes());
			final MediaType mediaTypeJson = new MediaType("application", "*+json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET);
			supportedMediaTypes.add(mediaTypeJson);
			converter.setSupportedMediaTypes(supportedMediaTypes);
			restTemplate.getMessageConverters().add(converter);
		}
	}

	/**
	 * Add byteArray converter
	 */
	public void addByteArrayConterver() {
		if(restTemplate.getMessageConverters().stream().noneMatch( cvt -> cvt instanceof ByteArrayHttpMessageConverter) ) {
			restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		}
	}

}
