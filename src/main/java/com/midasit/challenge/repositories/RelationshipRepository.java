package com.midasit.challenge.repositories;

import com.midasit.challenge.entities.CompositePK;
import com.midasit.challenge.entities.Relationship;
import org.springframework.data.repository.CrudRepository;

public interface RelationshipRepository extends CrudRepository<Relationship, CompositePK> {

}
