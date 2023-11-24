package com.example.backend.repository;

import com.example.backend.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    List<Photo> findByTitleContainingIgnoreCase(String titleKeyword);

    List<Photo> findByVisibilityTrue();
    List<Photo> findByTitleContainingIgnoreCaseAndVisibilityTrue(String titleKeyword);

}
