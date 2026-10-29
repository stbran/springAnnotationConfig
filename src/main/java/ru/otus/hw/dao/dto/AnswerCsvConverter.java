package ru.otus.hw.dao.dto;

import com.opencsv.bean.AbstractCsvConverter;
import ru.otus.hw.domain.Answer;

public class AnswerCsvConverter extends AbstractCsvConverter {

    @Override
    public Object convertToRead(String value) {
        var valueArr = value.split("%");
        if (valueArr[1].equals("true") || valueArr[1].equals("false")) {
            return new Answer(valueArr[0], Boolean.parseBoolean(valueArr[1]));
        } else {
            throw new RuntimeException("Error: incorrect answer format");
        }
    }
}
