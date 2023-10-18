package oauth.client.entity.sec;

import java.util.Map;
import oauth.client.entity.Provider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class OAuth2Factory {

    public static CustomOAuth2User factory(String registrationId, Map<String, Object> attributes){
        if (registrationId.toLowerCase().equals(Provider.github.toString())){
            return new GitHubOAuth2User(attributes);
        }else throw new BadCredentialsException(String.format("Invalid registrationId %s", registrationId));
    }
}
