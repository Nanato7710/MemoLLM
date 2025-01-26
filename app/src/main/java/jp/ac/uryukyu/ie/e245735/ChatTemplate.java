package jp.ac.uryukyu.ie.e245735;

import java.util.ArrayList;

import jp.ac.uryukyu.ie.e245735.params.ChatTemplateParams;

public class ChatTemplate {
    private ChatTemplateParams params;
    private ArrayList<String> chatHistory = new ArrayList<>();
    private ArrayList<String> chatHistoryForUser = new ArrayList<>();
    private String systemPrompt;
    private String structuringPrompt;

    public ChatTemplate(ChatTemplateParams params, String systemPrompt, String structuringPrompt) {
        this.params = params;
        this.systemPrompt = systemPrompt;
        this.structuringPrompt = structuringPrompt;
    }

    public void addUserChat(String chat) {
        chatHistory.add(String.format(params.getUser(), chat));
        chatHistoryForUser.add("You: " + chat);
    }

    public void addAssistantChat(String chat) {
        chatHistory.add(String.format(params.getAssistant(), chat));
        chatHistoryForUser.add("Assistant: " + chat);
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

    public String convertToInput(String structuredMemo) {
        return toString() + String.format("<|start_header_id|>information<|end_header_id|>\\n%s<|eot_id|>",structuredMemo) + params.getSuffix();
    }

    public String getChatHistoryForUser() {
        StringBuilder sb = new StringBuilder();
        for (String chat : chatHistoryForUser) {
            sb.append(chat + "\n");
        }
        return sb.toString();
    }

    public String getPromptForStructuringMemo(String memo) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(params.getSystem(), structuringPrompt));
        sb.append(String.format(params.getUser(), memo));
        return sb.toString();
    }
}
