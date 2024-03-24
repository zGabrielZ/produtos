package br.com.gabrielferreira.produtos.api.mapper;

import br.com.gabrielferreira.produtos.api.dto.ClienteDTO;
import br.com.gabrielferreira.produtos.api.dto.create.ClienteCreateDTO;
import br.com.gabrielferreira.produtos.api.dto.update.ClienteUpdateDTO;
import br.com.gabrielferreira.produtos.domain.model.ClienteV2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {FormatMapper.class})
public interface ClienteMapper {

    ClienteV2 toCliente(ClienteCreateDTO clienteCreateDTO);

    ClienteV2 toCliente(ClienteUpdateDTO clienteUpdateDTO);

    @Mapping(target = "dataInclusao", qualifiedByName = "formatData")
    @Mapping(target = "dataAtualizacao", qualifiedByName = "formatData")
    ClienteDTO toClienteDto(ClienteV2 clienteV2);

    default Page<ClienteDTO> toClientesDtos(Page<ClienteV2> clientes){
        return clientes.map(this::toClienteDto);
    }
}
