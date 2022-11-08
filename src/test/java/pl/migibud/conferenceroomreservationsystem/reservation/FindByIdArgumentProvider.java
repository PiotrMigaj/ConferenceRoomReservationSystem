package pl.migibud.conferenceroomreservationsystem.reservation;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

class FindByIdArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        false
                ),
                Arguments.of(
                        Arrays.asList(new Reservation(
                                LocalDateTime.of(2022,10,9,10,30,00),
                                LocalDateTime.of(2022,10,9,11,30,00),
                                "r1",
                                null),
                                new Reservation(
                                        LocalDateTime.of(2022,11,9,10,30,00),
                                        LocalDateTime.of(2022,11,9,11,30,00),
                                        "r2",
                                        null),
                                new Reservation(
                                        LocalDateTime.of(2022,12,9,10,30,00),
                                        LocalDateTime.of(2022,12,9,11,30,00),
                                        "r3",
                                        null)),
                        true
                )
        );
    }
}
