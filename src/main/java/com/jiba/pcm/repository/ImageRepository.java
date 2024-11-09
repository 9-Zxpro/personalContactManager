package com.jiba.pcm.repository;

import com.jiba.pcm.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
