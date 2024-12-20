
#se tiver tendo problema da o comando mysql -u root -p no seu terminal ou muda a linha 10 com sua senha

import mysql.connector

# Configurações do banco de dados
db_config = {
    'host': 'localhost',
    'user': 'root',
    'password': 'root',
    'database': 'crud'
}

try:
    # Conectando ao MySQL
    connection = mysql.connector.connect(
        host=db_config['host'],
        user=db_config['user'],
        password=db_config['password']
    )

    cursor = connection.cursor()

    # Criando o banco de dados
    cursor.execute("CREATE DATABASE IF NOT EXISTS crud")

    # Selecionando o banco de dados
    connection.database = db_config['database']

    # Criando uma tabela exemplo
    cursor.execute("""
    CREATE TABLE IF NOT EXISTS exemplo (
        id INT AUTO_INCREMENT PRIMARY KEY,
        nome VARCHAR(255) NOT NULL,
        descricao TEXT
    )
    """)

    # Comitar as alterações
    connection.commit()

    print("Banco de dados e tabela criados com sucesso!")

except mysql.connector.Error as err:
    print(f"Erro: {err}")
finally:
    if connection.is_connected():
        cursor.close()
        connection.close()
