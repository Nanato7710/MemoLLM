package jp.ac.uryukyu.ie.e245735;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jp.ac.uryukyu.ie.e245735.params.ChatTemplateParameters;
import jp.ac.uryukyu.ie.e245735.params.LlmParameters;
import jp.ac.uryukyu.ie.e245735.params.MemoParameters;

/**
 * AppSetting
 * 
 * アプリケーションの設定を保持するクラス
 * 
 * @param sep pathのセパレータ
 * @param home ユーザーディレクトリ
 * @param workingDir 作業ディレクトリ
 * @param chatTemplateParams チャットテンプレートのパラメータ
 * @param llmParams LLMのパラメータ
 * @param memoParams メモのパラメータ
 * @param systemPrompt システムプロンプト
 * @param structuringPrompt 構造化プロンプト
 * @param searchPrompt 検索プロンプト
 */
public class AppSetting {
    public static final String sep = System.getProperty("file.separator");
    public static final String home = System.getProperty("user.home");
    public static final String workingDir = home + sep + ".memollm";

    private ChatTemplateParameters chatTemplateParams;
    private LlmParameters llmParams;
    private MemoParameters memoParams;
    private String systemPrompt;
    private String structuringPrompt;
    private String searchPrompt;

    public AppSetting() {
        initialize();
    }

    /**
     * 初期化処理
     * 
     * workingDirが存在しない場合は作成し、設定ファイルを読み込む
     * システムプロンプトファイルが存在しない場合は作成
     * モデルファイルが存在しない場合はダウンロード
     */
    private void initialize() {
        File dir = new File(workingDir);
        if (!dir.exists()) {
            createWorkingDirs(dir);
        } else {
            loadSettings();
        }
    }

    /**
     * 作業ディレクトリを作成し、設定ファイルを読み込む
     * 
     * @param dir 作業ディレクトリ
     */
    private void createWorkingDirs(File dir) {
        dir.mkdir();
        File modelDir = new File(workingDir + sep + "models");
        modelDir.mkdir();
        makeSetting();
        for (String fileName : new String[] {"SystemPrompt.md", "StructuringPrompt.md", "SearchPrompt.md"}) {
            makePromptFile(fileName);
        }
        loadSettings();
        downloadModelIfNeeded(modelDir);
    }

    /**
     * モデルファイルをダウンロードする
     * 
     * @param modelDir モデルファイルのディレクトリ
     */
    private void downloadModelIfNeeded(File modelDir) {
        try {
            Downloader.downloadFileWithProgress(llmParams.getUrl(), modelDir.toString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 設定ファイルを作成する
     */
    private void makeSetting() {
        File file = new File(workingDir + sep + "Settings.json");
        if (!file.exists()) {
            try (InputStream is = getClass().getResourceAsStream(sep+"Settings.json");
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(bis));
            FileWriter fw = new FileWriter(file)) {
                String line;
                while ((line = br.readLine()) != null) {
                    fw.write(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 設定ファイルを読み込む
     */
    private void loadSettings() {
        File file = new File(workingDir + sep + "Settings.json");
        if (file.exists()) {
            try (FileReader fr = new FileReader(file)) {
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(fr, JsonObject.class);
                chatTemplateParams = gson.fromJson(json.get("ChatTemplateParams"), ChatTemplateParameters.class);
                llmParams = gson.fromJson(json.get("LLMParams"), LlmParameters.class);
                memoParams = gson.fromJson(json.get("MemoParams"), MemoParameters.class);
                systemPrompt = loadPrompt("SystemPrompt.md");
                structuringPrompt = loadPrompt("StructuringPrompt.md");
                searchPrompt = loadPrompt("SearchPrompt.md");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * チャットテンプレートのパラメータを取得する
     * 
     * @return chatTemplateParams チャットテンプレートのパラメータ
     */
    public ChatTemplateParameters getChatTemplateParams() {
        return chatTemplateParams;
    }

    /**
     * LLMのパラメータを取得する
     * 
     * @return llmParams LLMのパラメータ
     */
    public LlmParameters getLLMParams() {
        return llmParams;
    }

    /**
     * ファイルを作成する
     */
    private void makePromptFile(String fileName) {
        File file = new File(workingDir + sep + fileName);
        if (!file.exists()) {
            try (InputStream is = getClass().getResourceAsStream(sep + fileName);
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(bis));
            FileWriter fw = new FileWriter(file)) {
                String line;
                while ((line = br.readLine()) != null) {
                    fw.write(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * プロンプトを読み込む
     * 
     * @param fileName ファイル名
     * 
     * @return プロンプト
     */
    private String loadPrompt(String fileName) {
        File file = new File(workingDir + sep + fileName);
        if (file.exists()) {
            return readFile(file);
        } else {
            makePromptFile(fileName);
            return loadPrompt(fileName);
        }
    }

    /**
     * システムプロンプトを取得する
     * 
     * @return システムプロンプト
     */
    public String getSystemPrompt() {
        if (systemPrompt == null) {
            systemPrompt = loadPrompt("SystemPrompt.md");
        }
        return systemPrompt;
    }

    /**
     * 構造化プロンプトを取得する
     * 
     * @return 構造化プロンプト
     */
    public String getStructuringPrompt() {
        if (structuringPrompt == null) {
            structuringPrompt = loadPrompt("StructuringPrompt.md");
        }
        return structuringPrompt;
    }

    /**
     * メモのパラメータを取得する
     * 
     * @return memoParams メモのパラメータ
     */
    public MemoParameters getMemoParams() {
        return memoParams;
    }

    /**
     * 検索プロンプトを取得する
     * 
     * @return 検索プロンプト
     */
    public String getSearchPrompt() {
        if (searchPrompt == null) {
            searchPrompt = loadPrompt("SearchPrompt.md");
        }
        return searchPrompt;
    }

    /**
     * ファイルを読み込む
     * 
     * @param file ファイル
     * 
     * @return ファイルの内容
     */
    public static String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}