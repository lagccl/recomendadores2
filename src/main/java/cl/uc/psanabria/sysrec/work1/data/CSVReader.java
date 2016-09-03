package cl.uc.psanabria.sysrec.work1.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

class CSVReader {
    private InputStream inputStream;
    private Scanner scanner;
    private String separator;

    CSVReader(InputStream inputStream) {
        this(inputStream, ",");
    }

    CSVReader(InputStream inputStream, String separator) {
        scanner = new Scanner(inputStream);
        this.separator = separator;
        this.inputStream = inputStream;
    }


    boolean hasNext() {
        return scanner.hasNext();
    }

    String[] next() {
        return scanner.nextLine().split(separator);
    }

    void reset() throws IOException {
        inputStream.reset();
        scanner = new Scanner(inputStream);
    }
}
