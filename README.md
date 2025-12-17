Projeto implementado utilizando Java 25.0.1 e compilado através do Maven 3.9.11

Banco de dados desenvolvido utilizando a SGBD MySQL

Aviso importante: para ter acesso ao banco de dados, altere o arquivo db.propertiees em src/main/resources:
- no campo "seu_banco" digite o nome do banco de dados implementado pelo arquivo usuarios.sql sem src/main/resources/database;
- no campo "seu_usuario" digite o usuário que terá acesso ao sistema MySQL cadastrado durante o processo de configuração do MySQL no seu computador;
- no campos "sua_senha" digite a senha cadastrada junto do nome de usuário.

Após isso, antes de rodar o algoritmo, vá até o arquivo usuarios.sql e rode os comandos SQL de criação do banco de dados e das tabelas, bem como de inserção do administrador no sistema.

Vá até o diretório PainelHidro/painel-hidro e execute:

Para compilar: 
"mvn compile package" ou "mvn clean install"

Para rodar: 
"mvn exec:java"
