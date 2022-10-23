package pl.migibud.conferenceroomreservationsystem.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParserUtil {
    private JsonParserUtil(){
    }

    public static <T> String asJsonString(final T t) {
        try {
            return new ObjectMapper().writeValueAsString(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
