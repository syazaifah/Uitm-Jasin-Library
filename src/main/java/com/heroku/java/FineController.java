package com.heroku.java;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FineController {

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
