package com.fridgeBE.fridge.service;

import com.fridgeBE.fridge.model.User;
import com.fridgeBE.fridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional // 회원가입
    public boolean signup(User user) {
        if(!idCheck(user)) {
            userRepository.save(user); // Email 중복 아니면 가입
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true) // 로그인
    public int login(User user) {
        User cur = userRepository.findByEmailAndAccountAndPassword(user.getEmail(),user.getAccount(), user.getPassword());
        if(cur != null) return cur.getId();
        else return -1;
    }

    @Transactional(readOnly = true) // id 존재 여부
    public boolean idCheck(User user) {
        if(userRepository.findByEmailAndAccount(user.getEmail(), user.getAccount()) != null) return true;
        else return false;
    }

    @Transactional(readOnly = true) // user정보 가져오기
    public User getUser(int userId) {
        return userRepository.findById(userId);
    }
}