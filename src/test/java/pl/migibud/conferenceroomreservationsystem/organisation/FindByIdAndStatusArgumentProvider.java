package pl.migibud.conferenceroomreservationsystem.organisation;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

class FindByIdAndStatusArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        1L,
                        Organisation.Status.ACTIVE,
                        false
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "PG-Projekt", Organisation.Status.ACTIVE),
                                new Organisation( "WODKAN", Organisation.Status.ACTIVE)
                        ),
                        1L,
                        Organisation.Status.ACTIVE,
                        true
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "PG-Projekt", Organisation.Status.INACTIVE),
                                new Organisation( "WODKAN", Organisation.Status.ACTIVE)
                        ),
                        3L,
                        Organisation.Status.INACTIVE,
                        true
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "PG-Projekt", Organisation.Status.ACTIVE),
                                new Organisation( "WODKAN", Organisation.Status.ACTIVE)
                        ),
                        5L,
                        Organisation.Status.INACTIVE,
                        false
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "PG-Projekt", Organisation.Status.ACTIVE),
                                new Organisation( "WODKAN", Organisation.Status.ACTIVE)
                        ),
                        100L,
                        Organisation.Status.ACTIVE,
                        false
                )
        );
    }
}
