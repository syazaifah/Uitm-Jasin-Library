package com.heroku.java;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

// import com.heroku.java.MODEL.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.*;

@Controller
public class userController {   
    private final DataSource dataSource;

    public userController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/createAccUser")
    public String addCustomer(HttpSession session,User user,Model model){
        return "createAccUser";
    }

    @PostMapping("/createAccUser")
    public String addAccount(HttpSession session, @ModelAttribute("user") User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO user2 (name, id, phone_number, email, password) VALUES (?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getName());
            statement.setString(2, user.getId());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());

            statement.executeUpdate();

            return "redirect:/loginUser";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }
}
/** 
    @GetMapping("/loginUser")
    public String showLoginForm(Model model) {
        model.addAttribute("user", User user());
        return "userLogin";
    }

    @PostMapping("/loginUser")
    public String loginUser(HttpSession session, @ModelAttribute("user") User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT email, password FROM user2 WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String password = resultSet.getString("password");

                if (password.equals(user.getPassword())) {
                    session.setAttribute("email", user.getEmail());
                    session.setAttribute("password", user.getPassword());
                    return "redirect:/userHome";
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "redirect:/loginUser";
    }
}
*/
/**        
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
*/
    

