package helpers;

import com.opencsv.CSVReaderBuilder;
import exceptions.InvalidDominoesCSVFile;
import models.classes.Domino;
import models.classes.DominoesList;
import models.classes.LandPortion;
import models.enums.CSVDominoesHeader;
import models.enums.LandPortionType;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles all the methods linked to the {@link Domino} source csv file.
 * The csv file must be located to the resources directory and called <code>dominos.csv</code>.
 */
public class CSVReader {

    /**
     * Returns a list of {@link Domino}s stored in the csv file.
     * @return  {@link DominoesList} which was contained in the csv file.
     * @throws  IOException Cannot read the csv file correctly.
     * @throws  InvalidDominoesCSVFile  The content of the csv file is not the same as expected.
     */
    public static DominoesList getDominoes() throws IOException, InvalidDominoesCSVFile {
        Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/dominos.csv"));
        com.opencsv.CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        List<String[]> records = csvReader.readAll();
        DominoesList dominoes = new DominoesList();
        for (String[] record : records) {
            try {
                int crownsPortion1 = Integer.valueOf(record[CSVDominoesHeader.NUMBER_CROWNS_PORTION_1.ordinal()]);
                LandPortionType typePortion1 = LandPortionType.valueOf(record[CSVDominoesHeader.TYPE_PORTION_1.ordinal()].toUpperCase());
                LandPortion landPortion1 = new LandPortion(crownsPortion1, typePortion1);
                int crownsPortion2 = Integer.valueOf(record[CSVDominoesHeader.NUMBER_CROWNS_PORTION_2.ordinal()]);
                LandPortionType typePortion2 = LandPortionType.valueOf(record[CSVDominoesHeader.TYPE_PORTION_2.ordinal()].toUpperCase());
                LandPortion landPortion2 = new LandPortion(crownsPortion2, typePortion2);
                int dominoNumber = Integer.valueOf(record[CSVDominoesHeader.NUMBER_DOMINOES.ordinal()]);
                Domino domino = new Domino(landPortion1, landPortion2, dominoNumber);
                dominoes.add(domino);
            } catch (Exception e){
                e.printStackTrace();
                throw new InvalidDominoesCSVFile();
            }
        }
        return dominoes;
    }

}
