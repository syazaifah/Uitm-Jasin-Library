package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.heroku.Modal.Librarian;

import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class LibrarianController {
    private final DataSource dataSource;

    @Autowired
    public LibrarianController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping("/createAccLib")
    public String addAccount(HttpSession session, @ModelAttribute("createAccLib") Librarian librarian) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO libdata (name, lib_id, phone_number, email, password) VALUES (?,?,?,?,?)"; 
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, librarian.getName());
            statement.setString(2, librarian.getId());
            statement.setString(3, librarian.getPhone());
            statement.setString(4, librarian.getEmail());
            statement.setString(5, librarian.getPassword());

            statement.executeUpdate();

            connection.close();
            return "redirect:/loginLib";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "redirect:/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    @GetMapping("/createAccLib")
    public String addUser(HttpSession session,Librarian librarian,Model model){
        return "createAccLib";
    }


    @PostMapping("/loginLib")
    public String libHome(HttpSession session, @ModelAttribute("loginLib") Librarian librarian, Model model) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT email, password FROM libdata";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            String returnPage = "";

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                if (email.equals(librarian.getEmail()) && password.equals(librarian.getPassword())) {
                    session.setAttribute("email", librarian.getEmail());
                    returnPage = "redirect:/libHome";
                    break;
                } else {
                    returnPage = "/loginLib";
                }
            }

            connection.close();
            return returnPage;
        } catch (Throwable t) {
            t.printStackTrace();
            return "/loginLib";
        }
    }

    @GetMapping("/libHome")
    public String userHome() {
        return "libHome";
    }
    

    @GetMapping("/profileLib")
    public String viewProfileLib(HttpSession session, Model model) {
    String email = (String) session.getAttribute("email");

    if (email != null) { 
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT name, lib_id, phone_number, password FROM libdata WHERE email=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String lib_id = resultSet.getString("lib_id");
                String phone_number = resultSet.getString("phone_number");
                String password = resultSet.getString("password");
                
                // Set attributes
                Librarian profileLib = new Librarian(name, lib_id, phone_number, email, password);
                model.addAttribute("profileLib", profileLib);
                return "profileLib";
            } else {
                return "error";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return "error";
}


@PostMapping("/profileLib")
public String profileLib(HttpSession session, Model model) {
    String email = (String) session.getAttribute("email");

    if (email != null) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT name, lib_id, phone_number, email, password FROM libdata WHERE email=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String lib_id = resultSet.getString("lib_id");
                String phone_number = resultSet.getString("phone_number");
                String password = resultSet.getString("password");

                System.out.println("name from db: " + name);
                Librarian profileLib = new Librarian(name, lib_id, phone_number, email, password);
                model.addAttribute("profileLib", profileLib);
                System.out.println("Session profileLib: " + model.getAttribute("profileLib"));
                return "profileLib";
            } else {
                return "error";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return "error";
}

    
    @PostMapping("/updateLib")
    public String updateLib(HttpSession session, @ModelAttribute("updateAcc") Librarian librarian, Model model) {
    try (Connection connection = dataSource.getConnection()) {
        String sql = "UPDATE libdata SET name=?, phone_number=?, password=? WHERE email=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, librarian.getName());
        statement.setString(2, librarian.getPhone());
        statement.setString(3, librarian.getPassword());
        statement.setString(4, (String) session.getAttribute("email"));

        int rowsUpdated = statement.executeUpdate();

        if (rowsUpdated > 0) {
            // Update successful
            return "redirect:/profileLib";
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


    @PostMapping("/deleteLib")
    public String deleteLib(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");

        if (email != null) {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "DELETE FROM libdata WHERE email=?";
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