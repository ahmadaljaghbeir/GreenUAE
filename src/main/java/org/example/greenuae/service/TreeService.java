package org.example.greenuae.service;

import org.example.greenuae.model.Tree;


import java.util.List;

public interface TreeService {
    Tree addTree(Tree tree);

    Tree getTreeById(long id);

    List<Tree> getAllTree();

    void deleteTree(long id);
}
