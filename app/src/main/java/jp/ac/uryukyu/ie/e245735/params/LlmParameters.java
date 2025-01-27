package jp.ac.uryukyu.ie.e245735.params;

import jp.ac.uryukyu.ie.e245735.AppSetting;

public class LlmParameters {
    private String modelId;
    private String url;
    private float temperature;
    private String stopString;
    private int gpuLayerCount;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getStopString() {
        return stopString;
    }

    public void setStopString(String stopString) {
        this.stopString = stopString;
    }

    public int getGpuLayerCount() {
        return gpuLayerCount;
    }

    public void setGpuLayerCount(int gpuLayerCount) {
        this.gpuLayerCount = gpuLayerCount;
    }

    public String getModelPath() {
        return buildModelPath();
    }

    private String buildModelPath() {
        return AppSetting.workingDir + "/models/" + modelId + ".gguf";
    }
}