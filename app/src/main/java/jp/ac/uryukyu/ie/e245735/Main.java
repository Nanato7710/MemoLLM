package jp.ac.uryukyu.ie.e245735;

public class Main {
    public static void main(String[] args) {
        AppSetting setting = new AppSetting();
        ChatTemplate chatTemplate = new ChatTemplate(setting.getChatTemplateParams(), setting.getSystemPrompt(), setting.getStructuringPrompt(), setting.getSearchPrompt());
        Llm llm = new Llm(setting.getLLMParams());
        new ChatGUI(chatTemplate, llm, setting.getMemoParams());
    }
}
