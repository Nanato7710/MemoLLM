package jp.ac.uryukyu.ie.e245735.params;

import java.io.File;

import jp.ac.uryukyu.ie.e245735.Setting;

public class MemoParams {
    private String fileName;
    private String contents;

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return Setting.workingDir + Setting.sep + fileName;
    }

    public String getContents() {
        if (contents == null) {
            File file = new File(getFilePath());
            if (file.exists()) {
                contents = Setting.readFile(file);
            }
        }
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
