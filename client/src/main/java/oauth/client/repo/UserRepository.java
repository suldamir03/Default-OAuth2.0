package oauth.client.repo;

import java.util.Optional;
import oauth.client.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsernameAndProviderId(String username, String providerId);
}
