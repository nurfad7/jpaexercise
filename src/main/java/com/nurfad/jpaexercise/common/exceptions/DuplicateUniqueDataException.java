package com.nurfad.jpaexercise.common.exceptions;

public class DuplicateUniqueDataException extends RuntimeException {
    public DuplicateUniqueDataException(String message) {
        super(message);
    }
}
