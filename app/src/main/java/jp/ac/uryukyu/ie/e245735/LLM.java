package jp.ac.uryukyu.ie.e245735;

import java.io.File;
import java.io.IOException;

import de.kherud.llama.InferenceParameters;
import de.kherud.llama.LlamaIterable;
import de.kherud.llama.LlamaModel;
import de.kherud.llama.ModelParameters;
import de.kherud.llama.args.MiroStat;
import jp.ac.uryukyu.ie.e245735.params.LLMParams;

public class LLM {
    private LLMParams params;
    private LlamaModel model;

    public LLM(LLMParams params) {
        this.params = params;
        ensureModelDownloaded();
        initModel();
    }

    private void ensureModelDownloaded() {
        File modelFile = new File(params.getModelPath());
        if (!modelFile.exists()) {
            try {
                Downloader.downloadFileWithProgress(params.getUrl(), Setting.workingDir + Setting.sep + "models");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initModel() {
        ModelParameters modelParams = new ModelParameters()
                .setModelFilePath(params.getModelPath())
                .setNGpuLayers(params.getNGpulayers());
        model = new LlamaModel(modelParams);
    }

    public LlamaIterable generate(String prompt) {
        InferenceParameters infParams = createInferenceParams(prompt);
        return model.generate(infParams);
    }

    private InferenceParameters createInferenceParams(String prompt) {
        return new InferenceParameters(prompt)
                .setTemperature(params.getTemperature())
                .setStopStrings(params.getStopString())
                .setPenalizeNl(true)
                .setMiroStat(MiroStat.V2);
    }

    public void close() {
        model.close();
    }

    public LLMParams getParams() {
        return params;
    }

    public void setParams(LLMParams params) {
        this.params = params;
    }
}
