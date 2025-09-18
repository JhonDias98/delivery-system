-- Criação dos bancos para cada microsserviço
CREATE DATABASE restaurants_db;
CREATE DATABASE orders_db;
CREATE DATABASE delivery_db;
CREATE DATABASE payments_db;

-- Criação de usuários específicos para cada serviço
CREATE USER restaurants_user WITH PASSWORD 'restaurants_pass';
CREATE USER orders_user WITH PASSWORD 'orders_pass';
CREATE USER delivery_user WITH PASSWORD 'delivery_pass';
CREATE USER payments_user WITH PASSWORD 'payments_pass';

-- Concessão de privilégios
GRANT ALL PRIVILEGES ON DATABASE restaurants_db TO restaurants_user;
GRANT ALL PRIVILEGES ON DATABASE orders_db TO orders_user;
GRANT ALL PRIVILEGES ON DATABASE delivery_db TO delivery_user;
GRANT ALL PRIVILEGES ON DATABASE payments_db TO payments_user;

