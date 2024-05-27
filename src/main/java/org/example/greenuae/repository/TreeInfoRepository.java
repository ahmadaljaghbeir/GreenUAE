package org.example.greenuae.repository;

import org.example.greenuae.model.Tree;
import org.example.greenuae.model.TreeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeInfoRepository extends JpaRepository<TreeInfo, Long> {
}