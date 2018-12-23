package helpers;

import com.opencsv.CSVReaderBuilder;
import exceptions.InvalidDominoesCSVFile;
import models.classes.Domino;
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

public class CSVReader {

    /**
     * Return a list of all dominoes stored in the csv file
     * @return
     * @throws IOException
     * @throws InvalidDominoesCSVFile
     */
    public static List<Domino> getDominoes() throws IOException, InvalidDominoesCSVFile {
        Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/dominos.csv"));
        com.opencsv.CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        List<String[]> records = csvReader.readAll();
        List<Domino> dominoes = new ArrayList<>();
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
                throw new InvalidDominoesCSVFile();
            }
        }
        return dominoes;
    }

}
