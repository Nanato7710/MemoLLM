package jp.ac.uryukyu.ie.e245735.params;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LlmParametersTest {
    @Test
    void getModelIdTest() {
        String modelId = "modelId";
        LlmParameters params = new LlmParameters(modelId, "url", 0.0f, "stopString", 0, "modelDir");
        assertEquals(modelId, params.getModelId());
    }

    @Test
    void getUrlTest() {
        String url = "url";
        LlmParameters params = new LlmParameters("modelId", url, 0.0f, "stopString", 0, "modelDir");
        assertEquals(url, params.getUrl());
    }

    @Test
    void getTemperatureTest() {
        float temperature = 0.0f;
        LlmParameters params = new LlmParameters("modelId", "url", temperature, "stopString", 0, "modelDir");
        assertEquals(temperature, params.getTemperature());
    }

    @Test
    void getStopStringTest() {
        String stopString = "stopString";
        LlmParameters params = new LlmParameters("modelId", "url", 0.0f, stopString, 0, "modelDir");
        assertEquals(stopString, params.getStopString());
    }

    @Test
    void getGpuLayerCountTest() {
        int gpuLayerCount = 0;
        LlmParameters params = new LlmParameters("modelId", "url", 0.0f, "stopString", gpuLayerCount, "modelDir");
        assertEquals(gpuLayerCount, params.getGpuLayerCount());
    }
}
