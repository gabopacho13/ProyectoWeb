package co.edu.javeriana.jpa_example2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.javeriana.jpa_example2.dto.JwtAuthenticationResponse;
import co.edu.javeriana.jpa_example2.dto.LoginDTO;
import co.edu.javeriana.jpa_example2.dto.UserRegistrationDTO;
import co.edu.javeriana.jpa_example2.dto.JugadorDTO;
import co.edu.javeriana.jpa_example2.model.Jugador;
import co.edu.javeriana.jpa_example2.model.Role;
import co.edu.javeriana.jpa_example2.model.User;
import co.edu.javeriana.jpa_example2.repository.JugadorRepository;
import co.edu.javeriana.jpa_example2.repository.UserRepository;

@Service
public class AuthenticationService {

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(UserRegistrationDTO request) {
        Role selectedRole = Role.valueOf(request.getRole().toUpperCase());
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                //Role.COMERCIANTE);
                selectedRole);
        userRepository.save(user);
        jugadorRepository.save(new Jugador(user.getFirstName() + " " + user.getLastName(), user.getRole().toString()));
        String jwt = jwtService.generateToken(user.getUsername());
        return new JwtAuthenticationResponse(jwt, user.getEmail(), user.getRole());
    }

    public JwtAuthenticationResponse login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwt = jwtService.generateToken(user.getUsername());
        return new JwtAuthenticationResponse(jwt, user.getEmail(), user.getRole());
    }

}
