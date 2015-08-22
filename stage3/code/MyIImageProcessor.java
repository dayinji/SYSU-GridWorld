package com.badprinter.myimage;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

import imagereader.IImageProcessor;

public class MyIImageProcessor implements IImageProcessor{

	private class BlueFilter extends RGBImageFilter {
		@Override
		public int filterRGB(int x, int y, int rgb) {
			return rgb & 0xff0000ff;
		}
	}
	private class GreenFilter extends RGBImageFilter {
		@Override
		public int filterRGB(int x, int y, int rgb) {
			return rgb & 0xff00ff00;
		}
	}
	private class RedFilter extends RGBImageFilter {
		@Override
		public int filterRGB(int x, int y, int rgb) {
			return rgb & 0xffff0000;
		}
	}	
	private class GreyFilter extends RGBImageFilter {
		@Override
		public int filterRGB(int x, int y, int rgb) {
			int red = (int)(((rgb & 0x00ff0000) >> 16)*0.299);
			int green = (int)(((rgb & 0x0000ff00) >> 8)*0.587);
			int blue = (int)(((rgb & 0x000000ff))*0.114);
			int grey = red + green + blue;
			//System.out.println((255<<24 + grey << 16 + grey << 8 + grey)+"");
			return (255<<24) +( grey << 16) + (grey << 8) + grey;
		}
	}
	
	@Override
	public Image showChanelB(Image img) {
		BlueFilter bf = new BlueFilter();
		return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), bf));
	}

	@Override
	public Image showChanelG(Image img) {
		GreenFilter gf = new GreenFilter();
		return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), gf));
	}

	@Override
	public Image showChanelR(Image img) {
		RedFilter rf = new RedFilter();
		return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), rf));
	}

	@Override
	public Image showGray(Image img) {
		GreyFilter greyf = new GreyFilter();
		return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), greyf));
	}

	public Image showAdd(Image img1, Image img2) {
		GreyFilter greyf = new GreyFilter();
		return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img1.getSource(), greyf));
	}

}
