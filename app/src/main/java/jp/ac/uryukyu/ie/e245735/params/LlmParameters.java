package jp.ac.uryukyu.ie.e245735.params;

import jp.ac.uryukyu.ie.e245735.AppSetting;

/**
 * LlmParameters
 * 
 * LLMのパラメータを保持するクラス
 * 
 * @param modelId モデルID
 * @param url モデルのURL
 * @param temperature 生成時のtemperature
 * @param stopString 生成を終了する文字列
 * @param gpuLayerCount GPUのレイヤー数
 * @param modelDir モデルのディレクトリ
 */
public class LlmParameters {
    private String modelId;
    private String url;
    private float temperature;
    private String stopString;
    private int gpuLayerCount;
    private String modelDir;

    public LlmParameters(String modelId, String url, float temperature, String stopString, int gpuLayerCount, String modelDir) {
        this.modelId = modelId;
        this.url = url;
        this.temperature = temperature;
        this.stopString = stopString;
        this.gpuLayerCount = gpuLayerCount;
        this.modelDir = modelDir;
    }

    /**
     * モデルIDを取得する
     * 
     * @return モデルID
     */
    public String getModelId() {
        return modelId;
    }

    /**
     * モデルIDを設定する
     * 
     * @param modelId モデルID
     */
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    /**
     * モデルのURLを取得する
     * 
     * @return モデルのURL
     */
    public String getUrl() {
        return url;
    }

    /**
     * モデルのURLを設定する
     * 
     * @param url モデルのURL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 生成時のtemperatureを取得する
     * 
     * @return 生成時のtemperature
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     * 生成時のtemperatureを設定する
     * 
     * @param temperature 生成時のtemperature
     */
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    /**
     * 生成を終了する文字列を取得する
     * 
     * @return 生成を終了する文字列
     */
    public String getStopString() {
        return stopString;
    }

    /**
     * 生成を終了する文字列を設定する
     * 
     * @param stopString 生成を終了する文字列
     */
    public void setStopString(String stopString) {
        this.stopString = stopString;
    }

    /**
     * GPUのレイヤー数を取得する
     * 
     * @return GPUのレイヤー数
     */
    public int getGpuLayerCount() {
        return gpuLayerCount;
    }

    /**
     * GPUのレイヤー数を設定する
     * 
     * @param gpuLayerCount GPUのレイヤー数
     */
    public void setGpuLayerCount(int gpuLayerCount) {
        this.gpuLayerCount = gpuLayerCount;
    }

    /**
     * モデルのパスを取得する
     * 
     * @return モデルのパス
     */
    public String getModelPath() {
        return modelDir + AppSetting.sep + modelId + ".gguf";
    }
    
    public void setModelDir(String modelDir) {
        this.modelDir = modelDir;
    }

    public String getModelDir() {
        return modelDir;
    }
}