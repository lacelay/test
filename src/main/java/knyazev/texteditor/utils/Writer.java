package knyazev.texteditor.utils;

import java.io.File;
import java.io.FileWriter;

public class Writer {

    public static void write(String text, File file) throws Exception {

        if ( !file.isFile() ) {
            throw new Exception(String.format("There are no such file by following path [ %s ]", file.getAbsolutePath()));
        }
        try (FileWriter writer = new FileWriter(file)) {
            for (char c:
                 text.toCharArray()) {
                writer.write(c);
            }
        }

    }

}