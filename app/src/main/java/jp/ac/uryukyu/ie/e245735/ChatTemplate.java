package jp.ac.uryukyu.ie.e245735;

import java.util.ArrayList;

import jp.ac.uryukyu.ie.e245735.params.ChatTemplateParams;

public class ChatTemplate {
    private ChatTemplateParams params;
    private ArrayList<String> chatHistory = new ArrayList<>();
    private ArrayList<String> chatHistoryForUser = new ArrayList<>();
    private String systemPrompt;
    private String structuringPrompt;
    private String searchPrompt;

    public ChatTemplate(ChatTemplateParams params, String systemPrompt, String structuringPrompt, String searchPrompt) {
        this.params = params;
        this.systemPrompt = systemPrompt;
        this.structuringPrompt = structuringPrompt;
        this.searchPrompt = searchPrompt;
    }

    public void addUserChat(String chat) {
        String formatted = formatUserChat(chat);
        chatHistory.add(formatted);
        chatHistoryForUser.add("You: " + chat);
    }

    public void addAssistantChat(String chat) {
        String formatted = formatAssistantChat(chat);
        chatHistory.add(formatted);
        chatHistoryForUser.add("Assistant: " + chat);
    }

    private String formatUserChat(String chat) {
        return String.format(params.getUser(), chat);
    }

    private String formatAssistantChat(String chat) {
        return String.format(params.getAssistant(), chat);
    }

    @Override
    public String toString() {
        return buildSystemPrompt() + buildChatHistory();
    }

    private String buildSystemPrompt() {
        return String.format(params.getSystem(), systemPrompt);
    }

    private String buildChatHistory() {
        StringBuilder sb = new StringBuilder();
        for (String chat : chatHistory) {
            sb.append(chat);
        }
        return sb.toString();
    }

    public String convertToInput(String structuredMemo) {
        return toString() + String.format("<|start_header_id|>information<|end_header_id|>\\n%s<|eot_id|>", structuredMemo)
                + params.getSuffix();
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
        return sb.toString()+params.getSuffix();
    }

    public String getPromptForSearchMemo(String structuredMemo, String query) {
        StringBuilder sb = new StringBuilder();
        String input = String.format("```Query\n%s\n```\n\n```DataBase\n%s\n```", query, structuredMemo);
        sb.append(String.format(params.getSystem(), searchPrompt));
        sb.append(String.format(params.getUser(), input));
        return sb.toString()+params.getSuffix();
    } 
}
