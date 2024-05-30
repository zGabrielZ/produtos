package br.com.gabrielferreira.notificacao.api.mapper;

import br.com.gabrielferreira.notificacao.api.dto.NotificacaoDTO;
import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificacaoMapper {

    Notificacao toNotificacao(NotificacaoDTO notificacaoDTO);

    NotificacaoDTO toNotificacaoDto(Notificacao notificacao);
}
