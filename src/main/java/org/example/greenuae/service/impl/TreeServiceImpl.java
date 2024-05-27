package org.example.greenuae.service.impl;
import org.example.greenuae.exception.ResourceNotFoundException;
import org.example.greenuae.model.Tree;
import org.example.greenuae.repository.TreeRepository;
import org.example.greenuae.service.TreeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeServiceImpl implements TreeService {

    private TreeRepository treeRepository;

    public TreeServiceImpl(TreeRepository treeRepository) {
        super();
        this.treeRepository = treeRepository;
    }

    @Override
    public Tree addTree(Tree tree) {
        return treeRepository.save(tree);
    }

    @Override
    public Tree getTreeById(long id) {
        return treeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tree", "Id", id));
    }

    @Override
    public List<Tree> getAllTree() {
        return treeRepository.findAll();
    }


    @Override
    public void deleteTree(long id) {
        treeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tree", "Id", id));
        treeRepository.deleteById(id);
    }
}
