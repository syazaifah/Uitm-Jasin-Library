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
            String sql = "INSERT INTO librarian (libid, libname, libphonenumber, libemail, libpassword) VALUES (?,?,?,?,?)"; 
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, librarian.getId());
            statement.setString(2, librarian.getName());
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
            String sql = "SELECT libemail, libpassword FROM librarian";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            String returnPage = "";

            while (resultSet.next()) {
                String libemail = resultSet.getString("libemail");
                String libpassword = resultSet.getString("libpassword");

                if (libemail.equals(librarian.getEmail()) && libpassword.equals(librarian.getPassword())) {
                    session.setAttribute("libemail", librarian.getEmail());
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
    String libemail = (String) session.getAttribute("libemail");

    if (libemail != null) { 
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT libid, libname, libphonenumber, libpassword FROM librarian WHERE libemail=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, libemail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String libid = resultSet.getString("libid");
                String libname = resultSet.getString("libname");
                String libphonenumber = resultSet.getString("libphonenumber");
                String libpassword = resultSet.getString("libpassword");
                
                // Set attributes
                Librarian profileLib = new Librarian(libid, libname, libphonenumber, libemail, libpassword);
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
    String libemail = (String) session.getAttribute("libemail");

    if (libemail != null) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT libid, libname, libphonenumber, libemail, libpassword FROM librarian WHERE libemail=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, libemail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String libid = resultSet.getString("libid");
                String libname = resultSet.getString("libname");
                String libphonenumber = resultSet.getString("libphonenumber");
                String libpassword = resultSet.getString("libpassword");

                System.out.println("name from db: " + libname);
                Librarian profileLib = new Librarian(libid, libname, libphonenumber, libemail, libpassword);
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
        String sql = "UPDATE librarian SET libname=?, libphonenumber=?, libpassword=? WHERE libemail=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, librarian.getName());
        statement.setString(2, librarian.getPhone());
        statement.setString(3, librarian.getPassword());
        statement.setString(4, (String) session.getAttribute("libemail"));

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
        String libemail = (String) session.getAttribute("libemail");

        if (libemail != null) {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "DELETE FROM librarian WHERE libemail=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, libemail);

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