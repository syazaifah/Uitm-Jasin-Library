package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heroku.Modal.Borrow;

import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class BorrowController {
    private final DataSource dataSource;

    @Autowired
    public BorrowController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/bookBorrowingForm")
    public String addBorrow(HttpSession session, @ModelAttribute("bookBorrowingForm") Borrow borrow) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO borrow (studid, bookid, bookquantity, dateborrow, datereturn) VALUES (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, borrow.getStudid());
            statement.setString(2, borrow.getBookid());
            statement.setInt(3, borrow.getBookquantity());
            statement.setDate(4, borrow.getDateborrow());
            statement.setDate(5, borrow.getDatereturn());

            statement.executeUpdate();

            connection.close();
            return "redirect:/userHome";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @GetMapping("/borrowerList")
    public String viewBorrrowerList(HttpSession session, Model model, Borrow borroww) {
        ArrayList<Borrow> borrows = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            final var statement = con.prepareStatement("SELECT studid, borrowid, bookid,bookquantity, dateborrow, datereturn FROM borrow ORDER BY studid");
            final var rs = statement.executeQuery();
            while (rs.next()) {
                String studid = rs.getString("studid");
                String borrowid = rs.getString("borrowid");
                String bookid = rs.getString("bookid");
                int bookquantity = rs.getInt("bookquantity");
                Date dateborrow = rs.getDate("dateborrow");
                Date datereturn = rs.getDate("datereturn");
                // borroww.setDatereturn(datereturn);
                Borrow borrow = new Borrow(studid, borrowid, bookid, bookquantity, dateborrow, datereturn);
                borrows.add(borrow);
            }
            model.addAttribute("borrows", borrows);
            return "borrowerList";
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



 @GetMapping("/deleteBorrow")
    public String deleteBorrow(@RequestParam("borrowid") Integer borrowid) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM borrow WHERE borrowid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, borrowid);
            statement.executeUpdate();
            connection.close();
            return "redirect:/borrowerList";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}