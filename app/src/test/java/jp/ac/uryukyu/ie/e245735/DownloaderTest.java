package jp.ac.uryukyu.ie.e245735;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class DownloaderTest {
    @Test
    void downloadFileWithProgressTest() {
        String url = "https://www.eicar.org/download/eicar.com";
        String saveDir = getClass().getResource("/").getPath();
        try {
            Downloader.downloadFileWithProgress(url, saveDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File file = new File(saveDir + AppSetting.sep + "eicar.com");
        assertTrue(file.exists());
        file.delete();
    }
}
