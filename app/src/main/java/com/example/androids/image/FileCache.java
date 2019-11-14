package com.example.androids.image;

import java.io.File;
import android.content.Context;

public class FileCache {

    private File cacheDir;
    final String dataDir = "/sdcard/";
    public FileCache(Context context) {

        System.out.println("처음 파일 경로 " + android.os.Environment.MEDIA_MOUNTED);
        System.out.println("테스트 경로" + dataDir);
        // Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) //sd카드 사용여부
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "JsonParseTutorialCache");

        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        System.out.println("두번째 파일 경로 " + android.os.Environment.getExternalStorageDirectory());
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        // String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        System.out.println("파일 값 " + f);
        return f;

    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}