package jp.ac.uryukyu.ie.e245735;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChatGUITest {
    private ChatTemplate chatTemp;
    private Llm llm;
    private ChatGUI chatGUI;

    @BeforeAll
    void setUp() throws URISyntaxException {
        AppSetting setting = new AppSetting(getClass().getResource("/").getPath());
        chatTemp = new ChatTemplate(setting.getChatTemplateParams(), setting.getSystemPrompt(), setting.getStructuringPrompt(), setting.getSearchPrompt());
        llm = new Llm(setting.getLLMParams());
        chatGUI = new ChatGUI(chatTemp, llm, setting.getMemoParams());
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