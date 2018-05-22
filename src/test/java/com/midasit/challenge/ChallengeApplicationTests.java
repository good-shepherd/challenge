package com.midasit.challenge;

import com.midasit.challenge.entities.User;
import com.midasit.challenge.repositories.UserRepository;
import com.midasit.challenge.services.SocialService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChallengeApplicationTests {

    @Autowired
    SocialService socialService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void addFriend() {
        User user1 = userRepository.findById(4l).get();
        User user2 = userRepository.findById(5l).get();
        socialService.requestFriendship(user1, user2);
    }

}
