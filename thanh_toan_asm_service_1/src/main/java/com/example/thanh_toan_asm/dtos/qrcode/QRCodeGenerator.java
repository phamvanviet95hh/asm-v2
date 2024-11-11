package com.example.thanh_toan_asm.dtos.qrcode;

import com.example.thanh_toan_asm.dtos.GlobalValue;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;


import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.util.Base64;


public class QRCodeGenerator {

    public static String generateQRCodeImage(String text)
            throws WriterException, IOException {

        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 400, 400);
        MatrixToImageConfig imageConfig = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, imageConfig);
        // Getting logo image
        BufferedImage logoImage = ImageIO.read( new File(GlobalValue.pathImageLogoQr));
        int finalImageHeight = qrImage.getHeight() - logoImage.getHeight();
        int finalImageWidth = qrImage.getWidth() - logoImage.getWidth();
        //Merging both images
        BufferedImage finalImage = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) finalImage.getGraphics();
        graphics.drawImage(qrImage, 0, 0, null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        graphics.drawImage(logoImage, (int) Math.round(finalImageWidth / 2), (int) Math.round(finalImageHeight / 2), null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(finalImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
