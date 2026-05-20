package com.example.securestoragelabjava.external;

import android.content.Context;

import java.io.File;
import java.nio.charset.StandardCharsets;

public final class ExternalAppFilesStore {

    private ExternalAppFilesStore() {
    }

    // Écrire un fichier dans le stockage externe app-specific
    public static String write(Context context,
                               String fileName,
                               String content) throws Exception {

        File dir = context.getExternalFilesDir(null);

        if (dir == null) {
            return null;
        }

        File file = new File(dir, fileName);

        java.nio.file.Files.writeString(
                file.toPath(),
                content,
                StandardCharsets.UTF_8
        );

        return file.getAbsolutePath();
    }

    // Lire un fichier externe
    public static String read(Context context,
                              String fileName) throws Exception {

        File dir = context.getExternalFilesDir(null);

        if (dir == null) {
            return null;
        }

        File file = new File(dir, fileName);

        if (!file.exists()) {
            return null;
        }

        return java.nio.file.Files.readString(
                file.toPath(),
                StandardCharsets.UTF_8
        );
    }

    // Supprimer un fichier externe
    public static boolean delete(Context context,
                                 String fileName) {

        File dir = context.getExternalFilesDir(null);

        if (dir == null) {
            return false;
        }

        File file = new File(dir, fileName);

        return file.delete();
    }
}