package knyazev.texteditor.utils;

import java.io.File;
import java.io.FileReader;

public class Reader {

    public static String read(File file) throws Exception {

        StringBuilder text = new StringBuilder();

        if ( !file.isFile() ) {
            throw new Exception(String.format("There are no such file by following path [ %s ]", file.getAbsolutePath()));
        }
        try (FileReader reader = new FileReader(file)) {
            int i;
            while ( (i = reader.read()) != -1 ) {
                text.append( (char)i );
            }
        }

        return text.toString();
    }

}