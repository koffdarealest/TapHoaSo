package util;

import com.google.gson.JsonObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringConverter {

    public static int convertToInt(String input){
        int output = 0;
        try{
            output = Integer.parseInt(input);
        }catch (Exception e){
            throw new NumberFormatException();
        }
        return output;
    }

    public static String encodeBase64(String input){
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decodeBase64(String input){
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        return new String(decodedBytes);
    }

    public static String imgToBase64String(final RenderedImage img, final String formatName) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (final OutputStream b64os = Base64.getEncoder().wrap(os)) {
            ImageIO.write(img, formatName, b64os);
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        return os.toString();
    }
    public static BufferedImage base64StringToImg(final String base64String) {
        try {
            return ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64String)));
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    public static UUID convertToUUID(String input) {
        StringBuilder sb = new StringBuilder(input);
        sb.insert(20, '-');
        sb.insert(16, '-');
        sb.insert(12, '-');
        sb.insert(8, '-');

        // Parsing the formatted string to a UUID
        return UUID.fromString(sb.toString());
    }

    public static String convertTimestamp(String timestamp) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = inputFormat.parse(timestamp);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String currencyFormat(BigInteger input){
        try {
            Locale locale = new Locale("vi", "VN");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
            return numberFormat.format(input);
        }catch (Exception e) {
            return "0 ₫";
        }
    }

    public static String getStringOrNull(JsonObject jsonObject, String key) {
        return Optional.ofNullable(jsonObject.get(key))
                .map(com.google.gson.JsonElement::getAsString)
                .orElse(null);
    }
}
