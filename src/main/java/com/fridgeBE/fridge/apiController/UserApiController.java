package com.fridgeBE.fridge.apiController;

import com.fridgeBE.fridge.dto.ResponseDto;
import com.fridgeBE.fridge.model.AccountType;
import com.fridgeBE.fridge.model.User;
import com.fridgeBE.fridge.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.fridgeBE.fridge.model.AccountType.SELF;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @PostMapping("/signup") // 회원가입
    public ResponseDto<Integer> signup(@RequestBody User user) {
        user.setAccount(SELF); // 자체 로그인

        boolean result = userService.signup(user);
        if(result) return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 회원가입 성공(200)
        return new ResponseDto<Integer>(HttpStatus.UNAUTHORIZED.value(), 0); // 회원가입 실패(401)
    }

    @PostMapping("/login") // 로그인
    public ResponseDto<Integer> login(@RequestBody User user) {
        user.setAccount(SELF);
        User principal = userService.login(user);

        if(principal != null) {
            session.setAttribute("principal", principal);
            return new ResponseDto<Integer>(HttpStatus.OK.value(), principal.getId()); // 로그인 성공(200)
        }
        else {
            boolean idExist = userService.idCheck(user);
            if(idExist) return new ResponseDto<Integer>(HttpStatus.UNAUTHORIZED.value(), 10); // id/pw 오류
            else return new ResponseDto<Integer>(HttpStatus.UNAUTHORIZED.value(), 0); // id 존재x
        }
    }

    @PostMapping("/logout") // 로그아웃
    public ResponseDto<Integer> logout(@RequestBody User user) {
        return new ResponseDto<>(HttpStatus.OK.value(), 1); // 미완
    }

}
