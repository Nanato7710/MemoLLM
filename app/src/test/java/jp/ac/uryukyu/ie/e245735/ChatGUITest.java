package jp.ac.uryukyu.ie.e245735;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import jp.ac.uryukyu.ie.e245735.params.LlmParameters;
import jp.ac.uryukyu.ie.e245735.params.MemoParameters;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChatGUITest {
    private ChatTemplate chatTemp;
    private Llm llm;
    private ChatGUI chatGUI;

    @BeforeAll
    void setUp() throws URISyntaxException {
        AppSetting setting = new AppSetting(getClass().getResource("/").getPath());
        chatTemp = new ChatTemplate(setting.getChatTemplateParams(), setting.getSystemPrompt(), setting.getStructuringPrompt(), setting.getSearchPrompt());
        LlmParameters llmParams = new LlmParameters("smollm2-1.7b-instruct-q4_k_m", "https://huggingface.co/HuggingFaceTB/SmolLM2-1.7B-Instruct-GGUF/resolve/main/smollm2-1.7b-instruct-q4_k_m.gguf", 0.0f, "<|im_end|>", 43, getClass().getResource("/models").getPath());
        llm = new Llm(llmParams);
        MemoParameters memoParams = new MemoParameters("memo.md", getClass().getResource("/").getPath());
        chatGUI = new ChatGUI(chatTemp, llm, memoParams);
    }

    @Test
    void getLLMResponseTest() {
        try {
            Method getLLMResponse = chatGUI.getClass().getDeclaredMethod("getLLMResponse", String.class);
            getLLMResponse.setAccessible(true);
            assertInstanceOf(String.class, getLLMResponse.invoke(chatGUI, "Hello"));
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}