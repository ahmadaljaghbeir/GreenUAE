package org.example.greenuae.repository;

import org.example.greenuae.model.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long> {
}