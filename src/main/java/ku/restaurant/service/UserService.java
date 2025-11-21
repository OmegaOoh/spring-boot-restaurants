package ku.restaurant.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import ku.restaurant.dto.SignUpRequest;
import ku.restaurant.entity.User;
import ku.restaurant.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public void createUser(SignUpRequest request) throws EntityExistsException {
        if (userExists(request.getUsername())) {
            throw new EntityExistsException("Username already exists");
        }
        User dao = new User();
        dao.setUsername(request.getUsername());
        dao.setPassword(encoder.encode(request.getPassword()));
        dao.setName(request.getName());

        dao.setRole("ROLE_USER");
        dao.setCreatedAt(Instant.now());

        userRepository.save(dao);
    }

    public User findOrCreateGoogleUser(String email, String name) {
        User user = this.userRepository.findByUsername(email);
        if (user == null) {
            User dao = new User();
            dao.setUsername(email);
            dao.setName(name);
            dao.setPassword(encoder.encode("NO_PASSWORD"));
            dao.setRole("ROLE_USER");
            dao.setCreatedAt(Instant.now());
            return userRepository.save(dao);
        }
        return user;
    }
}
