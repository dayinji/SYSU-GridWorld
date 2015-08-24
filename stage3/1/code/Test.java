//package com.badprinter.myimage;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;  

public class Test {
	private static  MyIImageIO io = new MyIImageIO();
	private static  MyIImageProcessor pro = new MyIImageProcessor();
	private static String path = "/home/badprinter/桌面/SYSU---GridWorld/stage3/bmptest/1.bmp";
	private static String path1 = "/home/badprinter/桌面/SYSU---GridWorld/stage3/bmptest/1_cpoy.bmp";
	
	public static void main(String[] args) throws IOException {
		Image img = io.myRead(path);
		io.myWrite(pro.showGray(img), path1);
	}

}
