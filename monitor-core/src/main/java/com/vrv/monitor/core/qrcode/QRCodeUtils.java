package com.vrv.monitor.core.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     二维码生成工具类
 * </p>
 * @author DanRui
 * @since 2017/09/30
 * @version 0.0.1
 */
public class QRCodeUtils {

    public static final String BASE64_FORMAT = "data:image/png;base64,";

    public static String encodeURL(String url) throws Exception {
        if (StringUtils.isBlank(url)) {
            return null;
        }

        int width = 200;
        int height = 200;

        String format = "png";
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);

        //MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());

        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        //注意此处拿到字节数据
        ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。  
        ImageIO.write(image, format, os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        byte[] bytes = os.toByteArray();
        // Base64编码
        String base64String = Base64.getEncoder().encodeToString(bytes);
        return BASE64_FORMAT+base64String;
    }

    public static void encodeURLToFile(String filepath, String filename, String url) throws Exception {
        if (StringUtils.isBlank(filepath) || StringUtils.isBlank(filename) || StringUtils.isBlank(url)) {
            return;
        }

        int width = 200;
        int height = 200;

        String format = "png";
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);

        Path path = FileSystems.getDefault().getPath(filepath, filename);
        if (!path.toFile().exists()) {
           path.toFile().getParentFile().mkdirs();
        }

        MatrixToImageWriter.writeToPath(bitMatrix, format, path);
    }

    public static void main(String[] args) throws Exception {
        //encodeURLToFile("D://qrcode", "qrcode.png", "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing");
        System.out.println(encodeURL("https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing"));
    }

}
