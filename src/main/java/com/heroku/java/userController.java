package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class userController {
    private final DataSource dataSource;

    @Autowired
    public userController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/createAccFormUser")
    public String createAccForm() {
        return "createAccFormUser";
    }

    @PostMapping("/createAccFormUser")
    public String addAccount(HttpSession session, @ModelAttribute("createAccCust") User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO user2 (name, student_id, phone_number, email) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getName());
            statement.setString(2, user.getStudentID());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getEmail());

            statement.executeUpdate();

            connection.close();
            return "redirect:/loginUser";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @PostMapping("/loginUser")
    public String homePage(HttpSession session, @ModelAttribute("loginUser") User user, Model model) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT email, password FROM user";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            String returnPage = "";

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
         
                if (name.equals(user.getName()) && email.equals(user.getEmail()) ) {
                    session.setAttribute("name", user.getName());
                    session.setAttribute("email", user.getEmail());
                    returnPage = "redirect:/userHome";
                    break;
                } else {
                    returnPage = "/loginUser";
                }
            }

            connection.close();
            return returnPage;
        } catch (Throwable t) {
            t.printStackTrace();
            return "/loginUser";
        }
    }

    @PostMapping("/viewUser")
    public String viewAccCust(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");

        if (email != null) {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "SELECT name, studentid, phone, email FROM user WHERE email=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String studentid = resultSet.getString("studentid");
                    String phone = resultSet.getString("phone");

                    System.out.println("name from db: " + name);
                    User viewUser = new User(name, studentid, phone, email);
                    model.addAttribute("viewUser", viewUser);
                    System.out.println("Session viewUser: " + model.getAttribute("viewUser"));
                    return "viewUser";
                } else {
                    return "error";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "error";
    }

    @PostMapping("/updateUser")
    public String updateUser(HttpSession session, @ModelAttribute("updateUser") User user, Model model) {
       
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE user SET name=?, studentid=?, email=?, phone=? WHERE email=?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getName());
            statement.setString(2, user.getStudentID());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getEmail());

            statement.executeUpdate();

            return "updateUser";
        } catch (Throwable t) {
            t.printStackTrace();
            return "/loginUser";
        }
    }
}

    

