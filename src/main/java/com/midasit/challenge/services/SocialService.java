package com.midasit.challenge.services;

import com.midasit.challenge.entities.CompositePK;
import com.midasit.challenge.entities.Relationship;
import com.midasit.challenge.entities.User;
import com.midasit.challenge.repositories.RelationshipRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class SocialService {

    RelationshipRepository relationshipRepository;

    @Transactional
    public void requestFriendship(User user1, User user2) {
        Relationship relationship = new Relationship(new CompositePK(user1.getId(), user2.getId()), user1, user2, 0, user1.getId());
        log.info("user1 id: " + relationship.getId().getUserOneId() + ", user2 id: " + relationship.getId().getUserTwoId());
        relationshipRepository.save(relationship);
    }

    public void acceptFriendRequest() {

    }

    public void checkFriendship() {

    }

    public void listFriends() {

    }

    public void listPendingRequests() {

    }

    public void checkMyRequest() {

    }

}