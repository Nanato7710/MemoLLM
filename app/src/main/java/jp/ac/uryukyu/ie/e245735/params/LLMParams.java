package jp.ac.uryukyu.ie.e245735.params;

public class LLMParams {
    private String id;
    private String url;
    private double temperature;
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

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
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
}
