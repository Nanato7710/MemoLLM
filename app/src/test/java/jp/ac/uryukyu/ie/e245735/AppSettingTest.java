package jp.ac.uryukyu.ie.e245735;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.Test;

public class AppSettingTest {
    @Test
    void readFileTest() {
        String filePath = getClass().getResource(".").getPath()+"test.txt";
        String contents = "test\n";
        File file = new File(filePath);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(contents);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(contents, AppSetting.readFile(file));
        file.delete();
    }
}
