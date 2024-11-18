package com.example.Banking.application.authentication;

import com.example.Banking.application.accountManagement.AccountCreation;
import com.example.Banking.application.accountManagement.AccountCreationRepo;
import com.example.Banking.application.transactionManagement.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
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

        return saveUser;
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i <= 10; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    public User findbyEmail(String email) {
        return userRepo.findByemail(email);
    }

    public String login(String email, String password) {
        User user = userRepo.findByemail(email);
        if (user == null || !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(user);

    }

    public void changePassword(String email, String newPassword, String confirmPassword) {
        User user = userRepo.findByemail(email);

        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepo.save(user);

    }


    public boolean validateToken(String token, String email) {
        User user = userRepo.findByemail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return jwtUtil.isTokenValid(token, user);
    }

    public void logout(String token) {
        jwtUtil.revokeToken(token);
    }

    public boolean isTokenValidForLogout(String token) {
        JwtToken jwtToken = jwtRepo.findByToken(token).orElse(null);

        if (jwtToken == null || jwtToken.isRevoked() || jwtToken.isExpired()) {
            return false;
        }

        return true;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }


    public void updateUser(Long id, UpdateUser updateUser) {
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
            throw new RuntimeException("User not found with ID: " + id);
        }

    }
}

