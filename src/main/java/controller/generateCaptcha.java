package controller;

import jakarta.servlet.annotation.WebServlet;
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
        resp.setContentType("image/jpg");
        Captcha captchaGenerator = new Captcha();
        String code = captchaGenerator.generateCaptcha();
        BufferedImage biImage = captchaGenerator.generateCaptchaImage(code);
        req.getSession().setAttribute("captcha", code);
        OutputStream osImage = resp.getOutputStream();
        ImageIO.write(biImage, "jpg", osImage);
    }
}
