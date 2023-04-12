package com.site.employeetimesheetproject.payload;

import java.util.List;

/**
 * ClassName: JwtResponse
 * Package: com.site.employeetimesheetproject.model
 * Description:
 *
 * @Author: Site
 * @Version: v
 */
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String employeeName;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, String id, String employeeName, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.employeeName = employeeName;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String username) {
        this.employeeName = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
