package com.joelblanc.AppEcityclic;

import com.joelblanc.AppEcityclic.entity.Empresa;
import com.joelblanc.AppEcityclic.service.EmpresaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(EmpresaService empresaService) {
        return args -> {
            Empresa empresa1 = new Empresa();
            empresa1.setNom("Empresa 1");
            empresa1.setAdreca("Dirección 1");
            empresa1.setCif("CIF123");

            Empresa empresa2 = new Empresa();
            empresa2.setNom("Empresa 2");
            empresa2.setAdreca("Dirección 2");
            empresa2.setCif("CIF456");

            empresaService.save(empresa1);
            empresaService.save(empresa2);
        };
    }
}