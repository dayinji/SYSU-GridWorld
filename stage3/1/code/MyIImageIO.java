//package com.badprinter.myimage;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import imagereader.IImageIO;

public class MyIImageIO implements IImageIO{
	// 读取文件头
	private byte[] fileHeader;
	// 读取信息头
	private byte[] infoHeader;
	// 记录图片位数
	private int bitCount;
	// 记录图片宽度
	private int width;
	// 记录图片高度
	private int height;
	// 记录图片大小
	private int size;
	// 记录补全字节数
	private int nullBlock;
	// 记录图片颜色RGB
	private int[] color;
	private Image image = null;

	@Override
	public Image myRead(String path) throws IOException {
		FileInputStream input = new FileInputStream(path);
		fileHeader = new byte[14];
		infoHeader = new byte[40];
		// 读取input中的文件头和信息头
		input.read(fileHeader, 0, 14);
		input.read(infoHeader, 0, 40);
		analyzeInfo();
		getColor(input);
		getImage();
		return image;
	}

	@Override
	public Image myWrite(Image img, String path) throws IOException {
		BufferedImage bufferedImage = new BufferedImage(
        		img.getWidth(null), img.getHeight(null),
                	BufferedImage.TYPE_INT_RGB);
		// 得到Graphics
	        	Graphics canvas = bufferedImage.getGraphics();
	        	canvas.drawImage(img, 0, 0, null);
		// 释放
	        	canvas.dispose();
	        	// 调用API写入
		ImageIO.write( (RenderedImage) bufferedImage,  "bmp", new File(path));
		return img;
	}
	
	private void analyzeInfo() {
		// 获取宽度
		width = getNumFromByte(infoHeader[4], 0) +  getNumFromByte(infoHeader[5], 8) +  getNumFromByte(infoHeader[6], 16) + 
				 getNumFromByte(infoHeader[7], 24) ;
		// 获取高度
		height = getNumFromByte(infoHeader[8], 0) +  getNumFromByte(infoHeader[9], 8) +  getNumFromByte(infoHeader[10], 16) + 
				 getNumFromByte(infoHeader[11], 24) ;
		// 获取位数
		bitCount = getNumFromByte(infoHeader[14], 0) +  getNumFromByte(infoHeader[15], 8) ;
		// 获取大小
		size = getNumFromByte(infoHeader[20], 0) +  getNumFromByte(infoHeader[21], 8) +  getNumFromByte(infoHeader[22], 16) +
				getNumFromByte(infoHeader[23], 24) ;
	}
	
	private void getColor(FileInputStream input) throws IOException {
		if  (bitCount == 24) {
			nullBlock =4 -  width*3 % 4;
			nullBlock = nullBlock == 4 ? 0 : nullBlock;
			color = new int[size];  
			byte[] temp = new byte[size];
			input.read(temp, 0, size);
			int index = 0;
			for (int i = 0 ; i < height ; i++) {
				for (int j = 0 ; j < width ; j++) {
					color[width*(height-1-i) + j] = (255<<24)  + getNumFromByte(temp[index + 2], 16)  + getNumFromByte(temp[index + 1], 8)  +
							getNumFromByte(temp[index], 0) ;
					index += 3;
				}
				index += nullBlock;
			}
		} else {
			//抛出异常
			throw new IllegalArgumentException("不是24位真彩色图片~");
		}
	}
	
	private void getImage() {
		image =  Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, color, 0, width));
	}
	
	private int getNumFromByte(byte a, int jinwei) {
		if (jinwei == 0) {
			return a&0xff;
		}
		else {
			return (a&0xff) << jinwei;
		}
	}

}
