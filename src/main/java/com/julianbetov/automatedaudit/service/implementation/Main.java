package com.julianbetov.automatedaudit.service.implementation;

import com.julianbetov.automatedaudit.model.Registry;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        AIService aiService = new AIService(null);
        List<Registry> registries = aiService.createRegistries();
        System.out.println(aiService.queryToAI(registries));

    }
}
