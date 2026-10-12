package onetoone.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);
    User findByName(String username);
    User findByNameAndPassword(String username, String password);
    @Transactional
    void deleteById(int id);
    boolean existsByName(String name);
}
