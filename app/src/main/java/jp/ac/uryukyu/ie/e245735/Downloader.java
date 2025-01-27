package jp.ac.uryukyu.ie.e245735;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class Downloader {
    public static void downloadFileWithProgress(String fileURL, String saveDir) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileURL))
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        if (response.statusCode() == 200) {
            long contentLength = response.headers()
                    .firstValueAsLong("Content-Length")
                    .orElse(-1);

            String fileName = extractFileName(response.headers().firstValue("Content-Disposition"), fileURL);
            Path savePath = Path.of(saveDir, fileName);

            try (InputStream inputStream = response.body();
                OutputStream outputStream = Files.newOutputStream(savePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

                byte[] buffer = new byte[8192];
                long totalBytesRead = 0;
                int bytesRead;
                long lastReportedProgress = 0;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;

                    if (contentLength > 0) {
                        long progress = (totalBytesRead * 100) / contentLength;
                        if (progress != lastReportedProgress) {
                            System.out.printf("ダウンロード進行中: %d%% (%d/%d バイト)%n", progress, totalBytesRead, contentLength);
                            lastReportedProgress = progress;
                        }
                    } else {
                        System.out.printf("ダウンロード済み: %d バイト%n", totalBytesRead);
                    }
                }
            }

            System.out.println("ファイルが " + savePath + " にダウンロードされました。");
        } else {
            System.out.println("HTTPリクエストに失敗しました。ステータスコード: " + response.statusCode());
        }
    }

    private static String extractFileName(Optional<String> contentDisposition, String fileURL) {
        String fileName = contentDisposition
                .filter(disposition -> disposition.contains("filename="))
                .map(disposition -> {
                    String name = disposition.substring(disposition.indexOf("filename=") + 9).replace("\"", "").trim();
                    // セミコロンや余分な空白を削除
                    if (name.endsWith(";")) {
                        name = name.substring(0, name.length() - 1).trim();
                    }
                    return name;
                })
                .orElseGet(() -> {
                    // URLからファイル名を抽出
                    String path = URI.create(fileURL).getPath();
                    return Path.of(path).getFileName().toString();
                });

        return fileName;
    }
}