package com.rentalcar.rental.mapper;

import com.rentalcar.rental.dto.ClientDTO;
import com.rentalcar.rental.model.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {

    ClientDTO clientToClientDTO(Client client);
    Client clientDTOToClient(ClientDTO clientDTO);
}
