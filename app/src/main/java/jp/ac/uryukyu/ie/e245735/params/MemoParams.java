package jp.ac.uryukyu.ie.e245735.params;

import jp.ac.uryukyu.ie.e245735.Setting;

public class MemoParams {
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return Setting.workingDir + Setting.sep + fileName;
    }
}
