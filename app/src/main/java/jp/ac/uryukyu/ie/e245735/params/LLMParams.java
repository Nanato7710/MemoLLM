package jp.ac.uryukyu.ie.e245735.params;

import jp.ac.uryukyu.ie.e245735.Setting;

public class LLMParams {
    private String id;
    private String url;
    private float temperature;
    private String stopString;
    private int NGpulayers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getNGpulayers() {
        return NGpulayers;
    }

    public void setNGpulayers(int NGpulayers) {
        this.NGpulayers = NGpulayers;
    }

    public String getModelPath() {
        return Setting.workingDir + "/models/" + id + ".gguf";
    }
}
