package com.kaansrflioglu.pe.config;

import com.kaansrflioglu.pe.model.Sports;
import com.kaansrflioglu.pe.repository.SportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SportsDataSeeder implements CommandLineRunner {

    private final SportsRepository sportsRepository;

    @Override
    public void run(String... args) {
        List<String> names = List.of(
                "Futbol", "Basketbol", "Voleybol", "Hentbol", "Yüzme",
                "Koşu", "Tenis", "Masa Tenisi", "Badminton", "Okçuluk",
                "Güreş", "Karate", "Taekwondo", "Boks", "Bisiklet"
        );

        for (String name : names) {
            if (!sportsRepository.existsByName(name)) {
                sportsRepository.save(Sports.builder().name(name).build());
            }
        }
    }
}
