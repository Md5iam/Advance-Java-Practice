-- Database Initialization Script for Nook Book Management System

CREATE DATABASE IF NOT EXISTS nook_db;
USE nook_db;

-- 1. Table for User Details
CREATE TABLE IF NOT EXISTS userdetails (
    userName VARCHAR(255),
    userEmail VARCHAR(255) PRIMARY KEY,
    userNumber VARCHAR(255),
    userAddress VARCHAR(255),
    userBirthday VARCHAR(255),
    userPassword VARCHAR(255)
);

-- 2. Table for Nook Books
CREATE TABLE IF NOT EXISTS nookbooks (
    bookId INT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(255),
    description VARCHAR(1000),
    quantity INT,
    price VARCHAR(255),
    status VARCHAR(255),
    addedBy VARCHAR(255)
);

-- 3. Table for Borrow History / Reading List Tracking
CREATE TABLE IF NOT EXISTS borrow_history (
    borrowId INT AUTO_INCREMENT PRIMARY KEY,
    bookId INT,
    userEmail VARCHAR(255),
    borrowDate VARCHAR(255),
    returnDate VARCHAR(255),
    status VARCHAR(255),
    FOREIGN KEY (bookId) REFERENCES nookbooks(bookId) ON DELETE CASCADE,
    FOREIGN KEY (userEmail) REFERENCES userdetails(userEmail) ON DELETE CASCADE
);

-- Insert a default user for testing
INSERT INTO userdetails (userName, userEmail, userNumber, userAddress, userBirthday, userPassword)
VALUES ('Test User', 'test@gmail.com', '01712345678', 'Dhaka, Bangladesh', '2000-01-01', '1234')
ON DUPLICATE KEY UPDATE userPassword='1234';

