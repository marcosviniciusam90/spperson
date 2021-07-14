package com.mvam.spperson.resources.exceptions;

import com.mvam.spperson.services.exceptions.RecursoNaoEncontradoException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RecursosExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroDTO> recursoNaoEncontradoException(RecursoNaoEncontradoException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroDTO erroDTO = criarErroDTO(request, status, "Recurso nao encontrado", ex.getMessage(), null);
        return ResponseEntity.status(status).body(erroDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ErroDTO.Campo> fields = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            fields.add(new ErroDTO.Campo(nome, mensagem));
        }

        String mensagem = "Um ou mais campos nao foram preenchidos corretamente";
        ErroDTO erroDTO = criarErroDTO(request, status, "Campos invalidos", mensagem, fields);
        return ResponseEntity.status(status).body(erroDTO);
    }

    private ErroDTO criarErroDTO(HttpServletRequest request, HttpStatus status, String erro, String mensagem, List<ErroDTO.Campo> campos) {
        return ErroDTO.builder()
                .dataHora(Instant.now())
                .status(status.value())
                .erro(erro)
                .mensagem(mensagem)
                .endereco(request.getRequestURI())
                .metodo(request.getMethod())
                .campos(campos)
                .build();
    }
}
