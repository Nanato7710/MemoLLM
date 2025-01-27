package jp.ac.uryukyu.ie.e245735.params;


/**
 * ChatTemplateParameters
 * 
 * チャットテンプレートのパラメータを保持するクラス
 * 
 * @param system システムプロンプトの入力フォーマット
 * @param user ユーザーの入力フォーマット
 * @param assistant アシスタントの出力フォーマット
 * @param suffix チャットテンプレートのファイル名の接尾辞
 */
public class ChatTemplateParameters {
    private String system;
    private String user;
    private String assistant;
    private String suffix;

    public ChatTemplateParameters(String system, String user, String assistant, String suffix) {
        this.system = system;
        this.user = user;
        this.assistant = assistant;
        this.suffix = suffix;
    }

    /**
     * システムプロンプトの入力フォーマットを取得する
     * 
     * @return システムプロンプトの入力フォーマット
     */
    public String getSystem() {
        return system;
    }

    /**
     * システムプロンプトの入力フォーマットを設定する
     * 
     * @param system システムプロンプトの入力フォーマット
     */
    public void setSystem(String system) {
        this.system = system;
    }

    /**
     * ユーザーの入力フォーマットを取得する
     * 
     * @return ユーザーの入力フォーマット
     */
    public String getUser() {
        return user;
    }

    /**
     * ユーザーの入力フォーマットを設定する
     * 
     * @param user ユーザーの入力フォーマット
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * アシスタントの出力フォーマットを取得する
     * 
     * @return アシスタントの出力フォーマット
     */
    public String getAssistant() {
        return assistant;
    }

    /**
     * アシスタントの出力フォーマットを設定する
     * 
     * @param assistant アシスタントの出力フォーマット
     */
    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    /**
     * チャットテンプレートのファイル名の接尾辞を取得する
     * 
     * @return チャットテンプレートのファイル名の接尾辞
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * チャットテンプレートのファイル名の接尾辞を設定する
     * 
     * @param suffix チャットテンプレートのファイル名の接尾辞
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}