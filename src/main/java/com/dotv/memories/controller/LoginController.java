package com.dotv.memories.controller;

import com.dotv.memories.config.GooglePojo;
import com.dotv.memories.config.GoogleUtils;
import com.dotv.memories.entity.Account;
import com.dotv.memories.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class LoginController {

    @Autowired
    private GoogleUtils googleUtils;

    @Value("${spring.social.google.clientId}")
    String clientId;
    @Value("${spring.social.google.clientSecret}")
    String clientSecret;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
        String siteURL = request.getRequestURL().toString().replace(request.getServletPath(), "");
        String link="https://accounts.google.com/o/oauth2/auth?scope=openid+profile+email&redirect_uri="+siteURL+"/login_google&response_type=code&client_id="+clientId+"&approval_prompt=force";
        model.addAttribute("link",link);
        return "login";
    }

    @GetMapping("/login_google")
    public String processLogin(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return "redirect:/login";
        }
        String accessToken = googleUtils.getToken(code,request);

        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
        Account user = googleUtils.buildUser(googlePojo);

        if(!user.getStatus()) {
            return "redirect:/login";
        }
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("D"));
        UserDetails userDetail = new User(user.getUsername(),
                "", true,true,true,true, authorities);

        //set user hiện tại
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/dn/home";
    }
}
