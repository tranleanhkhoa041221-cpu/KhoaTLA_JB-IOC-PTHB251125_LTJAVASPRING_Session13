package ra.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.edu.dto.request.LoginRequest;
import ra.edu.dto.request.RegisterRequest;
import ra.edu.entity.Role;
import ra.edu.entity.User;
import ra.edu.exception.ApiException;
import ra.edu.principal.UserPrincipal;
import ra.edu.repository.UserRepository;
@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest req) {

        if (userRepo.findByUsername(req.getUsername()).isPresent()) {
            throw new ApiException("Username already exists");
        }

        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new ApiException("Email already exists");
        }

        if (userRepo.findByPhone(req.getPhone()).isPresent()) {
            throw new ApiException("Phone already exists");
        }
        if (req.getPassword().length() < 6) {
            throw new ApiException("Password must be at least 6 characters");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.USER);

        return userRepo.save(user);
    }

    public User login(LoginRequest req) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getInput(),     // email/username/phone
                            req.getPassword()
                    )
            );

            UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
            return principal.getUser();

        } catch (BadCredentialsException e) {
            throw new ApiException(e.getMessage());
        }
    }
}
