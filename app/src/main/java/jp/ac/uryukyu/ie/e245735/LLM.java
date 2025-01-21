package jp.ac.uryukyu.ie.e245735;

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
        ModelParameters modelParams = new ModelParameters()
                                        .setModelFilePath(params.getModelPath())
                                        .setNGpuLayers(params.getNGpulayers());
        model = new LlamaModel(modelParams);
    }

    public LlamaIterable generate(String prompt) {
        InferenceParameters infParams = new InferenceParameters(prompt)
                                            .setTemperature(params.getTemperature())
                                            .setStopStrings(params.getStopString())
                                            .setPenalizeNl(true)
                                            .setMiroStat(MiroStat.V2);
        return model.generate(infParams);
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
