package jp.ac.uryukyu.ie.e245735.params;

import java.io.File;

import jp.ac.uryukyu.ie.e245735.AppSetting;

/**
 * MemoParameters
 * 
 * メモのパラメータを保持するクラス
 * 
 * @param fileName ファイル名
 * @param contents メモの内容
 */
public class MemoParameters {
    private String fileName;
    private String contents;
    private String fileDir;

    public MemoParameters(String fileName, String fileDir) {
        this.fileName = fileName;
        this.fileDir = fileDir;
    }

    /**
     * ファイル名を取得する
     * 
     * @return ファイル名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * ファイル名を設定する
     * 
     * @param fileName ファイル名
     */
    public String getFilePath() {
        return fileDir + AppSetting.sep + fileName;
    }

    /**
     * ファイルのディレクトリを設定する
     * 
     * @param fileDir ファイルのディレクトリ
     */
    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    /**
     * メモの内容を取得する
     * 
     * @return メモの内容
     */
    public String getContents() {
        loadContentsIfNeeded();
        return contents;
    }

    /**
     * メモの内容をファイルから取得して、contentsにセットする
     * 
     * @param contents メモの内容
     */
    private void loadContentsIfNeeded() {
        if (contents == null) {
            File file = new File(getFilePath());
            if (file.exists()) {
                contents = AppSetting.readFile(file);
            }
        }
    }

    /**
     * メモの内容を設定する
     * 
     * @param contents メモの内容
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}