package com.ecom.eilco.repository;

import com.ecom.eilco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}