import java.util.Date;

public class Appt {
        private int appointmentID;
        private int userID;
        private String name;
        private String doctor;
        private String date;
        private String time;
        private String reason;

        // Getters
        public int getAppointmentID() {
            return appointmentID;
        }

        public int getUserID() {
            return userID;
        }

        public String getName() {
            return name;
        }

        public String getDoctor() {
            return doctor;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getReason() {
            return reason;
        }

        // Setters
        public void setAppointmentID(int appointmentID) {
            this.appointmentID = appointmentID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDoctor(String doctor) {
            this.doctor = doctor;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
