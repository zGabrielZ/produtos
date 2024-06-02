package br.com.gabrielferreira.notificacao.domain.model.enums;

public enum NotificacaoStatusEnum {
    ENVIADO,
    ERRO;

    public static boolean isEnviado(NotificacaoStatusEnum status){
        return status.equals(NotificacaoStatusEnum.ENVIADO);
    }
}
