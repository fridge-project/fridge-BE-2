package com.fridgeBE.fridge.apiController;

import com.fridgeBE.fridge.dto.UserLoginResponseDto;
import com.fridgeBE.fridge.dto.ResponseDto;
import com.fridgeBE.fridge.jwt.JwtTokenizer;
import com.fridgeBE.fridge.model.User;
import com.fridgeBE.fridge.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.fridgeBE.fridge.model.AccountType.SELF;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private JwtTokenizer jwtTokenizer; // jwt

    @PostMapping("/signup") // 회원가입
    public ResponseDto<Integer> signup(@RequestBody User user) {
        user.setAccount(SELF); // 자체 로그인



        boolean result = userService.signup(user);
        if(result) return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 회원가입 성공(200)
        return new ResponseDto<Integer>(HttpStatus.UNAUTHORIZED.value(), 0); // 회원가입 실패(401)
    }

    @PostMapping("/login") // jwt 로그인
    public ResponseDto login(@RequestBody User user) {

        UserLoginResponseDto loginResponse = new UserLoginResponseDto();

        user.setAccount(SELF);
        if(!userService.idCheck(user)) return new ResponseDto<UserLoginResponseDto>(HttpStatus.UNAUTHORIZED.value(), loginResponse); // id 존재x

        user.setId(userService.login(user));
        if(user.getId() == -1) return new ResponseDto<UserLoginResponseDto>(HttpStatus.UNAUTHORIZED.value(), loginResponse); // id/pw 오류

        String accessToken = jwtTokenizer.createAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtTokenizer.createRefreshToken(user.getId(), user.getEmail());

        loginResponse = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();

        return new ResponseDto<UserLoginResponseDto>(HttpStatus.OK.value(), loginResponse);
    }

    @PostMapping("/logout") // 로그아웃
    public ResponseDto<Integer> logout(@RequestBody User user) {
        return new ResponseDto<>(HttpStatus.OK.value(), 1); // 미완
    }

}
