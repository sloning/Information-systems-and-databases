package com.isbd.dao;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;

public class ResultSetExtractorFactory {
    public static <T> ResultSetExtractor<Optional<T>> optionalExtractor(
            RowMapper<? extends T> mapper) {
        return rs -> rs.next() ? Optional.ofNullable(mapper.mapRow(rs, 1)) : Optional.empty();
    }
}
