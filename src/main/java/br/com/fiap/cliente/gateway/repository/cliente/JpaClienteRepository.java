package br.com.fiap.cliente.gateway.repository.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    Optional<ClienteEntity> findByCpfAndActiveIsTrue(String cpf);

    List<ClienteEntity> findAllByActiveIsTrue();
}
