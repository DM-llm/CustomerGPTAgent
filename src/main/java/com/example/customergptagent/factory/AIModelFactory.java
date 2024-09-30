package com.example.customergptagent.factory;

import com.example.customergptagent.service.AIModelService;
import com.example.customergptagent.service.GPTService;
import com.example.customergptagent.service.OtherAIModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AIModelFactory {

    @Autowired
    private GPTService gptService;

    @Autowired
    private OtherAIModelService otherAIModelService;

    public AIModelService getAIModel(String modelType) {
        switch (modelType.toLowerCase()) {
            case "gpt":
                return gptService;
            case "other":
                return otherAIModelService;
            default:
                throw new IllegalArgumentException("Unknown model type: " + modelType);
        }
    }
}
