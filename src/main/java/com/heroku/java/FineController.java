package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heroku.Modal.Fine;

import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class FineController {
    private final DataSource dataSource;

    @Autowired
    public FineController(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @PostMapping("/createFineList")
    public String addFine(HttpSession session, @ModelAttribute("createFineList") Fine fine, BindingResult result) {

    try (Connection connection = dataSource.getConnection()) {
        String sql = "INSERT INTO fine (fineid, amountpay, statuspayment) VALUES (?,?,?)"; 
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, fine.getFineid());
        statement.setInt(2, fine.getAmountpay());
        statement.setString(3, fine.getStatuspayment());


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


    @PostMapping("/notifyFine")
    public Response notifyFine(@RequestBody FineRequest request) {
        // Send fine notification email to the student using the provided details
        // Implement your email sending logic here

        // Simulate a successful notification for demonstration purposes
        return new Response(true, "Fine notification sent successfully!");
    }

    // FineRequest class to represent the request body
    public static class FineRequest {
        private String studentEmail;
        private double fineAmount;
        private String fineDescription;

        // Getters and setters

        public String getStudentEmail() {
            return studentEmail;
        }

        public void setStudentEmail(String studentEmail) {
            this.studentEmail = studentEmail;
        }

        public double getFineAmount() {
            return fineAmount;
        }

        public void setFineAmount(double fineAmount) {
            this.fineAmount = fineAmount;
        }

        public String getFineDescription() {
            return fineDescription;
        }

        public void setFineDescription(String fineDescription) {
            this.fineDescription = fineDescription;
        }
    }

    // Response class to represent the response JSON
    public static class Response {
        private boolean success;
        private String message;

        // Constructor

        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        // Getters and setters

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
