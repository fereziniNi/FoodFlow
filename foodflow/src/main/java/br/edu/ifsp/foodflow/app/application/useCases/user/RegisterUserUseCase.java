package br.edu.ifsp.foodflow.app.application.useCases.user;

import br.edu.ifsp.foodflow.app.domain.user.User;
import br.edu.ifsp.foodflow.app.domain.user.UserRepository;
import br.edu.ifsp.foodflow.app.domain.user.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(String name, String username, String email, String password, UserRole role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String encryptedPassword = passwordEncoder.encode(password);
        User user = new User(name, username, email, encryptedPassword, role);
        userRepository.save(user);
    }
}
