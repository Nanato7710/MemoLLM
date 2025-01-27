package jp.ac.uryukyu.ie.e245735;

public class Main {
    public static void main(String[] args) {
        Setting setting = new Setting();
        ChatTemplate chatTemplate = new ChatTemplate(setting.getChatTemplateParams(), setting.getSystemPrompt(), setting.getStructuringPrompt(), setting.getSearchPrompt());
        LLM llm = new LLM(setting.getLLMParams());
        new ChatGUI(chatTemplate, llm, setting.getMemoParams());
    }
}
