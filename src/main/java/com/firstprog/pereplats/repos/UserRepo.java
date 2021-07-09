package com.firstprog.pereplats.repos;

        import com.firstprog.pereplats.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    List<User> findAll();

    User findByUserid (String userid);

    Optional <User> findByUsername(String username);

    User findUserByUsername (String username);

    List<User> findByActive(int active);
}
