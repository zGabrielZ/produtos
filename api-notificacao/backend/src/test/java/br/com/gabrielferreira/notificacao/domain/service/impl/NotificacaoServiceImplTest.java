package br.com.gabrielferreira.notificacao.domain.service.impl;

import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.notificacao.domain.model.enums.NotificacaoStatusEnum;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.gabrielferreira.notificacao.tests.NotificacaoFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotificacaoServiceImplTest {

    @Mock
    private NotificacaoServiceImpl notificacaoService;

    private Notificacao notificacao;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        notificacaoService = new NotificacaoServiceImpl(new LogEmailServiceImpl());

        notificacao = criarNotificacaoEmailModel();
    }

    @Test
    @DisplayName("Deve enviar notificação")
    @Order(1)
    void deveEnviarNotificacao(){
        Notificacao notificacaoRetorno = notificacaoService.enviarNotificacao(notificacao);

        assertNotNull(notificacaoRetorno);
        assertEquals(NotificacaoStatusEnum.ENVIADO, notificacaoRetorno.getStatus());
    }

}
