package com.tinqin.library.book.persistence.filereaderfactory;

import com.tinqin.library.book.persistence.filereaderfactory.base.FileReader;
import com.tinqin.library.book.persistence.filereaderfactory.readers.CsvFileReader;
import org.springframework.stereotype.Component;
import com.tinqin.library.book.persistence.filereaderfactory.readers.JsonFileReader;

@Component
public class FileReaderFactory {

    public FileReader createCsvFileReader(String patch, Integer batchSize) {

        return new CsvFileReader(patch, batchSize);
    }

    public FileReader createJsonFileReader(String patch, Integer batchSize) {

        return new JsonFileReader(patch, batchSize);
    }
}
