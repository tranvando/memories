package com.dotv.memories.config;

import com.dotv.memories.entity.Account;
import com.dotv.memories.repository.AccountRepository;
import com.dotv.memories.service.AccountService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class GoogleUtils {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Value("${spring.social.google.clientId}")
    String GOOGLE_CLIENT_ID;
    @Value("${spring.social.google.clientSecret}")
    String GOOGLE_CLIENT_SECRET;

    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";
    public String getToken(final String code, HttpServletRequest request) throws ClientProtocolException, IOException {
        String siteURL = request.getRequestURL().toString().replace(request.getServletPath(), "");
        String GOOGLE_REDIRECT_URI=siteURL+"/login_google";
        String response = Request.Post(GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", GOOGLE_CLIENT_ID)
                        .add("client_secret", GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }
    public GooglePojo getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        GooglePojo googlePojo = mapper.readValue(response, GooglePojo.class);
        //System.out.println(googlePojo);
        return googlePojo;
    }
    public Account buildUser(GooglePojo googlePojo) {
        //Kiểm tra nếu là khách hàng mới thì đăng lưu vào db
        if(accountRepository.findByUsername(googlePojo.getEmail()).size()==0){
            Account user = new Account();
            user.setFullName(googlePojo.getName());
            user.setUsername(googlePojo.getEmail());
            user.setPassword("dfdfder343");
            user.setStatus(false);
            //lưu user vào db
            accountRepository.save(user);
        }
        return accountRepository.findByUsername(googlePojo.getEmail()).get(0);
    }
}
