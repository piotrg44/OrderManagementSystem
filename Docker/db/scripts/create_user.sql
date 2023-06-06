CREATE USER 'salesman'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON * . * TO 'salesman'@'localhost';
FLUSH PRIVILEGES;