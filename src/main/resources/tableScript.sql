CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO customers (name) VALUES 
('Ashwini More'),
('Prem More'),
('Raju More');

-- Transactions Table
CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    date DATE NOT NULL,
    product VARCHAR(100) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

INSERT INTO transactions (customer_id, date, product, amount) VALUES 
(1, '2025-10-01', 'Laptop', 1200.00),
(2, '2025-10-05', 'Mouse', 25.50),
(3, '2025-10-03', 'Keyboard', 45.00)
