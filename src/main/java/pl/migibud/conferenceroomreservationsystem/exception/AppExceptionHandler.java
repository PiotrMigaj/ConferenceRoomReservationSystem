package pl.migibud.conferenceroomreservationsystem.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomError;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomException;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationError;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = OrganisationException.class)
    ResponseEntity<ErrorInfo> handleAppUserException(OrganisationException e){
        HttpStatus httpStatus = null;
        if (OrganisationError.ORGANISATION_NOT_FOUND.equals(e.getOrganisationError())){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        if (OrganisationError.ORGANISATION_ALREADY_EXISTS.equals(e.getOrganisationError())){
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(Collections.singletonList(e.getOrganisationError().getMessage())));
    }

    @ExceptionHandler(value = ConferenceRoomException.class)
    ResponseEntity<ErrorInfo> handleConferenceRoomException(ConferenceRoomException e){
        HttpStatus httpStatus = null;
        if (ConferenceRoomError.CONFERENCE_ROOM_NOT_FOUND.equals(e.getConferenceRoomError())){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        if (ConferenceRoomError.CONFERENCE_ROOM_NAME_NOT_UNIQUE_FOR_ORGANISATION.equals(e.getConferenceRoomError())){
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        if (ConferenceRoomError.CONFERENCE_ROOM_ALREADY_EXISTS.equals(e.getConferenceRoomError())){
            httpStatus = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(Collections.singletonList(e.getConferenceRoomError().getMessage())));
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error ->errors.add(error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
