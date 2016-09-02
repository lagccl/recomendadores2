package cl.uc.psanabria.sysrec.work1.data;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class CSVReaderTest {
    @Test
    public void testCSVReadsEmptyFile() {
        String emptyString = "";
        CSVReader reader = new CSVReader(new ByteArrayInputStream(emptyString.getBytes()));

        assertFalse(reader.hasNext());
    }

    @Test
    public void testCSVReadsFileWithContent() {
        CSVReader reader = getCsvWithCommasReader();

        assertTrue(reader.hasNext());
    }

    @Test
    public void testCSVReadsFirstLine() {
        CSVReader reader = getCsvWithCommasReader();

        String line[] = reader.next();
        assertEquals(3, line.length);
    }

    @Test
    public void testCSVReadAllLines() {
        CSVReader reader = getCsvWithCommasReader();

        int times = 0;
        while (reader.hasNext()) {
            String line[] = reader.next();

            assertEquals(3, line.length);
            ++times;
        }

        assertEquals(2, times);
    }

    @Test
    public void resetReader() throws IOException {
        CSVReader reader = getCsvWithCommasReader();
        while (reader.hasNext()) {
            String line[] = reader.next();

            assertEquals(3, line.length);
        }
        assertFalse(reader.hasNext());

        reader.reset();
        assertTrue(reader.hasNext());
    }

    @Test
    public void testCSVReadFirstLineWithTabs() {
        CSVReader reader = getCsvWithTabsReader();
        String line[] = reader.next();

        assertEquals(3, line.length);
    }

    private CSVReader getCsvWithCommasReader() {
        String twoLinesCSV = "1,2,3\n4,5,6";
        return new CSVReader(new ByteArrayInputStream(twoLinesCSV.getBytes()));
    }

    private CSVReader getCsvWithTabsReader() {
        String twoLinesCSV = "1\t2\t3\n4\t5\t6";
        return new CSVReader(new ByteArrayInputStream(twoLinesCSV.getBytes()), "\t");
    }
}