package pl.migibud.conferenceroomreservationsystem.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.CreateReservationRequest;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationDto;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationView;

import javax.validation.Valid;

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

    @PostMapping
    ResponseEntity<ReservationDto> registerReservation(@RequestBody @Valid CreateReservationRequest request){
        return ResponseEntity.ok(reservationFacade.registerReservation(request));
    }

}
