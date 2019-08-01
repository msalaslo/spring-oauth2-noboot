package com.verisure.sa.example.service;

import org.springframework.context.annotation.Configuration;

import com.verisure.sa.example.exception.NotFoundException;

@Configuration
public interface DeviceConfigService {
	public String getInstallationNextZoneId(String installationId, String family) throws NotFoundException;
}
