package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.heroku.Modal.User;

import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class UserController {
    private final DataSource dataSource;

    @Autowired
    public UserController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/createAccUser")
    public String addAccount(HttpSession session, @ModelAttribute("createAccCust") User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO student (studid, studname, studphonenumber, studemail, studpassword) VALUES (?,?,?,?,?)"; 
            PreparedStatement statement = connection.prepareStatement(sql);

             statement.setString(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());

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

    @GetMapping("/createAccUser")
    public String addUser(HttpSession session,User user,Model model){
        return "createAccUser";
    }


    @PostMapping("/loginUser")
    public String userHome(HttpSession session, @ModelAttribute("userLogin") User user, Model model) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT studemail, studpassword FROM student";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            String returnPage = "";

            while (resultSet.next()) {
                String studemail = resultSet.getString("studemail");
                String studpassword = resultSet.getString("studpassword");

                if (studemail.equals(user.getEmail()) && studpassword.equals(user.getPassword())) {
                    session.setAttribute("studemail", user.getEmail());
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

    @GetMapping("/userHome")
    public String userHome() {
        return "userHome";
    }
    

    @GetMapping("/profileUser")
    public String viewprofileUser(HttpSession session, Model model) {
    String studemail = (String) session.getAttribute("studemail");

    if (studemail != null) { 
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT studid, studname,  studphonenumber, studemail, studpassword FROM student WHERE studemail=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studemail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String studid= resultSet.getString("studid");
                String studname = resultSet.getString("studname");
                String studphonenumber = resultSet.getString("studphonenumber");
                String studpassword = resultSet.getString("studpassword");
                
                // Set attributes
                User profileUser = new User(studid, studname, studphonenumber,studemail, studpassword);
                model.addAttribute("profileUser", profileUser);
                return "profileUser";
            } else {
                return "error";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return "error";
    }

    @PostMapping("/profileUser")
    public String profileUser(HttpSession session, Model model) {
        String studemail = (String) session.getAttribute("studemail");

        if (studemail != null) {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "SELECT studid, studname,  studphonenumber, studemail, studpassword FROM student WHERE studemail=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, studemail);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String studid= resultSet.getString("studid");
                    String studname = resultSet.getString("studname");
                    String studphonenumber = resultSet.getString("studphonenumber");
                    String studpassword = resultSet.getString("studpassword");
                
                    System.out.println("studname from db: " + studname);
                    User profileUser = new User(studid, studname, studphonenumber, studemail, studpassword);
                    model.addAttribute("profileUser", profileUser);
                    System.out.println("Session profileUser: " + model.getAttribute("profileUser"));
                    return "profileUser";
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
        String sql = "UPDATE student SET studname=?, studphonenumber=?, studpassword=? WHERE studemail=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, user.getName());
        statement.setString(2, user.getPhone());
        statement.setString(3, user.getPassword());
        statement.setString(4, (String) session.getAttribute("studemail"));

        int rowsUpdated = statement.executeUpdate();

        if (rowsUpdated > 0) {
            // Update successful
            return "redirect:/profileUser";
        } else {
            // Update failed
            model.addAttribute("error", "Failed to update account");
            return "error";
        }
    } catch (SQLException e) {
        e.printStackTrace();
        model.addAttribute("error", "Failed to update account");
        return "error";
    }
}


    @PostMapping("/deleteUser")
    public String deleteUser(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");

        if (email != null) {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "DELETE FROM userdata WHERE email=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    session.invalidate();
                    return "redirect:/userlogin";
                } else {
                    System.out.println("Delete failed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "deleteError";
            }
        }

        return "deleteError";
    }
}