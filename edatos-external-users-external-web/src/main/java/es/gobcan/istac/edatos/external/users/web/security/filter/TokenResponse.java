package es.gobcan.istac.edatos.external.users.web.security.filter;

import java.io.Serializable;

public class TokenResponse implements Serializable {

    private static final long serialVersionUID = 839288033110993243L;

    String token;

    String username;

    public TokenResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "TokenResponse{" + "token='" + token + '\'' + ", username='" + username + '\'' + '}';
    }
}
