package com.cy.share.controller.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.share.common.annotation.UnInterception;
import com.cy.share.common.constant.Jwtconstant;
import com.cy.share.common.constant.ResultCode;
import com.cy.share.common.exception.CodeSendException;
import com.cy.share.common.exception.CodeWrongException;
import com.cy.share.common.exception.UserSaveException;
import com.cy.share.common.exception.UserRegistException;
import com.cy.share.common.utils.JwtUtil;
import com.cy.share.common.utils.Result;
import com.cy.share.pojo.User;
import com.cy.share.service.CodeService;
import com.cy.share.service.UserService;
import com.cy.share.vo.LoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    CodeService codeService;
    @UnInterception
    @PostMapping("/login")
    public Result login(@RequestBody User data){
        //生成token 返回给前端
        User user = userService.getOne( new QueryWrapper<User>()
                .eq("name", data.getName())
                .eq("pwd",data.getPwd()));
        if(user == null) return Result.fail(ResultCode.DATA_NOT_FOUND);
            LoginVo loginVo = new LoginVo();
            BeanUtils.copyProperties(user, loginVo);
            String token = JwtUtil.createJWT(String.valueOf(user.getId()),user.getName(), Jwtconstant.JWT_TTL);
            loginVo.setToken(token);
            return Result.success(loginVo);
    }
    @UnInterception
    @PostMapping("/register/send-code")
    public Result sendCode(@RequestBody Map<String, String> params){
        String phone = params.get("phone");
        User user = userService.getOne( new QueryWrapper<User>()
                .eq("phone", phone));
        if(user!=null){
            throw new UserRegistException();
        }
        String code = String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
        boolean ok = codeService.add(phone,code);
        if(!ok) throw new CodeSendException();
        return Result.success(code);
    }
    @UnInterception
    @PostMapping("/register")
    public Result regist(@RequestBody Map<String, String> params){
        boolean ok = codeService.getOne(params.get("phone"),params.get("code"));
        if(!ok) throw new CodeWrongException();
        return Result.success();
    }
    @UnInterception
    @PostMapping("/registerover")
    public Result registo(@RequestBody User data){
        boolean save = userService.save(data);
        if(!save) throw new UserSaveException();
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(data, loginVo);
        String token = JwtUtil.createJWT(String.valueOf(data.getId()),data.getName(), Jwtconstant.JWT_TTL);
        loginVo.setToken(token);
        return Result.success(loginVo);
    }
}
