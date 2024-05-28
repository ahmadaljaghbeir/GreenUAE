package org.example.greenuae.service;

import org.example.greenuae.model.Tree;
import org.example.greenuae.model.TreeInfo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TreePlantingService {
    List<TreeInfo> getAllTreeInfo();

    Mono<String> processTreeSubmission(Tree tree, long tree_id);

    TreeInfo getTreeById(long treeId);
}
