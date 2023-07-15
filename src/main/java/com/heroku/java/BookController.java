package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.heroku.Modal.Book;

import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class BookController {
    private final DataSource dataSource;

    @Autowired
    public BookController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

   @PostMapping("/addBookLib")
public String addBook(HttpSession session, @ModelAttribute("addBookLib") Book book, BindingResult result) {
    // if (result.hasErrors()) {
    //     return "addBookLib"; // Return the form again if there are validation errors
    // }
    
    try (Connection connection = dataSource.getConnection()) {
        String sql = "INSERT INTO book (bookid, booktitle, author, bookquantity) VALUES (?,?,?,?,?)"; 
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, book.getBookid());
        statement.setString(2, book.getBooktitle());
        statement.setString(3, book.getAuthor());
        statement.setInt(4, book.getBookquantity());

        // Check if the bookquantity field is empty or null
        // if (book.getQuantity()!= 0) {
        //     statement.setInt(5, book.getQuantity());
        // } else {
        //     statement.setNull(5, Types.INTEGER); // Set the column as null
        // }

        statement.executeUpdate();

        connection.close();
        return "redirect:/libHome";
    } catch (SQLException sqe) {
        sqe.printStackTrace();
        return "redirect:/";
    } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/";
    }
}


    @GetMapping("/addBookLib")
    public String addBookForm(HttpSession session, Model model){
        return "addBookLib";
    }


@GetMapping("/bookListLib")
    public String viewBookList(HttpSession session, Model model) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            final var statement = con.prepareStatement("SELECT * FROM book");
            final var rs = statement.executeQuery();
            while (rs.next()) {
                String bookid = rs.getString("bookid");
                String booktitle = rs.getString("booktitle");
                String author = rs.getString("author");
                Integer bookquantity = rs.getInt("bookquantity");

                Book book = new Book(bookid, booktitle, author, bookquantity);
                books.add(book);
            }
            model.addAttribute("books", books);
            return "bookListLib";
        } catch (SQLException sqe) {
            System.out.println("Error Code = " + sqe.getErrorCode());
            System.out.println("SQL state = " + sqe.getSQLState());
            System.out.println("Message = " + sqe.getMessage());
            System.out.println("printTrace /n");
            sqe.printStackTrace();

            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("E message : " + e.getMessage());

            return "redirect:/";
        }
    }
} 


    