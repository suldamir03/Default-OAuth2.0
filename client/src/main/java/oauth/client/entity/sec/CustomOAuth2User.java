package oauth.client.entity.sec;

import java.util.Map;

public abstract class CustomOAuth2User {
    public Map<String, Object> attributes;

    public abstract String getName();
    public abstract String getLogin();
    public CustomOAuth2User(Map<String, Object> attributes){
        this.attributes=attributes;
    }
}
