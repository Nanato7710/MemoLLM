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

import jp.ac.uryukyu.ie.e245735.params.ChatTemplateParams;
import jp.ac.uryukyu.ie.e245735.params.LLMParams;

public class Setting {
    public static final String sep = System.getProperty("file.separator");
    public static final String home = System.getProperty("user.home");
    public static final String workingDir = home + sep + ".memollm";

    private ChatTemplateParams chatTemplateParams;
    private LLMParams llmParams;

    public void init() {
        // ディレクトリが存在しない場合は作成
        java.io.File dir = new java.io.File(workingDir);
        if (!dir.exists()) {
            dir.mkdir();
            makeSetting();
            makeSystemPromptFile();
        }
        loadSettings();
    }

    private void makeSetting() {
        // 設定ファイルが存在しない場合は作成
        File file = new File(workingDir + sep + "Settings.json");
        if (!file.exists()) {
            try (InputStream is = getClass().getResourceAsStream("/Settings.json");
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

    private void loadSettings() {
        // 設定ファイルを読み込む
        File file = new File(workingDir + sep + "Settings.json");
        if (file.exists()) {
            try (FileReader fr = new FileReader(file)) {
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(fr, JsonObject.class);
                chatTemplateParams = gson.fromJson(json.get("ChatTemplateParams"), ChatTemplateParams.class);
                llmParams = gson.fromJson(json.get("LLMParams"), LLMParams.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ChatTemplateParams getChatTemplateParams() {
        return chatTemplateParams;
    }

    public LLMParams getLLMParams() {
        return llmParams;
    }

    private void makeSystemPromptFile() {
        // システムプロンプトファイルが存在しない場合は作成
        File file = new File(workingDir + sep + "SystemPrompt.md");
        if (!file.exists()) {
            try (InputStream is = getClass().getResourceAsStream("/SystemPrompt.md");
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
}
