package ar.edu.itba.client.utils;

import com.opencsv.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.*;

public abstract class CSVUtils {
    public static final String CSV_EXTENSION = "csv";

    public static CSVReaderHeaderAware getReader(Reader reader) {
        return (CSVReaderHeaderAware) new CSVReaderHeaderAwareBuilder(reader)
                .withCSVParser(
                        new CSVParserBuilder()
                                .withSeparator(';')
                                .build()
                )
                .build();
    }

    public static CSVPrinter getWriter(Writer writer, String[] headers) throws IOException {
        return CSVFormat.newFormat(';')
                .withQuoteMode(QuoteMode.MINIMAL)
                .withQuote('"')
                .withRecordSeparator(System.getProperty("line.separator"))
                .withHeader(headers)
                .print(writer);
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
