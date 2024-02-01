package controller;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import util.Captcha;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
@WebServlet(urlPatterns = {"/generateCaptcha"})
public class generateCaptcha extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpg");

        Captcha captchaGenerator = new Captcha();
        BufferedImage biImage = captchaGenerator.generateCaptchaImage();

        OutputStream osImage = response.getOutputStream();
        ImageIO.write(biImage, "jpg", osImage);
    }
}
