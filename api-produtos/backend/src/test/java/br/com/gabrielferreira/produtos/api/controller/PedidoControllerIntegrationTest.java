package br.com.gabrielferreira.produtos.api.controller;

import br.com.gabrielferreira.produtos.api.dto.create.PedidoCreateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static br.com.gabrielferreira.produtos.tests.PedidoFactory.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PedidoControllerIntegrationTest {

    private static final String URL = "/usuarios";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private PedidoCreateDTO pedidoCreateDTO;

    private Long idUsuarioExistente;

    private Long idUsuarioInexistente;

    private Long idPedidoExistente;

    private Long idPedidoInexistente;

    private Long idPedidoExistenteFinalizado;

    private Long idPedidoExistenteCancelado;

    @BeforeEach
    void setUp(){
        pedidoCreateDTO = criarPedido();
        idUsuarioExistente = 1L;
        idUsuarioInexistente = -1L;
        idPedidoExistente = 1L;
        idPedidoInexistente = -1L;
        idPedidoExistenteFinalizado = 2L;
        idPedidoExistenteCancelado = 3L;
    }

    @Test
    @DisplayName("Deve cadastrar pedido quando informar dados")
    @Order(1)
    void deveCadastrarPedidoQuandoInformarDados() throws Exception{
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos");
        String jsonBody = objectMapper.writeValueAsString(pedidoCreateDTO);

        String pedidoStatusEsperado = "ABERTO";
        String precoTotalEsperado = BigDecimal.valueOf(39.00).toString();

        ResultActions resultActions = mockMvc
                .perform(post(url)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.data").exists());
        resultActions.andExpect(jsonPath("$.pedidoStatus").value(pedidoStatusEsperado));
        resultActions.andExpect(jsonPath("$.precoTotal").value(precoTotalEsperado));
        resultActions.andExpect(jsonPath("$.dataInclusao").exists());
        resultActions.andExpect(jsonPath("$.itensPedidos").exists());
    }

    @Test
    @DisplayName("Não deve cadastrar pedido quando informar usuário inexistente")
    @Order(2)
    void naoDeveCadastrarPedidoQuandoInformarUsuarioInexistente() throws Exception{
        String url = URL.concat("/").concat(idUsuarioInexistente.toString()).concat("/pedidos");
        String jsonBody = objectMapper.writeValueAsString(pedidoCreateDTO);

        ResultActions resultActions = mockMvc
                .perform(post(url)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.titulo").value("Não encontrado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Usuário não encontrado"));
    }

    @Test
    @DisplayName("Não deve cadastrar pedido quando informar produto inexistente")
    @Order(3)
    void naoDeveCadastrarPedidoQuandoInformarProdutoInexistente() throws Exception{
        pedidoCreateDTO.getItensPedidos().get(0).setIdProduto(-1L);
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos");
        String jsonBody = objectMapper.writeValueAsString(pedidoCreateDTO);

        ResultActions resultActions = mockMvc
                .perform(post(url)
                        .content(jsonBody)
                        .contentType(MEDIA_TYPE_JSON)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.titulo").value("Não encontrado"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Produto não encontrado"));
    }

    @Test
    @DisplayName("Deve buscar pedido por id quando existir dado informado")
    @Order(4)
    void deveBuscarPedidoPorId() throws Exception {
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos/")
                .concat(idPedidoExistente.toString());

        ResultActions resultActions = mockMvc
                .perform(get(url)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").exists());
        resultActions.andExpect(jsonPath("$.data").exists());
        resultActions.andExpect(jsonPath("$.pedidoStatus").exists());
        resultActions.andExpect(jsonPath("$.usuario").exists());
        resultActions.andExpect(jsonPath("$.precoTotal").exists());
        resultActions.andExpect(jsonPath("$.dataInclusao").exists());
    }

    @Test
    @DisplayName("Não deve buscar pedido por id quando não existir dado informado")
    @Order(5)
    void naoDeveBuscarPedidoPorId() throws Exception {
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos/")
                .concat(idPedidoInexistente.toString());

        ResultActions resultActions = mockMvc
                .perform(get(url)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Pedido não encontrado"));
    }

    @Test
    @DisplayName("Não deve buscar pedido por id quando não existir usuário informado")
    @Order(6)
    void naoDeveBuscarPedidoPorIdQuandoNaoEncontrarUsuario() throws Exception {
        String url = URL.concat("/").concat(idUsuarioInexistente.toString()).concat("/pedidos/")
                .concat(idPedidoExistente.toString());

        ResultActions resultActions = mockMvc
                .perform(get(url)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(jsonPath("$.mensagem").value("Usuário não encontrado"));
    }

    @Test
    @DisplayName("Deve finalizar pedido por id")
    @Order(7)
    void deveFinalizarPedidoPorId() throws Exception {
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos/")
                .concat(idPedidoExistente.toString()).concat("/finalizar");

        ResultActions resultActions = mockMvc
                .perform(put(url)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Não deve finalizar pedido por id quando já estiver finalizado")
    @Order(8)
    void naoDeveFinalizarPedidoPorIdQuandoJaEstiverFinalizado() throws Exception {
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos/")
                .concat(idPedidoExistenteFinalizado.toString()).concat("/finalizar");

        ResultActions resultActions = mockMvc
                .perform(put(url)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Este pedido já foi finalizado"));
    }

    @Test
    @DisplayName("Não deve finalizar pedido por id quando já estiver cancelado")
    @Order(9)
    void naoDeveFinalizarPedidoPorIdQuandoJaEstiverCancelado() throws Exception {
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos/")
                .concat(idPedidoExistenteCancelado.toString()).concat("/finalizar");

        ResultActions resultActions = mockMvc
                .perform(put(url)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Este pedido não pode ser finalizado pois já está cancelado"));
    }

    @Test
    @DisplayName("Deve cancelar pedido por id")
    @Order(10)
    void deveCancelarPedidoPorId() throws Exception {
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos/")
                .concat(idPedidoExistente.toString()).concat("/cancelar");

        ResultActions resultActions = mockMvc
                .perform(put(url)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Não deve cancelar pedido por id quando já estiver cancelado")
    @Order(11)
    void naoDeveCancelarPedidoPorIdQuandoJaEstiverCancelado() throws Exception {
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos/")
                .concat(idPedidoExistenteCancelado.toString()).concat("/cancelar");

        ResultActions resultActions = mockMvc
                .perform(put(url)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Este pedido já foi cancelado"));
    }

    @Test
    @DisplayName("Não deve cancelar pedido por id quando já estiver finalizado")
    @Order(12)
    void naoDeveCancelarPedidoPorIdQuandoJaEstiverFinalizado() throws Exception {
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos/")
                .concat(idPedidoExistenteFinalizado.toString()).concat("/cancelar");

        ResultActions resultActions = mockMvc
                .perform(put(url)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.titulo").value("Regra de negócio"));
        resultActions.andExpect(jsonPath("$.mensagem").value("Este pedido não pode ser cancelado pois já está finalizado"));
    }

    @Test
    @DisplayName("Deve buscar pedidos paginados")
    @Order(12)
    void deveBuscarPedidosPaginados() throws Exception {
        String url = URL.concat("/").concat(idUsuarioExistente.toString()).concat("/pedidos")
                .concat("?page=0&size=5&sort=id,desc");

        ResultActions resultActions = mockMvc
                .perform(get(url)
                        .accept(MEDIA_TYPE_JSON));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.content").exists());
    }
}
