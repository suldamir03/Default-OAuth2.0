package oauth.client.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oauth.client.entity.Role;
import oauth.client.entity.User;
import oauth.client.entity.sec.CustomOAuth2User;
import oauth.client.entity.sec.OAuth2Factory;
import oauth.client.entity.sec.OAuth2UserDetails;
import oauth.client.repo.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            return checkOAuth2User(userRequest, oAuth2User);
        } catch (Exception e) {
            log.error("Exception happened during authorization: {}\nStackTrace: {}", e.getLocalizedMessage(),
                    e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    private OAuth2User checkOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User) {

        CustomOAuth2User user = OAuth2Factory
                .factory(
                        request.getClientRegistration().getRegistrationId(),
                        oAuth2User.getAttributes()
                );

        Optional<User> optionalUser = userRepository.findUserByUsernameAndProviderId(
                user.getLogin(),
                request.getClientRegistration().getRegistrationId()
        );

        User userDetails = optionalUser.orElseGet(() -> registerNewOAuthUserDetails(request, user));

        return new OAuth2UserDetails(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getRole()
        );
    }


    private User registerNewOAuthUserDetails(OAuth2UserRequest request, CustomOAuth2User user) {
        log.info("in registerNewOAuthUserDetails method");
        User newUser = new User();
        newUser.setUsername(user.getLogin());
        newUser.setProviderId(request.getClientRegistration().getRegistrationId());
        newUser.setRole(Role.ROLE_USER);
        return userRepository.saveAndFlush(newUser);
    }
}
