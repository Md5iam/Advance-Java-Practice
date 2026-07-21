package bd.edu.seu.nookmanagementsystem.User.userInterface;

import bd.edu.seu.nookmanagementsystem.User.Model.NookBook;
import bd.edu.seu.nookmanagementsystem.User.Model.User;
import java.util.List;

public interface userInterface {
    void insertUser(User user);
    void insertBook(NookBook book);
    List<NookBook> getBookList();
    List<User> getUserList();
    void borrowBook(int bookId, String userEmail);
    List<NookBook> getBorrowHistory(String userEmail);
}
