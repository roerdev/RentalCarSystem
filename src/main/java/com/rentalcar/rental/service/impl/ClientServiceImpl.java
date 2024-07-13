package com.rentalcar.rental.service.impl;

import com.rentalcar.rental.dto.ClientDTO;
import com.rentalcar.rental.exception.NotFoundException;
import com.rentalcar.rental.mapper.ClientMapper;
import com.rentalcar.rental.repository.ClientRepository;
import com.rentalcar.rental.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDTO getClientById(Long id) {
        var client = this.clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
        return this.clientMapper.clientToClientDTO(client);
    }

    @Override
    public Page<ClientDTO> findAllClients(Pageable page) {
        return this.clientRepository.findAll(page).map(clientMapper::clientToClientDTO);
    }

    @Override
    @Transactional
    public ClientDTO updatePoints(Long id, Integer points) {
        var clientEntity = this.clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
        clientEntity.setLoyaltyPoints(clientEntity.getLoyaltyPoints() + points);
        return this.clientMapper.clientToClientDTO(this.clientRepository.saveAndFlush(clientEntity));
    }
}
