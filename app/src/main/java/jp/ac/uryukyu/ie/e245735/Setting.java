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
import jp.ac.uryukyu.ie.e245735.params.MemoParams;

public class Setting {
    public static final String sep = System.getProperty("file.separator");
    public static final String home = System.getProperty("user.home");
    public static final String workingDir = home + sep + ".memollm";

    private ChatTemplateParams chatTemplateParams;
    private LLMParams llmParams;
    private MemoParams memoParams;
    private String systemPrompt;
    private String structuringPrompt;

    public Setting() {
        // ディレクトリが存在しない場合は作成
        File dir = new File(workingDir);
        if (!dir.exists()) {
            dir.mkdir();
            File modelDir = new File(workingDir + sep + "models");
            modelDir.mkdir();
            makeSetting();
            makeSystemPromptFile();
            loadSettings();
            try {
                Downloader.downloadFileWithProgress(llmParams.getUrl(), modelDir.toString());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            loadSettings();
        }
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
                memoParams = gson.fromJson(json.get("MemoParams"), MemoParams.class);
                systemPrompt = loadPrompt("SystemPrompt.md");
                structuringPrompt = loadPrompt("StructuringPrompt.md");
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

    private String loadPrompt(String fileName) {
        // システムプロンプトファイルを読み込む
        File file = new File(workingDir + sep + fileName);
        if (file.exists()) {
            return readFile(file);
        }
        return "";
    }

    public String getSystemPrompt() {
        if (systemPrompt == null) {
            systemPrompt = loadPrompt("SystemPrompt.md");
        }
        return systemPrompt;
    }

    public String getStructuringPrompt() {
        if (structuringPrompt == null) {
            structuringPrompt = loadPrompt("StructuringPrompt.md");
        }
        return structuringPrompt;
    }

    public MemoParams getMemoParams() {
        return memoParams;
    }

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
