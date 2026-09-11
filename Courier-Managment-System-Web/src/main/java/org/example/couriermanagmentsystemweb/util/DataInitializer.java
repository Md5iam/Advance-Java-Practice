package org.example.couriermanagmentsystemweb.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.couriermanagmentsystemweb.entity.Role;
import org.example.couriermanagmentsystemweb.entity.User;
import org.example.couriermanagmentsystemweb.enums.RoleType;
import org.example.couriermanagmentsystemweb.repository.RoleRepository;
import org.example.couriermanagmentsystemweb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        for (RoleType roleType : RoleType.values()) {
            if (roleRepository.findByName(roleType).isEmpty()) {
                roleRepository.save(new Role(roleType));
                log.info("Initialized role: {}", roleType);
            }
        }

        if (userRepository.findByEmail("admin@courier.com").isEmpty()) {
            User admin = new User();
            admin.setFullName("System Admin");
            admin.setEmail("admin@courier.com");
            admin.setPhone("01700000000");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);

            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN).orElseThrow();
            admin.getRoles().add(adminRole);

            userRepository.save(admin);
            log.info("Initialized default Admin account: admin@courier.com / admin123");
        }
    }
}
