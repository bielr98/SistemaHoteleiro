# Hotel Room Reservation System

## Descrição do Projeto
Este projeto simula um sistema de reserva e controle de quartos em um hotel, utilizando Java 17 e implementando concorrência através de threads. O sistema modela diversas entidades como Quartos, Hóspedes, Camareiras e Recepcionistas, cada uma com responsabilidades específicas dentro da operação do hotel.

## Entidades

### Quarto
- O hotel possui **10 quartos**.
- Cada quarto tem capacidade para até 4 hóspedes e possui uma única chave.

### Hóspede
- Existem **50 hóspedes**, cada um representado por uma thread.
- Hóspedes podem fazer parte de um grupo ou família; se o grupo exceder 4 pessoas, eles serão acomodados em múltiplos quartos.
- Hóspedes devem deixar a chave na recepção sempre que saírem para passear.

### Camareira
- **10 camareiras** estão representadas, cada uma por uma thread.
- Camareiras só podem entrar em quartos que estejam vagos ou cujos hóspedes não estejam presentes, ou seja, a chave deve estar na recepção.

### Recepcionista
- **5 recepcionistas** trabalham juntos para alocar hóspedes apenas em quartos vagos.
- Eles coordenam entre si para garantir alocações corretas e sem conflitos.

## Regras de Negócio

1. **Alocação de Quartos**: Recepcionistas alocam hóspedes apenas em quartos vagos.
2. **Limpeza de Quartos**: A limpeza é sempre realizada após a saída dos hóspedes, seja para passear ou ao terminarem sua estadia.
3. **Disponibilidade de Quartos**: Um quarto não pode ser alocado enquanto está sendo limpo.
4. **Espera por Quartos**: Se não houver quartos disponíveis, o hóspede deve esperar. Se a espera for longa, ele pode optar por passear e tentar novamente mais tarde. Após duas tentativas sem sucesso, o hóspede deixa uma reclamação e vai embora.
5. **Integridade do Grupo**: Todo o grupo deve sair para passear ou permanecer no quarto juntos.

## Simulações e Testes
O sistema deve ser capaz de simular várias situações, incluindo:
- Diferentes números de hóspedes chegando ao mesmo tempo.
- Grupos com mais de 4 pessoas.
- Todos os quartos estando ocupados simultaneamente.

  
