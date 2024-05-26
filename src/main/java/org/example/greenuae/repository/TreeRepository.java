package org.example.greenuae.repository;

import org.example.greenuae.model.Tree;
import org.example.greenuae.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TreeRepository extends JpaRepository<Tree, Long> {
}