package com.rentalcar.rental.service;

import com.rentalcar.rental.dto.ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClientService {

    ClientDTO getClientById(Long id);
    Page<ClientDTO> findAllClients(Pageable page);
    ClientDTO updatePoints(Long id, Integer points);
}
