package jp.ac.uryukyu.ie.e245735.params;

import java.io.File;

import jp.ac.uryukyu.ie.e245735.AppSetting;

public class MemoParameters {
    private String fileName;
    private String contents;

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return AppSetting.workingDir + AppSetting.sep + fileName;
    }

    public String getContents() {
        loadContentsIfNeeded();
        return contents;
    }

    private void loadContentsIfNeeded() {
        if (contents == null) {
            File file = new File(getFilePath());
            if (file.exists()) {
                contents = AppSetting.readFile(file);
            }
        }
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}