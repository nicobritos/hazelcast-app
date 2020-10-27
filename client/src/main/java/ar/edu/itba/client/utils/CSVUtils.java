package ar.edu.itba.client.utils;

import com.opencsv.*;

import java.io.*;

public abstract class CSVUtils {
    public static CSVReader getReader(Reader reader) {
        return new CSVReaderBuilder(reader)
                .withCSVParser(
                        new CSVParserBuilder()
                                .withSeparator(';')
                                .build()
                )
                .build();
    }

    public static ICSVWriter getWriter(Writer writer) {
        return new CSVWriterBuilder(writer)
                .withSeparator(';')
                .build();
    }

    public static Reader getFileReader(String filepath) {
        return new InputStreamReader(ClassLoader.getSystemResourceAsStream(filepath));
    }

    public static Writer getFileWriter(String filepath) throws FileNotFoundException {
        return new OutputStreamWriter(new FileOutputStream(filepath));
    }
}
