package org.example.greenuae.service;

import reactor.core.publisher.Mono;

public interface OpenAIService {
    String getTreeInformation(String treeSpecies, String plantingDate, String location);
}
