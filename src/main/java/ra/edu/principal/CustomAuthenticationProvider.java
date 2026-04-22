package ra.edu.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ra.edu.entity.User;
import ra.edu.repository.UserRepository;
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String input = authentication.getName();
        String password = authentication.getCredentials().toString();


        User user = userRepo.findByUsername(input)
                .or(() -> userRepo.findByEmail(input))
                .or(() -> userRepo.findByPhone(input))
                .orElseThrow(() -> new BadCredentialsException("User not found"));


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        UserPrincipal principal = new UserPrincipal(user);

        return new UsernamePasswordAuthenticationToken(
                principal,
                password,
                principal.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
