package com.heroku.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import com.heroku.Modal.Book;
import com.heroku.Modal.Borrow;
import com.heroku.Modal.Fine;
import java.sql.Date;

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
        String libid = (String) session.getAttribute("libid");
        System.out.println("libid at finelist: " + libid);
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO fine (borrowid, actualdatereturn, amountpay,libid) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // statement.setInt(1, fine.getFineid());
            statement.setInt(1, fine.getBorrowid());
            statement.setDate(2, fine.getActualdatereturn());
            statement.setInt(3, fine.getAmountpay());
            statement.setString(4, libid);

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

    @GetMapping("/createFineList")
    public String createFineList(@RequestParam("borrowid") String borrowId, @RequestParam("studid") String studentId,
            Model model, Borrow borrow, HttpSession session) {

        // model.getAttribute("libid"+libid);
        model.addAttribute("borrowId", borrowId);
        model.addAttribute("studentId", studentId);

        return "createFineList";
    }

    @GetMapping("/fineList")
    public String viewFineList(HttpSession session, Model model) {
        ArrayList<Fine> fines = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            final var statement = con.prepareStatement("SELECT f.fineid, f.amountpay, f.borrowid, f.actualdatereturn," +
                    "b.studid FROM fine f JOIN borrow b ON b.borrowid=f.borrowid ORDER BY fineid");
            final var rs = statement.executeQuery();
            while (rs.next()) {
                Integer fineid = rs.getInt("fineid");
                Integer amountpay = rs.getInt("amountpay");
                Integer borrowid = rs.getInt("borrowid");
                Date actualdatereturn = rs.getDate("actualdatereturn");
                String studentid = rs.getString("studid");

                Fine fine = new Fine(fineid, amountpay, borrowid, actualdatereturn, studentid);
                fines.add(fine);
            }
            model.addAttribute("fines", fines);
            return "fineList";
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

    @GetMapping("/updateFineList")
    public String showUpdateForm(@RequestParam("fineId") Integer fineid, Model model) {
        System.out.println("fineid: "+fineid);
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM fine f JOIN borrow b ON b.borrowid=f.borrowid WHERE fineid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, fineid);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Integer amountpay = rs.getInt("amountpay");
                String statuspayment = rs.getString("statuspayment");
                String studid = rs.getString("studid");
                Date actualdatereturn = rs.getDate("actualdatereturn");
                Integer borrowid = rs.getInt("borrowid");
                

                Fine fine = new Fine(fineid, borrowid,statuspayment,studid, actualdatereturn, amountpay);
                model.addAttribute("fine", fine);
            }
            connection.close();
            return "updateFineList";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/updateFineList")
    public String updateFine(@ModelAttribute("fine") Fine fine) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE fine SET amountpay = ?, statuspayment = ?, actualdatereturn = ?, borrowid = ? WHERE fineid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, fine.getAmountpay());
            statement.setString(2, fine.getStatuspayment());
            statement.setDate(3, fine.getActualdatereturn());
            statement.setInt(4, fine.getBorrowid());
            statement.setInt(5, fine.getFineid());

            statement.executeUpdate();

            connection.close();
            return "redirect:/fineList";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/deleteFine")
    public String deleteBookList(@RequestParam("fineId") Integer fineId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM fine WHERE fineid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, fineId);
            statement.executeUpdate();
            connection.close();
            return "redirect:/fineList";
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}