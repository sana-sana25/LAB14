package com.example.securestoragelabjava.files;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public final class InternalTextStore {

    private InternalTextStore() {
    }

    // Écrire un fichier texte UTF-8 dans le stockage interne
    public static void writeUtf8(Context context,
                                 String fileName,
                                 String content) throws Exception {

        try (FileOutputStream fos =
                     context.openFileOutput(fileName, Context.MODE_PRIVATE)) {

            fos.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }

    // Lire un fichier texte UTF-8 depuis le stockage interne
    public static String readUtf8(Context context,
                                  String fileName) throws Exception {

        try (FileInputStream fis =
                     context.openFileInput(fileName)) {

            byte[] bytes = fis.readAllBytes();

            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    // Supprimer un fichier interne
    public static boolean delete(Context context,
                                 String fileName) {

        return context.deleteFile(fileName);
    }
}