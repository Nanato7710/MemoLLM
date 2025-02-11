package jp.ac.uryukyu.ie.e245735;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.io.File;

import org.junit.jupiter.api.Test;

import de.kherud.llama.LlamaIterable;
import jp.ac.uryukyu.ie.e245735.params.LlmParameters;

public class LlmTest {
    @Test
    void generateTest() {
        String modelDir = getClass().getResource("/models").getPath();
        File dir = new File(modelDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        LlmParameters params = new LlmParameters("smollm2-1.7b-instruct-q4_k_m", "https://huggingface.co/HuggingFaceTB/SmolLM2-1.7B-Instruct-GGUF/resolve/main/smollm2-1.7b-instruct-q4_k_m.gguf", 0.0f, "You:", 43, modelDir);
        Llm llm = new Llm(params);
        String prompt = "You: Hello, how are you?\nAssistant: I'm fine, thank you. I'm a helpful and honest assistant. How can I help you today?\nYou: Your name is Assistant? Answer with yes or no.\nAssistant: ";
        assertInstanceOf(LlamaIterable.class, llm.generate(prompt));
    }
}
