package com.verisure.sa.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import com.verisure.sa.example.exception.NotFoundException;
import com.verisure.sa.example.integration.OAuth2RestClient;
import com.verisure.sa.example.model.Installation;

@Service
// Import required in Spring 3 for autowire beans
@Import({OAuth2RestClient.class})
public class DeviceConfigServiceImpl implements DeviceConfigService {

	@Autowired
	private OAuth2RestClient client;

	public String getInstallationNextZoneId(String installationId, String family) throws NotFoundException {
		Installation installation = client.getInstallations(installationId, family);
		return installation.getNextZoneId();
	}

}
