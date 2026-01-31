package com.barcraftnz.gear;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AffiliateProperties.class)
public class GearConfig {
    // Additive only. No changes to your main app class needed.
}