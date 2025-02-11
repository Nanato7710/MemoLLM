package jp.ac.uryukyu.ie.e245735;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jp.ac.uryukyu.ie.e245735.params.ChatTemplateParameters;

public class ChatTemplateTest {
    @Test
    void getPromptForStructuringMemoTest() {
        ChatTemplateParameters params = new ChatTemplateParameters("system:%s", "user:%s", "assistant:%s", "suffix");
        ChatTemplate chatTemplate = new ChatTemplate(params, "systemPrompt", "structuringPrompt", "searchPrompt");
        String excepted = "system:structuringPromptuser:memosuffix";
        String actual = chatTemplate.getPromptForStructuringMemo("memo");
        assertEquals(excepted, actual);
    }
}
