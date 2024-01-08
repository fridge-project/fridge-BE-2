package com.fridgeBE.fridge.repository;

import com.fridgeBE.fridge.model.AccountType;
import com.fridgeBE.fridge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndAccountAndPassword(String email, AccountType accountType, String password);

    User findByEmailAndAccount(String email, AccountType accountType);

    User findById(int userId);
}
