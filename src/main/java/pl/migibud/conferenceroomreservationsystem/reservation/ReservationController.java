package pl.migibud.conferenceroomreservationsystem.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.migibud.conferenceroomreservationsystem.exception.reservation.ReservationError;
import pl.migibud.conferenceroomreservationsystem.exception.reservation.ReservationException;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.CreateReservationRequest;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationDto;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationView;

@Slf4j
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
class ReservationController {

    private final ReservationFacade reservationFacade;
    private final ReservationQueryRepository reservationQueryRepository;


    @GetMapping
    ResponseEntity<Page<ReservationView>> getAllReservations(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer size
    ){
        return ResponseEntity.ok(reservationQueryRepository.findAllBy(PageRequest.of(page,size)));
    }

    @GetMapping("/conferencerooms/{conferenceRoomId}")
    ResponseEntity<Page<ReservationView>> getAllReservationsByConferenceRoomId(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer size,
            @PathVariable String conferenceRoomId
    ){
        return ResponseEntity.ok(reservationQueryRepository.findAllByConferenceRoom_Id(PageRequest.of(page,size),conferenceRoomId));
    }

    @GetMapping("/{reservationId}")
    ResponseEntity<ReservationView> getReservationById(@PathVariable String reservationId){
        ReservationView reservationView = reservationQueryRepository.findAllById(reservationId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ReservationException(ReservationError.RESERVATION_NOT_FOUND));
        return ResponseEntity.ok(reservationView);
    }

    @PostMapping
    ResponseEntity<ReservationDto> registerReservation(@RequestBody @Validated(CreateReservationRequest.AddReservation.class) CreateReservationRequest request){
        return ResponseEntity.ok(reservationFacade.registerReservation(request));
    }

    @PutMapping("/{reservationId}")
    ResponseEntity<ReservationDto> updateReservation(@PathVariable String reservationId,@RequestBody @Validated(CreateReservationRequest.UpdateReservation.class) CreateReservationRequest request){
        ReservationDto reservationDto = reservationFacade.updateReservation(reservationId, request);
        return ResponseEntity.accepted().body(reservationDto);
    }

    @PatchMapping("/{reservationId}")
    ResponseEntity<ReservationDto> updateReservationName(@PathVariable String reservationId,@RequestBody @Validated(CreateReservationRequest.UpdateReservationName.class) CreateReservationRequest request){
        ReservationDto reservationDto = reservationFacade.updateReservationName(reservationId, request);
        return ResponseEntity.accepted().body(reservationDto);
    }

    @DeleteMapping("/{reservationId}")
    ResponseEntity<?> deleteReservationById(@PathVariable String reservationId){
        reservationFacade.deleteReservationById(reservationId);
        return ResponseEntity.accepted().build();
    }
}
