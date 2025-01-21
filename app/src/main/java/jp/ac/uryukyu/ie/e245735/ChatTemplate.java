package jp.ac.uryukyu.ie.e245735;

import java.util.ArrayList;

import jp.ac.uryukyu.ie.e245735.params.ChatTemplateParams;

public class ChatTemplate {
    private ChatTemplateParams params;
    private ArrayList<String> chatHistory = new ArrayList<>();
    private String systemPrompt;

    public ChatTemplate(ChatTemplateParams params, String systemPrompt) {
        this.params = params;
        this.systemPrompt = systemPrompt;
    }

    public void addUserChat(String chat) {
        chatHistory.add(String.format(params.getUser(), chat));
    }

    public void addAssistantChat(String chat) {
        chatHistory.add(String.format(params.getAssistant(), chat));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(params.getSystem(), systemPrompt));
        for (String chat : chatHistory) {
            sb.append(chat);
        }
        return sb.toString();
    }

    public String convertToInput() {
            return toString() + params.getSuffix();
    }
}
