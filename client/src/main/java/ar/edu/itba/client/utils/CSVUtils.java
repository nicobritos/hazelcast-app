package ar.edu.itba.client.utils;

import com.opencsv.*;

import java.io.*;

public abstract class CSVUtils {
    public static CSVReaderHeaderAware getReader(Reader reader) {
        return (CSVReaderHeaderAware) new CSVReaderHeaderAwareBuilder(reader)
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

    public static Reader getFileReader(String filepath) throws IOException {
        try {
            return new InputStreamReader(ClassLoader.getSystemResourceAsStream(filepath));
        } catch (NullPointerException e) {
            throw new IOException("File " + filepath + " not found");
        }
    }

    public static Writer getFileWriter(String filepath) throws FileNotFoundException {
        return new OutputStreamWriter(new FileOutputStream(filepath));
    }
}
