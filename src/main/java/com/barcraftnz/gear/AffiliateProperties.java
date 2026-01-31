package com.barcraftnz.gear;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "aff")
public class AffiliateProperties {

    /**
     * Binds properties like:
     * aff.links.shaker.bestoverall=...
     * aff.links.shaker.budget=...
     * aff.links.table.bestoverall=...
     * aff.links.table.budget=...
     *
     * Into:
     * links["shaker"]["bestoverall"]
     * links["shaker"]["budget"]
     * links["table"]["bestoverall"]
     * links["table"]["budget"]
     */
    private Map<String, Map<String, String>> links = new HashMap<>();

    public Map<String, Map<String, String>> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Map<String, String>> links) {
        this.links = links;
    }
}
