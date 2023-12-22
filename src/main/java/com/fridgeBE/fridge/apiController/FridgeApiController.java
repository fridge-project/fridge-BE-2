package com.fridgeBE.fridge.apiController;


import com.fridgeBE.fridge.dto.ResponseDto;
import com.fridgeBE.fridge.model.Fridge;
import com.fridgeBE.fridge.model.Ingredient;
import com.fridgeBE.fridge.model.StorageType;
import com.fridgeBE.fridge.model.User;
import com.fridgeBE.fridge.service.FridgeService;
import com.fridgeBE.fridge.service.IngredientService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FridgeApiController {
    @Autowired
    private FridgeService fridgeService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private HttpSession session;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class RequestIngre {
        private String name;
        private String exp;
        private String memo;
        private String storage;
    }

    @PostMapping("/fridge") // 재료 등록
    public ResponseDto<Integer> addIngredients(@RequestBody RequestIngre requestIngre) {
        Fridge fridge = new Fridge();
        fridge.setExp(requestIngre.getExp());
        fridge.setMemo(requestIngre.getMemo());
        fridge.setStorage(StorageType.valueOf(requestIngre.getStorage()));

        Ingredient ingredient = ingredientService.getIngredient(requestIngre.getName());
        fridge.setIngredient(ingredient);

        User user = (User)(session.getAttribute("principal"));

        // 1. 재료가 있나?
//        boolean exist = fridgeService.isExist(user.getId(), ingredient.getId());

        // 2-1. 동일 재료가 이미 있으면 실패 return
//        if(exist) return new ResponseDto<>(HttpStatus.UNAUTHORIZED.value(), 0);

        // 2-2. 동일 재료가 없으면 등록 후 성공 return

        fridge.setUser(user);

        fridgeService.addIngredient(fridge);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/fridge") // 보유 재료 확인
    public ResponseDto<List<Fridge>> getIngredients() {
        User user = (User)(session.getAttribute("principal"));


        List<Fridge> data = fridgeService.getIngredient(user);

        return new ResponseDto<List<Fridge>>(HttpStatus.OK.value(), data);
    }

}