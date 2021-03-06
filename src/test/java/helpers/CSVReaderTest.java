package helpers;

import exceptions.InvalidDominoesCSVFile;
import mockit.Mock;
import mockit.MockUp;
import models.classes.Domino;
import models.classes.LandPortion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import views.templates.DominoView;
import views.templates.LandPortionView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest extends CSVReader {

    @BeforeAll
    private static void beforeAll() {
        new MockUp<DominoView>() {
            @Mock
            public void $init(Domino domino) {
            }
        };
        new MockUp<LandPortionView>() {
            @Mock
            public void $init(LandPortion landPortion) {
            }
        };
    }

    @Test
    void getDominoesShouldReturnNotEmptyDominoList() throws IOException, InvalidDominoesCSVFile {
        assertTrue(CSVReader.getDominoes().size() > 0);
    }

    @Test
    void getDominoesInvalidCSVShoudThrowException() {
        new MockUp<com.opencsv.CSVReader>() {
            @Mock
            public List<String[]> readAll() throws IOException {
                List<String[]> arrayList = new ArrayList<>();
                arrayList.add(new String[]{"Unexpected","Values"});
                return arrayList;
            }
        };

        assertThrows(InvalidDominoesCSVFile.class,
                CSVReader::getDominoes, "Should throw an exception as the csv file is not correct");
    }
}