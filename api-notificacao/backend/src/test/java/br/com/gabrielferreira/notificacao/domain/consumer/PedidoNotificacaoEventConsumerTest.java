package br.com.gabrielferreira.notificacao.domain.consumer;

import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.notificacao.domain.model.enums.NotificacaoStatusEnum;
import br.com.gabrielferreira.notificacao.domain.service.impl.NotificacaoServiceImpl;
import br.com.gabrielferreira.produtos.commons.dto.NotificacaoDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.gabrielferreira.notificacao.tests.NotificacaoFactory.criarNotificacaoEmailDto;
import static br.com.gabrielferreira.notificacao.tests.NotificacaoFactory.criarNotificacaoEmailModel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PedidoNotificacaoEventConsumerTest {

    @Mock
    private NotificacaoServiceImpl notificacaoService;

    @InjectMocks
    private PedidoNotificacaoEventConsumer pedidoNotificacaoEventConsumer;

    private NotificacaoDTO notificacaoDTO;

    private Notificacao notificacaoRetorno;

    @Captor
    private ArgumentCaptor<Notificacao> argumentCaptorNotificacao;

    @BeforeEach
    void setUp(){
        notificacaoDTO = criarNotificacaoEmailDto();
        notificacaoRetorno = criarNotificacaoEmailModel();
        notificacaoRetorno.setStatus(NotificacaoStatusEnum.ENVIADO);
    }

    @Test
    @DisplayName("Deve consumir notificação")
    @Order(1)
    void deveConsumirNotificacao(){
        when(notificacaoService.enviarNotificacao(any())).thenReturn(notificacaoRetorno);

        pedidoNotificacaoEventConsumer.listenPedidoNotifcacaoEvent(notificacaoDTO);

        verify(notificacaoService).enviarNotificacao(argumentCaptorNotificacao.capture());

        Notificacao notificacao = argumentCaptorNotificacao.getValue();
        assertNotNull(notificacao);
    }
}
