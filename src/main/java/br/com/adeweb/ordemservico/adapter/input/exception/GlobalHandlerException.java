package br.com.adeweb.ordemservico.adapter.input.exception;

import br.com.adeweb.ordemservico.adapter.input.response.ErrorResponse;
import br.com.adeweb.ordemservico.core.exception.EntidadeNaoEncontradaExecption;
import br.com.adeweb.ordemservico.core.exception.OrdemServicoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {

    @ExceptionHandler(EntidadeNaoEncontradaExecption.class)
    public ResponseEntity<ErrorResponse> handlerNotFound(EntidadeNaoEncontradaExecption ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(OrdemServicoException.class)
    public ResponseEntity<ErrorResponse> handleErrorDatabase(OrdemServicoException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
