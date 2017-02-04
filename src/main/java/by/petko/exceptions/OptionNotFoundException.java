package by.petko.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Couldn't find the Option in database")
public class OptionNotFoundException extends RuntimeException {
}
