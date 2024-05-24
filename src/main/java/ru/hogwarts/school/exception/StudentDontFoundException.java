package ru.hogwarts.school.exception;

public class StudentDontFoundException extends RuntimeException{
    public StudentDontFoundException() {
    }

    public StudentDontFoundException(String message) {
        super(message);
    }

    public StudentDontFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentDontFoundException(Throwable cause) {
        super(cause);
    }

    public StudentDontFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
