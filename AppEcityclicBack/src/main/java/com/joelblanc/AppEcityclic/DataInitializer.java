package com.joelblanc.AppEcityclic;

import com.joelblanc.AppEcityclic.entity.Empresa;
import com.joelblanc.AppEcityclic.entity.UserEntity;
import com.joelblanc.AppEcityclic.repository.UserRepository;
import com.joelblanc.AppEcityclic.service.EmpresaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(EmpresaService empresaService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (empresaService.findAll().isEmpty()) {
                Empresa empresa1 = new Empresa();
                empresa1.setNom("Empresa 1");
                empresa1.setAdreca("Dirección 1");
                empresa1.setCif("N1234567J");

                Empresa empresa2 = new Empresa();
                empresa2.setNom("Empresa 2");
                empresa2.setAdreca("Dirección 2");
                empresa2.setCif("W1234567H");


                empresaService.save(empresa1);
                empresaService.save(empresa2);
            }

            if (userRepository.findAll().isEmpty()) {
                // Crear un usuari
                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("password"));

                userRepository.save(admin);
            }
        };
    }
}