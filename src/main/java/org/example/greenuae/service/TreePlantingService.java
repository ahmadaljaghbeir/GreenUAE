package org.example.greenuae.service;

import org.example.greenuae.model.Tree;
import org.example.greenuae.model.TreeInfo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TreePlantingService {
    Mono<String> processTreeSubmission(Tree tree, long tree_id);
}
