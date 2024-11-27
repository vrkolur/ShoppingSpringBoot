package com.varun.shopping.data;

import com.varun.shopping.model.User;
import com.varun.shopping.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class UserCreatorComponent implements CommandLineRunner {

    private final UserRepository userRepository;

    public UserCreatorComponent(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        createUsers();
    }

    private void createUsers() {
        List<User> users = IntStream.range(1, 6)
                .mapToObj(i -> {
                    User user = new User();
                    user.setFirstName("User" + i);
                    user.setLastName("LastName" + i);
                    user.setEmail("user" + i + "@example.com");
                    user.setPassword("password" + i);
                    return user;
                }).collect(Collectors.toList());

        userRepository.saveAll(users);
    }
}
