package bd.edu.seu.nookmanagementsystem.User.UserService;

import bd.edu.seu.nookmanagementsystem.User.Model.NookBook;
import bd.edu.seu.nookmanagementsystem.User.Model.User;
import bd.edu.seu.nookmanagementsystem.User.userInterface.userInterface;
import bd.edu.seu.nookmanagementsystem.utill.connectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class userService implements userInterface {

    @Override
    public void insertUser(User user) {
        try {
            Connection connection = connectionSingleton.getConnection();
            String query = "INSERT INTO userdetails VALUES(? , ? , ? , ? , ? , ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserEmail());
            preparedStatement.setString(3, user.getUserNumber());
            preparedStatement.setString(4, user.getUserAddress());
            preparedStatement.setString(5, user.getUserBirthday());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Problem in userService.insertUser");
        }
    }

    @Override
    public void insertBook(NookBook book) {
        try {
            Connection connection = connectionSingleton.getConnection();
            String query = "INSERT INTO nookbooks VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getGenre());
            preparedStatement.setString(5, book.getDescription());
            preparedStatement.setInt(6, book.getQuantity());
            preparedStatement.setString(7, book.getPrice());
            preparedStatement.setString(8, book.getStatus());
            preparedStatement.setString(9, book.getAddedBy());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Problem in userService.insertBook");
        }
    }

    @Override
    public List<NookBook> getBookList() {
        List<NookBook> books = new ArrayList<>();
        try {
            Connection connection = connectionSingleton.getConnection();
            String query = "SELECT * FROM nookbooks";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                NookBook book = new NookBook(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                );
                books.add(book);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Problem in userService.getBookList");
        }
        return books;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        try {
            Connection connection = connectionSingleton.getConnection();
            String query = "SELECT * FROM userdetails";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User tmp = new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
                userList.add(tmp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Problem in userService.getUserList");
        }
        return userList;
    }

    @Override
    public void borrowBook(int bookId, String userEmail) {
        Connection connection = connectionSingleton.getConnection();
        try {
            // First check book quantity
            String checkQuery = "SELECT quantity FROM nookbooks WHERE bookId = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                if (quantity > 0) {
                    // Update quantity
                    String updateQuery = "UPDATE nookbooks SET quantity = ? WHERE bookId = ?";
                    PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                    updateStmt.setInt(1, quantity - 1);
                    updateStmt.setInt(2, bookId);
                    updateStmt.executeUpdate();

                    // Insert borrow record
                    String insertQuery = "INSERT INTO borrow_history (bookId, userEmail, borrowDate, returnDate, status) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                    insertStmt.setInt(1, bookId);
                    insertStmt.setString(2, userEmail);
                    insertStmt.setString(3, LocalDate.now().toString());
                    insertStmt.setString(4, LocalDate.now().plusDays(14).toString()); // 2 weeks default return window
                    insertStmt.setString(5, "Borrowed");
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Problem in userService.borrowBook");
        }
    }

    @Override
    public List<NookBook> getBorrowHistory(String userEmail) {
        List<NookBook> borrowList = new ArrayList<>();
        try {
            Connection connection = connectionSingleton.getConnection();
            String query = "SELECT b.borrowId, b.borrowDate, b.returnDate, b.status, n.bookId, n.title, n.author, n.genre, n.price " +
                           "FROM borrow_history b JOIN nookbooks n ON b.bookId = n.bookId " +
                           "WHERE b.userEmail = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userEmail);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                NookBook book = new NookBook();
                book.setBorrowId(resultSet.getInt(1));
                book.setBorrowDate(resultSet.getString(2));
                book.setReturnDate(resultSet.getString(3));
                book.setStatus(resultSet.getString(4));
                book.setBookId(resultSet.getInt(5));
                book.setTitle(resultSet.getString(6));
                book.setAuthor(resultSet.getString(7));
                book.setGenre(resultSet.getString(8));
                book.setPrice(resultSet.getString(9));
                borrowList.add(book);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Problem in userService.getBorrowHistory");
        }
        return borrowList;
    }
}
