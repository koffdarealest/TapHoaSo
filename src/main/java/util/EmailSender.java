package util;

public class EmailSender extends Thread{
    private String host;
    private String port;
    private String user;
    private char[] password;
    private String to;
    private String subject;
    private String message;

    public EmailSender(String host, String port, String user, char[] password, String to, String subject, String message) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            EmailUtility.sendEmail(host, port, user, password, to, subject, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
