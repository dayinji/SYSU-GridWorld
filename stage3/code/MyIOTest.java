package com.badprinter.myimage;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;  

public class MyIOTest {
	public static void main(String[] args) throws IOException {
		Image img;// = ImageIO.read(new File("/home/badprinter/桌面/SYSU---GridWorld/stage3/bmptest/1.bmp"));
		int w = 1340;
        int h = 720;
        int pix[] = new int[w * h];
        int index = 0;
        for (int y = 0; y < h; y++) {
            int red = (y * 255) / (h - 1);
            for (int x = 0; x < w; x++) {
                int blue = (x * 255) / (w - 1);
                pix[index++] = (255 << 24) | (red << 16) | blue;
            }
        }
        img = Toolkit.getDefaultToolkit()
                .createImage(new MemoryImageSource(w, h, pix, 0, w));
        BufferedImage bufferedImage = new BufferedImage(
        		img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        Graphics canvas = bufferedImage.getGraphics();
        canvas.drawImage(img, 0, 0, null);
        canvas.dispose();
		ImageIO.write( (RenderedImage) bufferedImage,  "jpeg", new File("/home/badprinter/桌面/SYSU---GridWorld/stage3/bmptest/1222." + "jpg"));
	}

}
