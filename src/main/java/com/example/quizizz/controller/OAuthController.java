package com.example.quizizz.controller;

import com.example.quizizz.model.Root;
import com.example.quizizz.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class OAuthController {

    private UserService userService;
    @GetMapping("/sign")
    public Map<String, Object> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        System.out.println(toPerson(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getName());
        System.out.println(toPerson(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getEmail());
        System.out.println(toPerson(oAuth2AuthenticationToken.getPrincipal().getAttributes()).getPicture());
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello security";
    }
    @GetMapping("/security")
    public String security() {
       return "hello security";
    }

    public Root toPerson(Map<String, Object> map){
        if (map == null){
            return null;
        }
        Root root = new Root();
        root.setEmail((String) map.get("email"));
        root.setName((String) map.get("name"));
        root.setPicture((String) map.get("picture"));
        return root;
    }
    @PostMapping("/logout")
    public String logout() {
        // Xóa thông tin đăng nhập khi đăng xuất
        SecurityContextHolder.clearContext();

        return "Logout successful";
    }
}
