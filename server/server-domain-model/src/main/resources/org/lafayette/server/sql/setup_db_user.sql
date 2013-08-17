
CREATE DATABASE lafayette;
USE mysql;

INSERT INTO 
	user (Host, User, Password, ssl_cipher, x509_issuer, x509_subject) 
VALUES 
	('localhost', 'lafayette', PASSWORD('ooleiTh1'), '', '', '');
FLUSH PRIVILEGES;

GRANT ALL PRIVILEGES 
	ON lafayette.* 
	TO 'lafayette'@'localhost';
FLUSH PRIVILEGES;
