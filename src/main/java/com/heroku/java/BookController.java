package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heroku.Modal.Book;

import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        // return "addBookLib"; // Return the form again if there are validation errors
        // }

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO book (bookid, booktitle, author, status, bookquantity) VALUES (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, book.getBookid());
            statement.setString(2, book.getBooktitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getStatus());
            statement.setInt(5, book.getBookquantity());

            // Check if the bookquantity field is empty or null
            // if (book.getQuantity()!= 0) {
            // statement.setInt(5, book.getQuantity());
            // } else {
            // statement.setNull(5, Types.INTEGER); // Set the column as null
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
    public String addBookForm(HttpSession session, Model model) {
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
                String status = rs.getString("status");
                Integer bookquantity = rs.getInt("bookquantity");

                Book book = new Book(bookid, booktitle, author, status, bookquantity);
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

    
    @GetMapping("/updateBookList")
    public String showUpdateForm(@RequestParam("bookId") String bookid, Model model) {
        System.out.println("bookid: "+bookid);
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM book WHERE bookid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bookid);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String booktitle = rs.getString("booktitle");
                String author = rs.getString("author");
                String status = rs.getString("status");
                Integer bookquantity = rs.getInt("bookquantity");

                Book book = new Book(bookid, booktitle, author, status, bookquantity);
                model.addAttribute("book", book);
            }
            connection.close();
            return "updateBookList";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/updateBookList")
    public String updateBook(@ModelAttribute("book") Book book) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE book SET booktitle = ?, author = ?, status = ?, bookquantity = ? WHERE bookid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, book.getBooktitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getStatus());
            statement.setInt(4, book.getBookquantity());
            statement.setString(5, book.getBookid());
            statement.executeUpdate();

            connection.close();
            return "redirect:/bookListLib";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/deleteBookList")
    public String deleteBookList(@RequestParam("bookId") String bookId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM book WHERE bookid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bookId);
            statement.executeUpdate();
            connection.close();
            return "redirect:/bookListLib";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/bookListUser")
    public String viewBookListUser(HttpSession session, Model model) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            final var statement = con.prepareStatement("SELECT * FROM book");
            final var rs = statement.executeQuery();
            while (rs.next()) {
                String bookid = rs.getString("bookid");
                String booktitle = rs.getString("booktitle");
                String author = rs.getString("author");
                String status = rs.getString("status");
                Integer bookquantity = rs.getInt("bookquantity");

                Book book = new Book(bookid, booktitle, author, status, bookquantity);
                books.add(book);
            }
            model.addAttribute("books", books);
            return "bookListUser";
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


    @GetMapping("/fiction")
    public String displayFiction(Model model) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT *\r\n" + //
                    "FROM book\r\n" + //
                    "WHERE bookid LIKE '%fc%'";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String bookid = rs.getString("bookid");
                String booktitle = rs.getString("booktitle");
                String author = rs.getString("author");
                String status = rs.getString("status");
                Integer bookquantity = rs.getInt("bookquantity");

                Book book = new Book(bookid, booktitle, author, status, bookquantity);
                books.add(book);
            }
            model.addAttribute("books", books);
            return "fiction";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/science")
    public String displayScience(Model model) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT *\r\n" + //
                    "FROM book\r\n" + //
                    "WHERE bookid LIKE '%sc%'";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String bookid = rs.getString("bookid");
                String booktitle = rs.getString("booktitle");
                String author = rs.getString("author");
                String status = rs.getString("status");
                Integer bookquantity = rs.getInt("bookquantity");

                Book book = new Book(bookid, booktitle, author, status, bookquantity);
                books.add(book);
            }
            model.addAttribute("books", books);
            return "science";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/historical")
    public String displayHistorical(Model model) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT *\r\n" + //
                    "FROM book\r\n" + //
                    "WHERE bookid LIKE '%ht%'";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String bookid = rs.getString("bookid");
                String booktitle = rs.getString("booktitle");
                String author = rs.getString("author");
                String status = rs.getString("status");
                Integer bookquantity = rs.getInt("bookquantity");

                Book book = new Book(bookid, booktitle, author, status, bookquantity);
                books.add(book);
            }
            model.addAttribute("books", books);
            return "historical";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/non")
    public String displayNon(Model model) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT *\r\n" + //
                    "FROM book\r\n" + //
                    "WHERE bookid LIKE '%non%'";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String bookid = rs.getString("bookid");
                String booktitle = rs.getString("booktitle");
                String author = rs.getString("author");
                String status = rs.getString("status");
                Integer bookquantity = rs.getInt("bookquantity");

                Book book = new Book(bookid, booktitle, author, status, bookquantity);
                books.add(book);
                model.addAttribute("book", book);
            }
            
            model.addAttribute("books", books);
            return "non";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    

    @GetMapping("/bookBorrowForm")
    public String bookBorrowForm(Model model, @RequestParam("bookId") String bookId,HttpSession session) {
    System.out.println("BookId: " + bookId);
    String studid = (String) session.getAttribute("studid");
    try (Connection con = dataSource.getConnection()) {
        String sql = "SELECT * FROM BOOK WHERE bookid = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, bookId);
        ResultSet rs = statement.executeQuery();
        
        if (rs.next()) {
            String bookTitle = rs.getString("booktitle");
            String author = rs.getString("author");
            // Retrieve other book information as needed
            
            // Create a new Book object
            Book book = new Book(bookId, bookTitle, author);
            model.addAttribute("studid",studid);
            // Add the book object to the model
            model.addAttribute("book", book);
        }
        con.close();
        return "bookBorrowForm";
    } catch (SQLException sqe) {
        sqe.printStackTrace();
        return "error";
    }
}

}