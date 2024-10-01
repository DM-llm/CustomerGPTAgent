package com.example.customergptagent.factory;

import com.example.customergptagent.service.GPTService;
import com.example.customergptagent.service.OtherAIModelService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AIModelFactoryTest {

    @Mock
    private GPTService gptService;

    @Mock
    private OtherAIModelService otherAIModelService;

    @InjectMocks
    private AIModelFactory aiModelFactory;

    public AIModelFactoryTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetGPTModel() {
        assertTrue(aiModelFactory.getAIModel("gpt") instanceof GPTService);
    }

    @Test
    public void testGetOtherModel() {
        assertTrue(aiModelFactory.getAIModel("other") instanceof OtherAIModelService);
    }
}
