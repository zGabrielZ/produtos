package br.com.gabrielferreira.produtos.domain.service.impl;

import br.com.gabrielferreira.produtos.commons.dto.NotificacaoDTO;
import br.com.gabrielferreira.produtos.domain.exception.NaoEncontradoException;
import br.com.gabrielferreira.produtos.domain.exception.RegraDeNegocioException;
import br.com.gabrielferreira.produtos.domain.model.Usuario;
import br.com.gabrielferreira.produtos.domain.model.ItemPedido;
import br.com.gabrielferreira.produtos.domain.model.Pedido;
import br.com.gabrielferreira.produtos.domain.model.Produto;
import br.com.gabrielferreira.produtos.domain.model.enums.PedidoStatusEnum;
import br.com.gabrielferreira.produtos.domain.publisher.PedidoNotificacaoEventPublisher;
import br.com.gabrielferreira.produtos.domain.repository.PedidoRepository;
import br.com.gabrielferreira.produtos.domain.service.UsuarioService;
import br.com.gabrielferreira.produtos.domain.service.PedidoService;
import br.com.gabrielferreira.produtos.domain.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

import static br.com.gabrielferreira.produtos.common.utils.DataUtils.*;
import static br.com.gabrielferreira.produtos.common.utils.ConstantesUtils.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    private final UsuarioService usuarioService;

    private final ProdutoService produtoService;

    private final PedidoNotificacaoEventPublisher pedidoNotificacaoEventPublisher;

    @Transactional
    @Override
    public Pedido salvarPedido(Long idUsuario, Pedido pedido) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
        validarItemPedido(pedido);

        for (ItemPedido itemPedido : pedido.getItensPedidos()) {
            Produto produto = produtoService.buscarProdutoPorId(itemPedido.getProduto().getId());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setValorAtualProduto(produto.getPreco());
        }

        pedido.setData(ZonedDateTime.now(UTC));
        pedido.setPedidoStatus(PedidoStatusEnum.ABERTO);
        pedido.setUsuario(usuario);

        pedido = pedidoRepository.save(pedido);
        return pedido;
    }

    @Override
    public Pedido buscarPedidoPorId(Long idUsuario, Long idPedido) {
        validarUsuarioExistente(idUsuario);
        return buscarPedidoPorIdUsuarioIdPedido(idUsuario, idPedido);
    }

    @Transactional
    @Override
    public Pedido finalizarPedidoPorId(Long idUsuario, Long idPedido) {
        validarUsuarioExistente(idUsuario);
        Pedido pedido = buscarPedidoPorIdUsuarioIdPedido(idUsuario, idPedido);
        validarPedidoFinalizado(pedido.getPedidoStatus());
        validarPedidoFinalizarCancelado(pedido.getPedidoStatus());

        pedido.setDataFinalizado(ZonedDateTime.now(UTC));
        pedido.setPedidoStatus(PedidoStatusEnum.FINALIZADO);

        pedido = pedidoRepository.save(pedido);
        return pedido;
    }

    @Transactional
    @Override
    public Pedido cancelarPedidoPorId(Long idUsuario, Long idPedido) {
        validarUsuarioExistente(idUsuario);
        Pedido pedido = buscarPedidoPorIdUsuarioIdPedido(idUsuario, idPedido);
        validarPedidoCancelado(pedido.getPedidoStatus());
        validarPedidoCancelarFinalizado(pedido.getPedidoStatus());

        pedido.setDataCancelado(ZonedDateTime.now(UTC));
        pedido.setPedidoStatus(PedidoStatusEnum.CANCELADO);

        pedido = pedidoRepository.save(pedido);
        return pedido;
    }

    @Override
    public Page<Pedido> buscarPedidosPaginados(Long idUsuario, Pageable pageable) {
        validarUsuarioExistente(idUsuario);
        return pedidoRepository.buscarPedidos(idUsuario, pageable);
    }

    @Transactional
    @Override
    public Pedido salvarPedidoEnviarNotificacao(Long idUsuario, Pedido pedido) {
        pedido = salvarPedido(idUsuario, pedido);

        try {
            NotificacaoDTO notificacaoDTO = montarPedidoRealizado(pedido);
            pedidoNotificacaoEventPublisher.publishPedidoNotificacaoEvent(notificacaoDTO);
        } catch (Exception e){
            log.error("Erro ao enviar notificação quando realizar o pedido {}", e.getMessage());
        }

        return pedido;
    }

    @Transactional
    @Override
    public void finalizarPedidoPorIdEnviarNotificacao(Long idUsuario, Long idPedido) {
        Pedido pedido = finalizarPedidoPorId(idUsuario, idPedido);

        try {
            NotificacaoDTO notificacaoDTO = montarPedidoFinalizado(pedido);
            pedidoNotificacaoEventPublisher.publishPedidoNotificacaoEvent(notificacaoDTO);
        } catch (Exception e){
            log.error("Erro ao enviar notificação quando for finalizar pedido {}", e.getMessage());
        }
    }

    @Transactional
    @Override
    public void cancelarPedidoPorIdEnviarNotificacao(Long idUsuario, Long idPedido) {
        Pedido pedido = cancelarPedidoPorId(idUsuario, idPedido);

        try {
            NotificacaoDTO notificacaoDTO = montarPedidoCancelado(pedido);
            pedidoNotificacaoEventPublisher.publishPedidoNotificacaoEvent(notificacaoDTO);
        } catch (Exception e){
            log.error("Erro ao enviar notificação quando for cancelar pedido {}", e.getMessage());
        }
    }

    private Pedido buscarPedidoPorIdUsuarioIdPedido(Long idUsuario, Long idPedido) {
        return pedidoRepository.buscarPedido(idUsuario, idPedido)
                .orElseThrow(() -> new NaoEncontradoException("Pedido não encontrado"));
    }

    private void validarPedidoFinalizado(PedidoStatusEnum pedidoStatusEnum){
        if(PedidoStatusEnum.isFinalizado(pedidoStatusEnum)){
            throw new RegraDeNegocioException("Este pedido já foi finalizado");
        }
    }

    private void validarPedidoFinalizarCancelado(PedidoStatusEnum pedidoStatusEnum){
        if(PedidoStatusEnum.isCancelado(pedidoStatusEnum)){
            throw new RegraDeNegocioException("Este pedido não pode ser finalizado pois já está cancelado");
        }
    }

    private void validarPedidoCancelado(PedidoStatusEnum pedidoStatusEnum){
        if(PedidoStatusEnum.isCancelado(pedidoStatusEnum)){
            throw new RegraDeNegocioException("Este pedido já foi cancelado");
        }
    }

    private void validarPedidoCancelarFinalizado(PedidoStatusEnum pedidoStatusEnum){
        if(PedidoStatusEnum.isFinalizado(pedidoStatusEnum)){
            throw new RegraDeNegocioException("Este pedido não pode ser cancelado pois já está finalizado");
        }
    }

    private void validarItemPedido(Pedido pedido){
        Map<Long, ItemPedido> itensProdutosRepetidos = new HashMap<>();
        pedido.getItensPedidos().forEach(itemPedido -> {
            Long idProduto = itemPedido.getProduto().getId();
            if(!itensProdutosRepetidos.containsKey(idProduto)){
                itensProdutosRepetidos.put(idProduto, itemPedido);
            } else if(itensProdutosRepetidos.get(idProduto) != null){
                ItemPedido itemPedidoEncontrado = itensProdutosRepetidos.get(idProduto);
                itemPedidoEncontrado.setQuantidade(itemPedidoEncontrado.getQuantidade() + itemPedido.getQuantidade());
            }
        });

        List<ItemPedido> itensPedidos = new ArrayList<>();
        for (Map.Entry<Long, ItemPedido> itemPedidoEntry : itensProdutosRepetidos.entrySet()) {
            itensPedidos.add(itemPedidoEntry.getValue());
        }

        pedido.setItensPedidos(itensPedidos);
    }

    private void validarUsuarioExistente(Long idUsuario){
        if(usuarioService.naoExisteUsuarioPorId(idUsuario)){
            throw new NaoEncontradoException("Usuário não encontrado");
        }
    }
}
