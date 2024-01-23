# language: pt
Funcionalidade: API - Clientes

  @smoke
  Cenário: Registrar um cliente
    Quando requisitar o registro de um novo cliente
    Então um novo cliente é registrado com sucesso

  Cenário: Registrar um cliente existente
    Dado que um cliente foi registrado
    Quando requisitar o registro de um novo cliente já existente
    Então um erro de cliente já registrado é retornada

  Cenário: Buscar dados de um cliente por cpf
    Dado que um cliente foi registrado
    Quando requisitar um cliente por cpf
    Então o cliente é exibido com sucesso

  Cenário: Buscar dados de todos os clientes
    Dado que um cliente foi registrado
    Quando requisitar a lista de clientes
    Então a lista de clientes é exibida com sucesso


