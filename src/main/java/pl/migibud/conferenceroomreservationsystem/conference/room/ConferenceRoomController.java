package pl.migibud.conferenceroomreservationsystem.conference.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.migibud.conferenceroomreservationsystem.conference.room.dto.ConferenceRoomDto;
import pl.migibud.conferenceroomreservationsystem.conference.room.dto.CreateConferenceRoomRequest;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomError;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomException;
import pl.migibud.conferenceroomreservationsystem.organisation.Organisation;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/conferencerooms")
@RequiredArgsConstructor
class ConferenceRoomController {

    private final ConferenceRoomFacade conferenceRoomFacade;
    private final ConferenceRoomQueryRepository conferenceRoomQueryRepository;

    @PostMapping
    ResponseEntity<ConferenceRoomDto> registerConferenceRoom(@RequestBody @Valid CreateConferenceRoomRequest request){
        ConferenceRoomDto result = conferenceRoomFacade.registerConferenceRoom(request);
        return ResponseEntity.created(URI.create("api/conferencerooms/"+result.getId())).body(result);
    }

    @GetMapping
    ResponseEntity<Page<ConferenceRoomDto>> getOrganisations(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer size
    ){
        return ResponseEntity.ok(conferenceRoomQueryRepository.findAllBy(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    ResponseEntity<ConferenceRoomDto> getConferenceRoomByName(@PathVariable String id){
        ConferenceRoomDto conferenceRoomDto = conferenceRoomQueryRepository.getByIdBy(id)
                .orElseThrow(() ->new ConferenceRoomException(ConferenceRoomError.CONFERENCE_ROOM_NOT_FOUND));
        return ResponseEntity.ok(conferenceRoomDto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteConferenceRoomById(@PathVariable String id){
        conferenceRoomFacade.deleteConferenceRoomById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<ConferenceRoomDto> updateConferenceRoom(
            @PathVariable String id,
            @RequestBody @Valid CreateConferenceRoomRequest createConferenceRoomRequest
    ){
        ConferenceRoomDto result = conferenceRoomFacade.updateConferenceRoom(id, createConferenceRoomRequest);
        return ResponseEntity.ok(result);
    }

}
