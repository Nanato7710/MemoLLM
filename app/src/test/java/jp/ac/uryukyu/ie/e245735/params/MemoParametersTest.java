package jp.ac.uryukyu.ie.e245735.params;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jp.ac.uryukyu.ie.e245735.AppSetting;

public class MemoParametersTest {
    @Test
    void getFilePathTest() {
        String fileDir = getClass().getClassLoader().getResource("").getPath();
        String path = fileDir + AppSetting.sep + "test.txt";
        MemoParameters params = new MemoParameters("test.txt", fileDir);
        assertEquals(path, params.getFilePath());
    }

    @Test
    void getContentsTest() {
        String contents = "test";
        MemoParameters params = new MemoParameters("test.txt", "test");
        params.setContents(contents);
        assertEquals(contents, params.getContents());
    }
}
