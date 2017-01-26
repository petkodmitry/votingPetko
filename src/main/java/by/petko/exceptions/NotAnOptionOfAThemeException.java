package by.petko.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Selected option does not belong to selected theme")
public class NotAnOptionOfAThemeException extends RuntimeException {
}
