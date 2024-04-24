docker pull mysql/mysql-server

docker run --name=mysql -d mysql/mysql-server

docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=password -d -p 3306:3306 mysql/mysql-server

If you encounter a connection error, such as “Host ‘172.17.0.1’ is not allowed to connect to this MySQL server,” 
it means that the MySQL server is not configured to allow connections from the IP address 172.17.0.1, which is the IP address of the Docker host machine.


docker exec -it mysql-container mysql -u root -p

Create a new user account with a desired username and password:

CREATE USER 'user'@'%' IDENTIFIED BY 'password';

Grant appropriate privileges to the new user account:

GRANT ALL PRIVILEGES ON *.* TO 'user'@'%';

FLUSH PRIVILEGES;

create DATABASE stock_portfolio

use stock_portfolio

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    stock_symbol VARCHAR(255),
    quantity DOUBLE,
    price DOUBLE,
    order_date DATETIME,
    order_type ENUM('BUY', 'SELL')
);

insert into orders (userId, stock_symbol, quantity, price, transaction_date, order_type) values (1, 'AAPL', 10, 100, '2021-01-01 00:00:00', 'BUY');

// Generate 50 inserts to table orders with stockSymbols as AAPL,MSFT,AMZN,GOOGL,TSLA for userId 1 and different values for quantity and order type. Use price similar to actutal stock price
insert into orders (userId, stock_symbol, quantity, price, transaction_date, order_type) values (1, 'AAPL', 10, 100, '2021-01-01 00:00:00', 'BUY');
insert into orders (userId, stock_symbol, quantity, price, transaction_date, order_type) values (1, 'MSFT', 10, 200, '2021-01-01 00:00:00', 'BUY');
insert into orders (userId, stock_symbol, quantity, price, transaction_date, order_type) values (1, 'AMZN', 10, 300, '2021-01-01 00:00:00', 'BUY');

https://financialmodelingprep.com/api/v3/quote/AAPL,MSFT,AMZN,%20GOOGL,%20TSLA?apikey=oJGKyZ9AMdEo4jpgaHyiPWUKrgnxPAcV


ab -n 50 -c 20 http://localhost:8080/stock-holdings