package com.example.demo.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.ByteArrayOutputStream;

import java.util.Base64;

@Service
public class QRCodeService {

    public String generateQRCode(String text) {

        try {

            QRCodeWriter qrCodeWriter =
                    new QRCodeWriter();

            var bitMatrix =
                    qrCodeWriter.encode(
                            text,
                            BarcodeFormat.QR_CODE,
                            200,
                            200
                    );

            BufferedImage image =
                    new BufferedImage(
                            200,
                            200,
                            BufferedImage.TYPE_INT_RGB
                    );

            for (int x = 0; x < 200; x++) {

                for (int y = 0; y < 200; y++) {

                    image.setRGB(
                            x,
                            y,
                            bitMatrix.get(x, y)
                                    ? 0xFF000000
                                    : 0xFFFFFFFF
                    );
                }
            }

            ByteArrayOutputStream baos =
                    new ByteArrayOutputStream();

            ImageIO.write(image, "png", baos);

            String base64 =
                    Base64.getEncoder()
                            .encodeToString(
                                    baos.toByteArray()
                            );

            return "data:image/png;base64," + base64;

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}