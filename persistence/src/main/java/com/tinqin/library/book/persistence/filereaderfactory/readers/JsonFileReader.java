package com.tinqin.library.book.persistence.filereaderfactory.readers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.tinqin.library.book.persistence.filereaderfactory.base.FileReader;
import com.tinqin.library.book.persistence.filereaderfactory.models.BookModel;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonFileReader implements FileReader {

    private final Integer batchSize;
    private final JsonParser jsonParser;

    public JsonFileReader(String patch, Integer batchSize) {

        this.batchSize = batchSize;
        this.jsonParser = initParser(patch);
    }

    private JsonParser initParser(String path) {

        try {
            InputStream pathResource = new ClassPathResource(path).getInputStream();
            JsonFactory jsonFactory = new JsonFactory();

            return jsonFactory.createParser(pathResource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BookModel> getBatch() {

        ArrayList<BookModel> batch = new ArrayList<>();

        try {
            while ((jsonParser.currentToken() == JsonToken.START_OBJECT || jsonParser.nextToken() != JsonToken.END_ARRAY) && batch.size() < batchSize) {

                if (jsonParser.currentToken() == null) {
                    break;
                }

                if (jsonParser.currentToken() == JsonToken.START_OBJECT) {

                    Optional<BookModel> book = parseObject();
                    book.ifPresent(batch::add);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return batch;
    }

    private Optional<BookModel> parseObject() {

        BookModel.BookModelBuilder builder = BookModel.builder();

        try {
            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {

                String fieldName = jsonParser.currentName();
                jsonParser.nextToken();

                switch (fieldName) {
                    case "title" -> builder.title(jsonParser.getText());
                    case "pages" -> builder.pages(jsonParser.getIntValue());
                    case "price" -> builder.price(BigDecimal.valueOf(jsonParser.getDoubleValue()));
                    case "priceRental" -> builder.priceRental(BigDecimal.valueOf(jsonParser.getDoubleValue()));
                    case "stock" -> builder.stock(jsonParser.getIntValue());
                    case "authors" -> builder.authors(getAuthors());
                    default -> jsonParser.skipChildren();
                }
            }

            return Optional.of(builder.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getAuthors() {

        List<String> authors = new ArrayList<>();

        try {
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {

                String author = jsonParser.getText();

                authors.add(author);
            }

            return authors;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
