package com.example.Banking.application.authentication;

import com.example.Banking.application.accountManagement.AccountCreation;
import com.example.Banking.application.accountManagement.AccountCreationRepo;
import com.example.Banking.application.transactionManagement.TransactionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountCreationRepo accountRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtRepo jwtRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TransactionRepository transactionRepo;

    public User registerUser(String name, String password, String email, AccountCreation.AccountType accountType, long balance) {
        log.traceEntry("Entering registerUser with name: {}, email: {}, accountType: {}, balance: {}", name, email, accountType, balance);
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        User user = new User();
        user.setUsername(name);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        user.setAccountType(accountType);
        user.setBalance(balance);
        User saveUser = userRepo.save(user);

        String accountNumber = generateAccountNumber();

        AccountCreation accountCreation = AccountCreation.builder()
                .user(saveUser)
                .accountType(accountType)
                .accountNumber(accountNumber)
                .balance(balance)
                .createOn(LocalDate.now())
                .build();
        AccountCreation saveAccount = accountRepo.save(accountCreation);
        saveUser.setAccount(saveAccount);
        userRepo.save(saveUser);
        log.traceExit("Exiting registerUser with user: {}", saveUser);

        return saveUser;
    }

    private String generateAccountNumber() {
        log.traceEntry("Entering generateAccountNumber");
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i <= 10; i++) {
            accountNumber.append(random.nextInt(10));
        }
        log.traceExit("Exiting generateAccountNumber with accountNumber: {}", accountNumber.toString());
        return accountNumber.toString();
    }

    public User findbyEmail(String email) {
        log.traceEntry("Entering findbyEmail with email: {}", email);
        var user = userRepo.findByemail(email);
        log.traceExit("Exiting findbyEmail with user: {}", user);
        return user;

    }

    public String login(String email, String password) {
        log.traceEntry("Entering login with email: {}", email);

        User user = userRepo.findByemail(email);
        if (user == null || !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user);
        log.traceExit("Exiting login with token: {}", token);
        return token;

    }

    public void changePassword(String email, String newPassword, String confirmPassword) {
        log.traceEntry("Entering changePassword for email: {}", email);

        User user = userRepo.findByemail(email);

        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepo.save(user);
        log.traceExit("Exiting changePassword for email: {}", email);

    }


    public boolean validateToken(String token, String email) {
        log.traceEntry("Entering validateToken with token: {}, email: {}", token, email);
        User user = userRepo.findByemail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        boolean isValid = jwtUtil.isTokenValid(token, user);
        log.traceExit("Exiting validateToken with isValid: {}", isValid);
        return isValid;
    }

    public void logout(String token) {
        log.traceEntry("Entering logout with token: {}", token);
        jwtUtil.revokeToken(token);
        log.traceExit("Exiting logout");    }

    public boolean isTokenValidForLogout(String token) {
        log.traceEntry("Entering isTokenValidForLogout with token: {}", token);
        JwtToken jwtToken = jwtRepo.findByToken(token).orElse(null);

        boolean isValid = jwtToken != null && !jwtToken.isRevoked() && !jwtToken.isExpired();
        log.traceExit("Exiting isTokenValidForLogout with isValid: {}", isValid);
        return isValid;

    }

    public List<User> getAllUsers() {
        log.traceEntry("Entering getAllUsers");
        var users = userRepo.findAll();
        log.traceExit("Exiting getAllUsers with users: {}", users);
        return users;
    }

    public Optional<User> getUserById(Long id) {
        log.traceEntry("Entering getUserById with id: {}", id);
        var user = userRepo.findById(id);
        log.traceExit("Exiting getUserById with user: {}", user);
        return user;
    }


    public void updateUser(Long id, UpdateUser updateUser) {
        log.traceEntry("Entering updateUser with id: {}, updateUser: {}", id, updateUser);
        Optional<User> existingUser = userRepo.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (updateUser.getUserName() != null && !updateUser.getUserName().isEmpty()) {
                user.setUsername(updateUser.getUserName());
            }
            if (updateUser.getEmail() != null && !updateUser.getEmail().isEmpty()) {
                user.setEmail(updateUser.getEmail());
            }
            userRepo.save(user);
        } else {
            log.error("User not found with ID: {}", id);
            throw new RuntimeException("User not found with ID: " + id);
        }
        log.traceExit("Exiting updateUser for id: {}", id);
    }
}
