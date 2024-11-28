package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.errors.OperationError;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <R extends ProcessorResult> ResponseEntity<?> mapToResponseEntity(Either<OperationError, R> either, HttpStatus httpStatus) {

        return either.isRight()
                ? new ResponseEntity<>(either.get(), httpStatus)
                : new ResponseEntity<>(either.getLeft(), httpStatus);
    }
}
