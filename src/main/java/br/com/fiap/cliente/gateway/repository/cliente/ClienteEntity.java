package br.com.fiap.cliente.gateway.repository.cliente;

import br.com.fiap.cliente.core.entity.Cliente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
@Getter
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cpf;
    private String email;
    private Boolean active;

    public ClienteEntity(Cliente cliente) {
        this.id = cliente.getId();
        this.cpf = cliente.getCpf().getValor();
        this.email = cliente.getEmail().getValor();
        this.nome = cliente.getNome();
        this.active = true;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
