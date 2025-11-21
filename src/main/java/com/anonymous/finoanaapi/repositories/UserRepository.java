package com.anonymous.finoanaapi.repositories;

import com.anonymous.finoanaapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {}
