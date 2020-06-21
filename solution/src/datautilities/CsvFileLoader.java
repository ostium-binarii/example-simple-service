package datautilities;

import lombok.extern.slf4j.Slf4j;
import model.CompanyCode;
import model.Exception.DataLoadException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static datautilities.CsvFieldNames.CLOSING_PRICE;
import static datautilities.CsvFieldNames.CLOSING_PRICE_DATE;
import static datautilities.CsvFieldNames.COMPANY_CODE;


/**
 * Loads the given CSV file into memory for use by the service. This is kind of a throw away class, because in
 * reality, the datasource would be some kind of database. If file processing on a regular basis was actually
 * needed, this class would not rely on so many hard coded Strings (e.g. for file path, headers, etc) and pull
 * these from something that contractually guarantees file properties are the same between sender and consumer,
 * for example, a global ETL configuration service.
 */
@Slf4j
public class CsvFileLoader implements DataLoader {
    private static final String FILE_PATH = "closing_prices.csv";

    @Override
    public String getDataSource() {
        return FILE_PATH;
    }

    /**
     * Processes a given file into an in-memory data structure for use by the service.
     * @throws DataLoadException when an error is encountered processing the file, such as if the file is not in
     * the expected location, or a record's data is not formatted as expected.
     */
    @Override
    public Map<CompanyCode,TreeMap<Date,BigDecimal>> loadData() throws DataLoadException {
        try {
            return processFile(FILE_PATH);
        } catch (IOException | ParseException | IllegalArgumentException e) {
            log.error("Initial load of data from {} failed.", FILE_PATH, e);
            throw new DataLoadException(e);
        }
    }

    private Map<CompanyCode,TreeMap<Date,BigDecimal>> processFile(final String filePath) throws IOException, ParseException {
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(new FileReader(filePath));
        Map<CompanyCode,TreeMap<Date,BigDecimal>> stockMetrics = new HashMap<>();
        int recordCount = 0;

        // if sort order was guaranteed, this would be a great spot to pre-calculate the answer to Question 4.
        for (CSVRecord record : records) {
            CompanyCode companyCode = new CompanyCode(record.get(COMPANY_CODE));
            Date date = Marshaller.toDate(record.get(CLOSING_PRICE_DATE));
            BigDecimal closingPrice = new BigDecimal(record.get(CLOSING_PRICE));

            if (!stockMetrics.containsKey(companyCode)) {
                stockMetrics.put(companyCode, new TreeMap<>());
            }

            stockMetrics.get(companyCode).put(date, closingPrice);
            recordCount++;
        }

        log.info("Processed {} records from {} into memory.", recordCount, filePath);
        return stockMetrics;
    }

}
