package jp.ac.uryukyu.ie.e245735;

import java.util.ArrayList;

import jp.ac.uryukyu.ie.e245735.params.ChatTemplateParameters;

/**
 * ChatTemplate
 * 
 * チャットテンプレートを保持するクラス
 * 
 * @param params チャットテンプレートのパラメータ
 * @param chatHistory チャット履歴
 * @param chatHistoryForUser ユーザー向けのチャット履歴
 * @param systemPrompt システムプロンプト
 * @param structuringPrompt 構造化プロンプト
 * @param searchPrompt 検索プロンプト
 */
public class ChatTemplate {
    private ChatTemplateParameters params;
    private ArrayList<String> chatHistory = new ArrayList<>();
    private ArrayList<String> chatHistoryForUser = new ArrayList<>();
    private String systemPrompt;
    private String structuringPrompt;
    private String searchPrompt;

    public ChatTemplate(ChatTemplateParameters params, String systemPrompt, String structuringPrompt, String searchPrompt) {
        this.params = params;
        this.systemPrompt = systemPrompt;
        this.structuringPrompt = structuringPrompt;
        this.searchPrompt = searchPrompt;
    }

    /**
     * ユーザーの発言を追加する
     * 
     * @param chat ユーザーの発言
     */
    public void addUserChat(String chat) {
        String formatted = formatUserChat(chat);
        chatHistory.add(formatted);
        chatHistoryForUser.add("You: " + chat);
    }

    /**
     * アシスタントの発言を追加する
     * 
     * @param chat アシスタントの発言
     */
    public void addAssistantChat(String chat) {
        String formatted = formatAssistantChat(chat);
        chatHistory.add(formatted);
        chatHistoryForUser.add("Assistant: " + chat);
    }

    /**
     * ユーザーの発言をフォーマットする
     * 
     * @param chat ユーザーの発言
     * @return フォーマットされたユーザーの発言
     */
    private String formatUserChat(String chat) {
        return String.format(params.getUser(), chat);
    }

    /**
     * アシスタントの発言をフォーマットする
     * 
     * @param chat アシスタントの発言
     * @return フォーマットされたアシスタントの発言
     */
    private String formatAssistantChat(String chat) {
        return String.format(params.getAssistant(), chat);
    }

    /**
     * チャットテンプレートを文字列に変換する
     * 
     * @return チャットテンプレートの文字列
     */
    @Override
    public String toString() {
        return buildSystemPrompt() + buildChatHistory();
    }

    /**
     * システムプロンプトを構築する
     * 
     * @return システムプロンプト
     */
    private String buildSystemPrompt() {
        return String.format(params.getSystem(), systemPrompt);
    }

    /**
     * チャット履歴を構築する
     * 
     * @return チャット履歴
     */
    private String buildChatHistory() {
        StringBuilder sb = new StringBuilder();
        for (String chat : chatHistory) {
            sb.append(chat);
        }
        return sb.toString();
    }

    /**
     * 構造化メモを入力に変換する
     * 
     * @param structuredMemo 構造化メモ
     * @return 入力
     */
    public String convertToInput(String structuredMemo) {
        return toString() + String.format("<|start_header_id|>information<|end_header_id|>\\n%s<|eot_id|>", structuredMemo)
                + params.getSuffix();
    }

    /**
     * ユーザー向けのチャット履歴を取得する
     * 
     * @return ユーザー向けのチャット履歴
     */
    public String getChatHistoryForUser() {
        StringBuilder sb = new StringBuilder();
        for (String chat : chatHistoryForUser) {
            sb.append(chat + "\n");
        }
        return sb.toString();
    }

    /**
     * 構造化メモのプロンプトを取得する
     * 
     * @param memo メモ
     * @return 構造化メモのプロンプト
     */
    public String getPromptForStructuringMemo(String memo) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(params.getSystem(), structuringPrompt));
        sb.append(String.format(params.getUser(), memo));
        return sb.toString()+params.getSuffix();
    }

    /**
     * 検索メモのプロンプトを取得する
     * 
     * @param structuredMemo 構造化メモ
     * @param query クエリ
     * @return 検索メモのプロンプト
     */
    public String getPromptForSearchMemo(String structuredMemo, String query) {
        StringBuilder sb = new StringBuilder();
        String input = String.format("```Query\n%s\n```\n\n```DataBase\n%s\n```", query, structuredMemo);
        sb.append(String.format(params.getSystem(), searchPrompt));
        sb.append(String.format(params.getUser(), input));
        return sb.toString()+params.getSuffix();
    } 
}
