/**
 * ibaixiong.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.ibaixiong.erp.web.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * @description 生成条形码工具类
 * @author chenzehe
 * @email hljuczh@163.com
 * @create 2015年10月12日-下午4:41:30
 */
public class BarcodeUtil {
	public static boolean createBarcode(String code, String path) {
		boolean result = true;
		try {
			// Create the barcode bean
			Code39Bean bean = new Code39Bean();
			final int dpi = 150;
			// Configure the barcode generator
			bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); // makes the narrow bar // width exactly one pixel
			bean.setWideFactor(3);
			bean.doQuietZone(false);
			// Open output file
			File outputFile = new File(path);
			OutputStream out = new FileOutputStream(outputFile);
			try {
				// Set up the canvas provider for monochrome JPEG output
				BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
				// Generate the barcode
				bean.generateBarcode(canvas, code);
				// Signal end of generation
				canvas.finish();
			} finally {
				out.close();
			}
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	public static boolean createQrCode(String content, String filePath, String fileName) {
		try {
			int width = 76; // 图像宽度
			int height = 76; // 图像高度
			String format = "png";// 图像类型
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 1);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
			Path path = FileSystems.getDefault().getPath(filePath, fileName);
			MatrixToImageWriter.writeToPath(bitMatrix, format, path);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean createQrCodeWithLogo(String code, String filePath, String logo) {
		try {

			String format = "png";
			int BLACK = 0xFF000000;
			int WHITE = 0xFFFFFFFF;
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.MARGIN, 1);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, 150, 150, hints);
			File qrcodeFile = new File(filePath);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
				}
			}

			Graphics2D gs = image.createGraphics();
			// 载入logo
			Image img = ImageIO.read(new File(logo));
			gs.drawImage(img, 61, 61, null);
			gs.dispose();
			img.flush();

			ImageIO.write(image, format, qrcodeFile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		createBarcode("0000000000000000", "c://0000000000000000.png");
		//createQrCodeWithLogo("http://h5.ibaixiong.com/anti-counterfeiting/select.html?mac=S123456789101112", "c://qrcodelogo.png", "c://28X28.png");
		//createQrCode("http://h5.ibaixiong.com/anti-counterfeiting/select.html?mac=S123456789101112", "c://", "qrcode.png");
	}
}