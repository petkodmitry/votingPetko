package by.petko.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Action is not allowed for this state of object")
public class NotAllowedException extends RuntimeException {
}
