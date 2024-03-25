package br.com.gabrielferreira.produtos.domain.model;

import br.com.gabrielferreira.produtos.domain.model.enums.PedidoStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.produtos.common.utils.DataUtils.UTC;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"cliente", "itensPedidos"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_PEDIDO")
public class PedidoV2 implements Serializable {

    @Serial
    private static final long serialVersionUID = 9008391171942665148L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "DATA", nullable = false)
    private ZonedDateTime data;

    @Column(name = "DATA_FINALIZADO")
    private ZonedDateTime dataFinalizado;

    @Enumerated(EnumType.STRING)
    private PedidoStatusEnum pedidoStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private ClienteV2 cliente;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ItemPedidoV2> itensPedidos = new ArrayList<>();

    @Column(name = "DATA_INCLUSAO", nullable = false)
    private ZonedDateTime dataInclusao;

    @Column(name = "DATA_ATUALIZACAO")
    private ZonedDateTime dataAtualizacao;

    public BigDecimal getPrecoTotal(){
        return this.itensPedidos.stream().map(ItemPedidoV2::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    public void prePersist(){
        dataInclusao = ZonedDateTime.now(UTC);
    }

    @PreUpdate
    public void preUpdate(){
        dataAtualizacao = ZonedDateTime.now(UTC);
    }
}
