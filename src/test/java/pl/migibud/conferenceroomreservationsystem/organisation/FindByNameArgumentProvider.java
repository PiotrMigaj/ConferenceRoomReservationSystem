package pl.migibud.conferenceroomreservationsystem.organisation;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

class FindByNameArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        "test1",
                        false
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "test1", Organisation.Status.ACTIVE),
                                new Organisation( "test2", Organisation.Status.ACTIVE)
                        ),
                        "test1",
                        true
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "test1", Organisation.Status.ACTIVE),
                                new Organisation( "test2", Organisation.Status.ACTIVE)
                        ),
                        "test3",
                        false
                )
        );
    }
}
