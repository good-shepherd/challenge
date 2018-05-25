package com.midasit.challenge.repositories;

import com.midasit.challenge.entities.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
