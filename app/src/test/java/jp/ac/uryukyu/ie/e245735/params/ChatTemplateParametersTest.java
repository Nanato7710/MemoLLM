package jp.ac.uryukyu.ie.e245735.params;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ChatTemplateParametersTest {
    @Test
    void getSystemTest() {
        String system = "system";
        ChatTemplateParameters params = new ChatTemplateParameters(system, "user", "assistant", "suffix");
        assertEquals(system, params.getSystem());
    }

    @Test
    void getUserTest() {
        String user = "user";
        ChatTemplateParameters params = new ChatTemplateParameters("system", user, "assistant", "suffix");
        assertEquals(user, params.getUser());
    }

    @Test
    void getAssistantTest() {
        String assistant = "assistant";
        ChatTemplateParameters params = new ChatTemplateParameters("system", "user", assistant, "suffix");
        assertEquals(assistant, params.getAssistant());
    }
}
