package pl.migibud.conferenceroomreservationsystem.organisation;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

class FindByIdArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        1L,
                        false
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "PG-Projekt", Organisation.Status.ACTIVE),
                                new Organisation( "WODKAN", Organisation.Status.ACTIVE)
                        ),
                        1L,
                        true
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "PG-Projekt", Organisation.Status.ACTIVE),
                                new Organisation( "WODKAN", Organisation.Status.ACTIVE)
                        ),
                        10L,
                        false
                )
        );
    }
}
