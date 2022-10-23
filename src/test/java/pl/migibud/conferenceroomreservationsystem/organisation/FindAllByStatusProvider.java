package pl.migibud.conferenceroomreservationsystem.organisation;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

class FindAllByStatusProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "aaa", Organisation.Status.ACTIVE),
                                new Organisation( "www", Organisation.Status.ACTIVE),
                                new Organisation( "fff", Organisation.Status.ACTIVE),
                                new Organisation( "ddd", Organisation.Status.INACTIVE)
                        ),
                        Sort.by("name").ascending(),
                        Organisation.Status.ACTIVE,
                        Arrays.asList(
                                new Organisation( "aaa", Organisation.Status.ACTIVE),
                                new Organisation( "fff", Organisation.Status.ACTIVE),
                                new Organisation( "www", Organisation.Status.ACTIVE)
                        )
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "aaa", Organisation.Status.ACTIVE),
                                new Organisation( "www", Organisation.Status.ACTIVE),
                                new Organisation( "fff", Organisation.Status.ACTIVE),
                                new Organisation( "ddd", Organisation.Status.INACTIVE)
                        ),
                        Sort.by("name").descending(),
                        Organisation.Status.ACTIVE,
                        Arrays.asList(
                                new Organisation( "www", Organisation.Status.ACTIVE),
                                new Organisation( "fff", Organisation.Status.ACTIVE),
                                new Organisation( "aaa", Organisation.Status.ACTIVE)
                        )
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "aaa", Organisation.Status.ACTIVE),
                                new Organisation( "www", Organisation.Status.ACTIVE),
                                new Organisation( "fff", Organisation.Status.ACTIVE),
                                new Organisation( "ddd", Organisation.Status.INACTIVE)
                        ),
                        Sort.by("name").ascending(),
                        Organisation.Status.INACTIVE,
                        Arrays.asList(
                                new Organisation( "ddd", Organisation.Status.INACTIVE)
                        )
                ),
                Arguments.of(
                        Collections.emptyList(),
                        Sort.by("name").ascending(),
                        Organisation.Status.ACTIVE,
                        Collections.emptyList()
                ),
                Arguments.of(
                        Arrays.asList(
                                new Organisation( "aaa", Organisation.Status.ACTIVE),
                                new Organisation( "www", Organisation.Status.ACTIVE),
                                new Organisation( "fff", Organisation.Status.ACTIVE)
                        ),
                        Sort.by("name").ascending(),
                        Organisation.Status.INACTIVE,
                        Collections.emptyList()
                )
        );
    }
}
