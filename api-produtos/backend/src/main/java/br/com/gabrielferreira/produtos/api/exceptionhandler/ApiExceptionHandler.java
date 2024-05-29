package br.com.gabrielferreira.produtos.api.exceptionhandler;

import br.com.gabrielferreira.produtos.api.mapper.ErroPadraoMapper;
import br.com.gabrielferreira.produtos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.produtos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.produtos.domain.exception.model.ErroPadrao;
import br.com.gabrielferreira.produtos.domain.exception.model.ErroPadraoCampos;
import br.com.gabrielferreira.produtos.domain.exception.model.ErroPadraoFormulario;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.ZonedDateTime;
import java.util.List;

import static br.com.gabrielferreira.produtos.common.utils.DataUtils.*;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {

    private final ErroPadraoMapper erroPadraoMapper;

    private final MessageSource messageSource;

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroPadrao> msgException(RegraDeNegocioException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErroPadrao erroPadrao = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Regra de negócio", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadrao);
    }

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> naoEncontradoException(NaoEncontradoException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErroPadrao erroPadraoModel = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadraoModel);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> validacaoException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErroPadraoCampos erroPadraoCampos = erroPadraoMapper.toErroPadraoCampos(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Erro validação de campos", "Ocorreu um erro de validação nos campos", request.getRequestURI());

        List<ErroPadraoFormulario> campos = e.getBindingResult().getFieldErrors().stream()
                .map(campo -> erroPadraoMapper.toErroPadraoFormulario(campo.getField(), messageSource.getMessage(campo, LocaleContextHolder.getLocale())))
                .toList();
        erroPadraoCampos.setCampos(campos);

        return ResponseEntity.status(httpStatus).body(erroPadraoCampos);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> erroException(Exception e, HttpServletRequest request){
        log.error(e.getMessage(), e);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErroPadrao erroPadrao = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Erro inesperado", "Ocorreu um erro inesperado no sistema, tente mais tarde", request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadrao);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroPadrao> dataIntegrityViolationException(HttpServletRequest request){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErroPadrao erroPadrao = erroPadraoMapper.toErroPadrao(toFusoPadraoSistema(ZonedDateTime.now()), httpStatus.value(), "Violação de integridade", "Esta entidade possui relacionamento", request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(erroPadrao);
    }
}
