package jp.ac.uryukyu.ie.e245735;

import java.io.File;
import java.io.IOException;

import de.kherud.llama.InferenceParameters;
import de.kherud.llama.LlamaIterable;
import de.kherud.llama.LlamaModel;
import de.kherud.llama.ModelParameters;
import de.kherud.llama.args.MiroStat;
import jp.ac.uryukyu.ie.e245735.params.LlmParameters;

/**
 * Llm
 * 
 * LLMを利用するクラス
 * 
 * @param params LlmParameters
 * @param model LlamaModel
 */
public class Llm {
    private LlmParameters params;
    private LlamaModel model;

    public Llm(LlmParameters params) {
        this.params = params;
        downloadModelIfAbsent();
        initializeModel();
    }

    /**
     * モデルファイルが存在しない場合はダウンロード
     */
    private void downloadModelIfAbsent() {
        File modelFile = new File(params.getModelPath());
        if (!modelFile.exists()) {
            try {
                Downloader.downloadFileWithProgress(params.getUrl(), AppSetting.workingDir + AppSetting.sep + "models");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * モデルを初期化
     */
    private void initializeModel() {
        ModelParameters modelParams = new ModelParameters()
                .setModelFilePath(params.getModelPath())
                .setNGpuLayers(params.getGpuLayerCount());
        model = new LlamaModel(modelParams);
    }

    /**
     * テキストを生成
     * 
     * @param prompt プロンプト
     * @return LlamaIterable
     */
    public LlamaIterable generate(String prompt) {
        InferenceParameters infParams = createInferenceParams(prompt);
        return model.generate(infParams);
    }

    /**
     * テキストを生成するためのLLMのパラメータを作成
     * 
     * @param prompt プロンプト
     * @return LlamaIterable
     */
    private InferenceParameters createInferenceParams(String prompt) {
        return new InferenceParameters(prompt)
                .setTemperature(params.getTemperature())
                .setStopStrings(params.getStopString())
                .setPenalizeNl(true)
                .setMiroStat(MiroStat.V2);
    }

    /**
     * モデルをクローズ
     */
    public void close() {
        model.close();
    }

    /**
     * パラメータを取得する
     * 
     * @return LlmParameters
     */
    public LlmParameters getParams() {
        return params;
    }

    /**
     * パラメータを設定する
     * 
     * @param params LlmParameters
     */
    public void setParams(LlmParameters params) {
        this.params = params;
    }
}
