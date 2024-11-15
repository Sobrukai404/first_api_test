package com.firstapi.teste.repositories;
import com.firstapi.teste.models.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByLogin(String login);
    boolean existsByLogin(String login);
}
