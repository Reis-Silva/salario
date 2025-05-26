# Projeto de Atividade de Teste 
#### Foram criado todos os desafios principais e opcionais pedidos na documentação de desafio:

#### Pontos de avaliação:
- 1 Criar uma aplicação Web Java, usando a especificação JSF (JavaServer Faces)
   com uma tela de Listagem de Pessoas, onde deve constar as informações
   consolidadas na tabela pessoa_salario_consolidado, após a realização do cálculo
   dos salários (item 2).
- 2 Implementação do cálculo dos salários das pessoas, com uma opção na tela acima
   para calcular / recalcular salários.

#### Diferenciais no desafio (opcionais):
- Utilizar processamento assíncrono no cálculo dos salários.
- Criar um relatório, utilizando JasperReports, para exibição das pessoas e seus salários calculados e exportação em PDF.
- Criar um CRUD de Pessoa, para atualizar, excluir e incluir novas pessoas.
- Criar um CRUD de usuário e implementar autenticação via Login e Senha.
- Criar testes de unidade.

#### OBS: Usuário de entrada no sistema: "Login: admin@admin.com e Senha: admin". Obs: pode ser criado outro usuário na tela.

# Tutorial de Emulação

## Scripts de Criação de tabelas no Postgres

- Localização: src/resources/data

### inserção manual no banco de dados em sequencia:

#### 1 - Criação da tabela Cargo
#### 2 - Criação da tabela Vencimentos
#### 3 - Criação da tabela Cargo_Vencimento
#### 4 - Criação da tabela Pessoa
#### 5 - Criação da tabela pessoa_salario_consolidado
#### 6 - Criação da tabela usuario
#### 7 - Criação da tabela agendamento_novo_salario_cargo

## Emulação Geral do Servidor

#### Numa IDE: Apenas emulador o servidor spring boot
- OBS: Alguns sistemas operacionais podem resultar em precisar que o repositorio do maven esteja em um lugar diferente da raiz, exemplo windows, devido o endereço de algumas bibliotecas dificultarem o acesso para nomes de usuario possuirem espaço.
- OBS2: No video enviado é explicado as utilizações de ferramentas e lógicas utilizadas para satisfazer os desafios principais e os opcionais.

#### Endereço da URL
- http://localhost:8080/salario/pages/login.xhtml - OBS: é necessário logar primeiro para ter acesso as outras páginas.
