package com.geniusee.cinema.event.generatedata;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!unit-test")
@RequiredArgsConstructor
public class GenerateSampleData implements ApplicationListener<ContextRefreshedEvent> {
    private final List<DataGenerator> dataGenerators;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        dataGenerators.forEach(DataGenerator::generate);
    }
}
