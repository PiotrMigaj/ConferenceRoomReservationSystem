package pl.migibud.conferenceroomreservationsystem.exception;

import lombok.Data;

import java.util.List;

@Data
public class ErrorInfo {
	private final List<String> message;
}
