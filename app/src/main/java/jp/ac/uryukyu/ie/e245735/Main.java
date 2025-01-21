package jp.ac.uryukyu.ie.e245735;

public class Main {
    public static void main(String[] args) {
        Setting setting = new Setting();
        setting.init();
        ChatTemplate chatTemplate = new ChatTemplate(setting.getChatTemplateParams(), setting.getSystemPrompt());
        LLM llm = new LLM(setting.getLLMParams());
    }
}
