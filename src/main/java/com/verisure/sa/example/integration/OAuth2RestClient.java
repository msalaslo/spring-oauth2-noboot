package com.verisure.sa.example.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.verisure.sa.example.handler.RestTemplateResponseErrorHandler;
import com.verisure.sa.example.interceptor.RequestResponseLoggingInterceptor;
import com.verisure.sa.example.model.Installation;

@Service
public class OAuth2RestClient {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final String API_URL = "https://mc-ef-apim-appservice.epi.securitasdirect.local:8243/device-support/device-config-repository/v1.0";
	private static final String CLIENT_CREDENTIAL_KEY = "zsIwz8rA8OjfEgCigEyZ3jZlxo0a";
	private static final String CLIENT_CREDENTIAL_SECRET = "wLkfEE1BUYCfqofXDfvVSC1gI58a";
	private static final String TOKEN_URL = "https://mc-ef-apim-appservice.epi.securitasdirect.local:8243/token";
	
//	private static final String API_URL = "http://ef102pocmom0v.epi.securitasdirect.local:8280/device-support/device-config-repository/v1.0";
//	private static final String CLIENT_CREDENTIAL_KEY = "muQ3jIkbS6YbCwhzAt_8iRapB9Ma";
//	private static final String CLIENT_CREDENTIAL_SECRET = "1kGJUImZ39IrfPgRGrxAMmbd5Jsa";
//	private static final String TOKEN_URL = "http://ef102pocmom0v.epi.securitasdirect.local:8280/token";

	
	
	private OAuth2RestTemplate oauth2RestTemplate = restTemplate();
	
	/**
	 * get all Installations
	 * 
	 */
	public Installation getInstallations(String installationId, String family) {
		String url = API_URL + "/config/installation/"+ installationId + "/nextNodeAvailable?family=" + family; 
		Installation installation = oauth2RestTemplate.getForObject(url, Installation.class);
		log.debug("Installation zone:" + installation);
		return installation;
	}
	
	/**
	 * OAuth2 Rest template
	 * @return
	 */
	private OAuth2RestTemplate restTemplate() {
		ClientCredentialsResourceDetails  resourceDetails = getClientCredentialsResourceDetails();
		OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
		restTemplate.setRetryBadAccessTokens(true);
		setInterceptors(restTemplate);
		setErrorHandler(restTemplate);
		return restTemplate;
	}
	
	private ClientCredentialsResourceDetails getClientCredentialsResourceDetails() {
		ClientCredentialsResourceDetails  resourceDetails = new ClientCredentialsResourceDetails ();
		resourceDetails.setGrantType("client_credentials");
		resourceDetails.setAccessTokenUri(TOKEN_URL);
		resourceDetails.setClientId(CLIENT_CREDENTIAL_KEY);
		resourceDetails.setClientSecret(CLIENT_CREDENTIAL_SECRET);
		return resourceDetails;
	}
	
	private void setInterceptors(RestTemplate restTemplate) {
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		interceptors.add(new RequestResponseLoggingInterceptor());
		restTemplate.setInterceptors(interceptors);
	}
	
	private void setErrorHandler(RestTemplate restTemplate) {
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
	}

}