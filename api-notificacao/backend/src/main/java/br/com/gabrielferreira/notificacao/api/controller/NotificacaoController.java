package br.com.gabrielferreira.notificacao.api.controller;

import br.com.gabrielferreira.notificacao.api.dto.NotificacaoDTO;
import br.com.gabrielferreira.notificacao.api.mapper.NotificacaoMapper;
import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.notificacao.domain.service.NotificacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    private final NotificacaoMapper notificacaoMapper;

    @PostMapping("/emails")
    public ResponseEntity<NotificacaoDTO> enviarEmail(@Valid @RequestBody NotificacaoDTO notificacaoDTO){
        Notificacao notificacao = notificacaoMapper.toNotificacao(notificacaoDTO);
        Notificacao notificacaoEnviada = notificacaoService.enviarNotificacao(notificacao);
        NotificacaoDTO emailDto = notificacaoMapper.toNotificacaoDto(notificacaoEnviada);

        return ResponseEntity.status(HttpStatus.CREATED).body(emailDto);
    }
}
