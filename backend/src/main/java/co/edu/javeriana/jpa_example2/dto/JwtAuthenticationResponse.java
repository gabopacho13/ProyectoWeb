package co.edu.javeriana.jpa_example2.dto;

import co.edu.javeriana.jpa_example2.model.Role;

public class JwtAuthenticationResponse {
    private String token;
    private String email;
    private Role role;

    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String token, String email, Role role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
