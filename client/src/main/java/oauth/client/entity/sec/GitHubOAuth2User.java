package oauth.client.entity.sec;

import java.util.Map;

public class GitHubOAuth2User extends CustomOAuth2User{

    public GitHubOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getLogin() {
        return attributes.get("login").toString();
    }
}
