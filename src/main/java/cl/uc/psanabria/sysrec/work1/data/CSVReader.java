package cl.uc.psanabria.sysrec.work1.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class CSVReader {
    private InputStream inputStream;
    private Scanner scanner;
    private String separator;

    public CSVReader(InputStream inputStream) {
        this(inputStream, ",");
    }

    public CSVReader(InputStream inputStream, String separator) {
        scanner = new Scanner(inputStream);
        this.separator = separator;
        this.inputStream = inputStream;
    }


    public boolean hasNext() {
        return scanner.hasNext();
    }

    public String[] next() {
        return scanner.nextLine().split(separator);
    }

    public void reset() throws IOException {
        inputStream.reset();
        scanner = new Scanner(inputStream);
    }
}
