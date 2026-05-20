package com.example.securestoragelabjava.cache;

import android.content.Context;

import java.io.File;
import java.nio.charset.StandardCharsets;

public final class CacheStore {

    private CacheStore() {
    }

    // Écrire un fichier dans le cache
    public static void write(Context context,
                             String fileName,
                             String content) throws Exception {

        File file = new File(context.getCacheDir(), fileName);

        java.nio.file.Files.writeString(
                file.toPath(),
                content,
                StandardCharsets.UTF_8
        );
    }

    // Lire un fichier depuis le cache
    public static String read(Context context,
                              String fileName) throws Exception {

        File file = new File(context.getCacheDir(), fileName);

        if (!file.exists()) {
            return null;
        }

        return java.nio.file.Files.readString(
                file.toPath(),
                StandardCharsets.UTF_8
        );
    }

    // Supprimer tous les fichiers du cache
    public static int purge(Context context) {

        File[] files = context.getCacheDir().listFiles();

        if (files == null) {
            return 0;
        }

        int deleted = 0;

        for (File file : files) {

            if (file.delete()) {
                deleted++;
            }
        }

        return deleted;
    }
}