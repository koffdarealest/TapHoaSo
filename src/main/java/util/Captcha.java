package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Captcha {

    public String generateCaptcha() {
        Random randChars = new Random();
        return generateCode(6, randChars);
    }


    public BufferedImage generateCaptchaImage(String sImageCode) {
        int iTotalChars = 6;
        int iHeight = 40;
        int iWidth = 150;

        Font fntStyle1 = new Font("Arial", Font.BOLD, 30);
        Font fntStyle2 = new Font("Verdana", Font.BOLD, 20);

        Random randChars = new Random();

        BufferedImage biImage = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();

        int iCircle = 15;

        for (int i = 0; i < iCircle; i++) {
            g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
            int iRadius = (int) (Math.random() * iHeight / 2.0);
            int iX = (int) (Math.random() * iWidth - iRadius);
            int iY = (int) (Math.random() * iHeight - iRadius);
            //g2dImage.fillRoundRect(iX, iY, iRadius * 2, iRadius * 2, 100, 100);
        }

        g2dImage.setFont(fntStyle1);

        for (int i = 0; i < iTotalChars; i++) {
            g2dImage.setColor(new Color(randChars.nextInt(255), randChars.nextInt(255), randChars.nextInt(255)));
            if (i % 2 == 0) {
                g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 24);
            } else {
                g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 35);
            }
        }

        g2dImage.dispose();
        return biImage;
    }

    private String generateCode(int iTotalChars, Random randChars) {
        StringBuilder captchaCode = new StringBuilder();
        for (int i = 0; i < iTotalChars; i++) {
            captchaCode.append((char) (randChars.nextInt(26) + 'A'));
        }
        return captchaCode.toString();
    }
    public void verifyCaptcha(String captcha) {
        //verify captcha

    }
    public void verifyCaptcha(String captcha) {
        //verify captcha

    }

}
