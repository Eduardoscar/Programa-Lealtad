package com.oegr.ProgramaLealtadPR.repository;

import com.oegr.ProgramaLealtadPR.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findOneByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = 'USER'")
    List<User> findUsersByRole();
}
