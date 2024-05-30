package br.com.gabrielferreira.notificacao.api.controller;

import br.com.gabrielferreira.notificacao.api.dto.NotificacaoDTO;
import br.com.gabrielferreira.notificacao.api.mapper.NotificacaoMapper;
import br.com.gabrielferreira.notificacao.domain.model.Notificacao;
import br.com.gabrielferreira.notificacao.domain.model.enums.NotificacaoStatusEnum;
import br.com.gabrielferreira.notificacao.domain.service.NotificacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static br.com.gabrielferreira.notificacao.tests.NotificacaoFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = NotificacaoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotificacaoControllerTest {

    private static final String URL = "/notificacoes";

    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected NotificacaoService notificacaoService;

    @MockBean
    protected NotificacaoMapper notificacaoMapper;

    private NotificacaoDTO notificacaoDTO;

    private Notificacao notificacao;

    @BeforeEach
    void setUp(){
        notificacaoDTO = criarNotificacaoEmail();
        notificacao = criarNotificacaoEmailModel();
    }

    @Test
    @DisplayName("Deve enviar notificação email")
    @Order(1)
    void deveEnviarNotificacaoEmail() throws Exception{
        when(notificacaoMapper.toNotificacao(notificacaoDTO)).thenReturn(notificacao);

        Notificacao notificacaoRetorno = notificacao;
        notificacaoRetorno.setStatus(NotificacaoStatusEnum.ENVIADO);
        when(notificacaoService.enviarNotificacao(notificacao)).thenReturn(notificacaoRetorno);

        NotificacaoDTO notificacaoDTORetorno = notificacaoDTO;
        notificacaoDTORetorno.setStatus(notificacaoRetorno.getStatus());
        when(notificacaoMapper.toNotificacaoDto(notificacaoRetorno)).thenReturn(notificacaoDTORetorno);

        String jsonBody = objectMapper.writeValueAsString(notificacaoDTO);

        String statusEsperado = NotificacaoStatusEnum.ENVIADO.toString();

        ResultActions resultActions = mockMvc
                .perform(post(URL.concat("/emails"))
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.status").value(statusEsperado));
    }
}
