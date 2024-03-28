package util;

public class StatusFormat {
    public static String formatStatus(String status) {
        if (status.equals("readyToSell")) {
            return "Selling";
        } else if (status.equals("buyerChecking")) {
            return "Buyer Checking";
        } else if (status.equals("buyerComplaining")) {
            return "Buyer Complaining about the product";
        } else if (status.equals("buyerCanceledComplain")) {
            return "Seller Denied Buyer's Complaint";
        } else if (status.equals("waitingAdmin")) {
            return "Waiting for Admin";
        } else if (status.equals("done")) {
            return "Done";
        } else if (status.equals("cancelled")) {
            return "Cancelled";
        } else {
            return "Unknown";
        }
    }
}
