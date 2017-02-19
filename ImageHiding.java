//Arnoldas Kurbanovas

package proj1CSI526;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import javax.swing.*;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class ImageHiding extends JFrame implements ActionListener
{
 BufferedImage hostImage;
 BufferedImage secretImage;

 JPanel controlPanel;
 JPanel imagePanel;

 JTextField encodeBitsText;
 //added noChars
 JTextField noChars;
 JButton encodeBitsPlus;
 JButton encodeBitsMinus;

 JTextField nBitsText;
 JButton nBitsPlus;
 JButton nBitsMinus;

 ImageCanvas hostCanvas;
 ImageCanvas secretCanvas;

 Steganography s;
/*
 public BufferedImage getHostWave(){
	 ByteArrayOutputStream out = new ByteArrayOutputStream();
	 BufferedInputStream in = new BufferedInputStream(new FileInputStream(WAV_FILE));

	 int read;
	 byte[] buff = new byte[1024];
	 while ((read = in.read(buff)) > 0)
	 {
	     out.write(buff, 0, read);
	 }
	 out.flush();
	 byte[] audioBytes = out.toByteArray();
 }
 */
 
//@override
//public void actionPerformed(ActionEvent event){
//	JRadioButton button = (JRadioButton) event.getSource();
//	
//	if(button == strategyOne){
//		//do simething
//	}else if (button == strategyTwo){
//		//Do somethung else
//	}
//}

 //method to get the path to text file of the .wav file
 //then conver the text file into a byte array 
 public static byte[] getByteArray() throws IOException{
	 Path path = Paths.get("test.txt");
	 byte[] data = Files.readAllBytes(path);
	 return data;
	 
//	 //convert this into an int array 
//	 //return an int array thats ready to go
//	 
//	 //n is the number of encode bits that we can specify
//	 int n = 2;
//	 int x = 1;
//	 int i,o = 1;
//	 int inputLen = 0;
//	 inputLen = (data.length * 8) / n;
//	 int[] outputArry = new int[inputLen];
//	 
//	 while(x<= inputLen){
//		 outputArry[o] = (data[i] & mask) | outputArry[o];
//		 outputArry[o] = outputArry[o] << 8;
//		 data[i] = data[i] >>> n;
//		 if((o % 4) == 0){
//			 o++;
//		 }
//		 if((i % (8/n)) == 0){
//			 i++;
//		 }
//	 }
	 
	 
	 
 }
 
 //method that has my algorithm for taking an int array and 
 
 //method to convert from byte array to int array
 //method takes in a byte array and returns and int array
 public static int byteArrayToIntArray(byte[] byt){
	 final ByteBuffer byteBuffer = ByteBuffer.wrap(byt);
	 byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
	 return byteBuffer.getInt();
 }
 
 public static int[] toIntArray(byte[] byt){
	 int size = (byt.length / 4) + ((byt.length % 4 == 0) ? 0:1);
	 ByteBuffer byteBuffer = ByteBuffer.allocate(size*4);
	 byteBuffer.put(byt);
	 
	 byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
	 
	 int[] output = new int[size];
	 byteBuffer.rewind();
	 while(byteBuffer.remaining()>0){
		 output[byteBuffer.position()/4] = byteBuffer.getInt();
	 }
	 return output;
 }
 
 //method that takes in an integer 
 
 //method that takes in a byte array and converts it 
 //to a text file named output.txt
 public static void byteToFile(byte[] myByteArray){
	 //FileUtils.writeByteArrayToFile(new File("pathbane"), myByteArray);
	 //Path path = Paths.get("output.txt");
	 //Files.write(path, myByteArray, w);
	 try (FileOutputStream fos = new FileOutputStream("output.txt")) {
		    fos.write(myByteArray);
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
 }
 
 public BufferedImage getHostImage()
 {
  BufferedImage img = null;

  try
  {
   img = ImageIO.read(new File("host_image.jpg"));
  }
  catch (IOException ioe) { ioe.printStackTrace(); }

  return img;
 }

 public BufferedImage getSecretImage()
 {
  BufferedImage img = null;

  try
  {
   img = ImageIO.read(new File("secret_image.jpg"));
  }
  catch (IOException ioe) { ioe.printStackTrace(); }

  return img;
 }

 //look here *****
 public int getBits()
 {
  return Integer.parseInt(encodeBitsText.getText());
 }

 public void actionPerformed(ActionEvent event){
	 /*
	 JRadioButton strategyOne = new JRadioButton("strategyOne");
	 JRadioButton strategyTwo = new JRadioButton("strategyTwo");
	 
	 //ading buttons to a group so multiple cant be selected at once
	 ButtonGroup group = new ButtonGroup();
	 group.add(strategyOne);
	 group.add(strategyTwo);
	 
	 JPanel panel = new JPanel(new GridBagLayout());
	// specify constraints
	GridBagConstraints constraints = new GridBagConstraints();
	 
	constraints.gridx = 0;
	panel.add(strategyOne, constraints);
	 
	constraints.gridx = 1;
	panel.add(strategyTwo, constraints);
	 
	 ImageHiding actionListener = new ImageHiding();
	 strategyOne.addActionListener(actionListener);
	 strategyTwo.addActionListener(actionListener);
	 
	 
	 JRadioButton button = (JRadioButton) event.getSource();
		
		if(button == strategyOne){
			//do simething
		}else if (button == strategyTwo){
			//Do somethung else
		}
	 */
	 
  Object source = event.getSource();

  if (source == encodeBitsPlus)
  {
   int bits = this.getBits() + 1;

   if (bits > 8) { bits = 8; }

   encodeBitsText.setText(Integer.toString(bits));
   
   //added
   int width = this.getHostImage().getWidth();
   int height = this.getHostImage().getHeight();
   noChars.setText(Integer.toString((bits*(3*width*height))/8));
   
   

   s = new Steganography(this.getHostImage());
   try {
	s.encode(this.getSecretImage(), bits);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

   hostCanvas.setImage(s.getImage());
   hostCanvas.repaint();

//   s = new Steganography(this.getSecretImage());
//   s.getMaskedImage(bits);
//
//   secretCanvas.setImage(s.getImage());
//   secretCanvas.repaint();
  }
  else if (source == encodeBitsMinus)
  {
   int bits = this.getBits() - 1;

   if (bits < 0) { bits = 0; }
   
 //added
   int width = this.getHostImage().getWidth();
   int height = this.getHostImage().getHeight();
   noChars.setText(Integer.toString((bits*(3*width*height))/8));
   

   encodeBitsText.setText(Integer.toString(bits));

   s = new Steganography(this.getHostImage());
   try {
	s.encode(this.getSecretImage(), bits);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

   hostCanvas.setImage(s.getImage());
   hostCanvas.repaint();

//   s = new Steganography(this.getSecretImage());
//   s.getMaskedImage(bits);
//
//   secretCanvas.setImage(s.getImage());
//   secretCanvas.repaint();
  }
 }

 public ImageHiding() throws IOException
 {
  GridBagLayout layout = new GridBagLayout();
  GridBagConstraints gbc = new GridBagConstraints();
  this.setTitle("Encoding wav file");

  Container container = this.getContentPane();

  this.setLayout(layout);

  this.add(new JLabel("Bits to encode into host image:"));

  encodeBitsText = new JTextField("0", 5);
  encodeBitsText.setEditable(false);

  gbc.weightx = -1.0;
  layout.setConstraints(encodeBitsText, gbc);
  this.add(encodeBitsText);

  encodeBitsPlus = new JButton("strategyOne");
  encodeBitsPlus.addActionListener(this);

  encodeBitsMinus = new JButton("strategyTwo");
  encodeBitsMinus.addActionListener(this);

  gbc.weightx = 1.0;
  layout.setConstraints(encodeBitsPlus, gbc);
  this.add(encodeBitsPlus);

  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(encodeBitsMinus, gbc);
  this.add(encodeBitsMinus);

  GridBagLayout imageGridbag = new GridBagLayout();
  GridBagConstraints imageGBC = new GridBagConstraints();

  imagePanel = new JPanel();
  imagePanel.setLayout(imageGridbag);

  JLabel hostImageLabel = new JLabel("Host image:");
  //changed
  JLabel secretImageLabel = new JLabel("Num of characters:");

  imagePanel.add(hostImageLabel);

  imageGBC.gridwidth = GridBagConstraints.REMAINDER;
  imageGridbag.setConstraints(secretImageLabel, imageGBC);
  imagePanel.add(secretImageLabel);

  hostCanvas = new ImageCanvas(this.getHostImage());  
  //secretCanvas = new ImageCanvas(this.getSecretImage());

  imagePanel.add(hostCanvas);
  //imagePanel.add(secretCanvas);

  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(imagePanel, gbc);
  this.add(imagePanel);

  Steganography host = new Steganography(this.getHostImage());
  host.encode(this.getSecretImage(), this.getBits());
  hostCanvas.setImage(host.getImage());

  //StegByte secret = new StegByte(this.getByteArray());
  
  
  //Steganography secret = new Steganography(this.getSecretImage());
  //look here
  //secret.getMaskedImage(this.getBits());
  //secretCanvas.setImage(secret.getImage());

  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.pack();

  this.setVisible(true);
 }

 
 
 
 public static void main(String[] args) throws IOException
 {
  ImageHiding frame = new ImageHiding();
  frame.setVisible(true);
  
//  byte[] test = getByteArray();
//  System.out.println("length of file is "+test.length);
//  byteToFile(test);
  
 }

 
 
 
 
 public class ImageCanvas extends JPanel
 { 
  Image img;

  public void paintComponent(Graphics g)
  {
   g.drawImage(img, 0, 0, this);
  }

  public void setImage(Image img)
  {
   this.img = img;
  }

  public ImageCanvas(Image img)
  {
   this.img = img;

   this.setPreferredSize(new Dimension(img.getWidth(this), img.getHeight(this)));
  }
 }
}


class StegByte{
	byte[] image;
	
	//test to see if works with byte[] aray rather than image
	 public StegByte(byte[] bs)
	 {
	  this.image = bs;
	 }
}


class Steganography
{
 BufferedImage image;
 ImageHiding ih;

 public void getMaskedImage(int bits)
 {
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int maskBits = (int)(Math.pow(2, bits)) - 1 << (8 - bits);
  int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;

  for (int i = 0; i < imageRGB.length; i++)
  {
   imageRGB[i] = imageRGB[i] & mask;
  }

  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
 }

 //change to take byte array 
 public void encode(BufferedImage encodeImage, int encodeBits) throws IOException
 {
  int[] encodeRGB = encodeImage.getRGB(0, 0, encodeImage.getWidth(null), encodeImage.getHeight(null), null, 0, encodeImage.getWidth(null));
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
  int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

  int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;
  int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;

  int maskBits = (int)(Math.pow(2, 4)) - 1 << (8 - 4);
  int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;
  
  //call my getByteArray function 
  byte[] byteTxtArr = ImageHiding.getByteArray();
  System.out.println("byteTxtArr: "+ byteTxtArr);
  //store that into an int array[]
  int[] inputArr = ImageHiding.toIntArray(byteTxtArr);
  System.out.println("inputArr: "+ inputArr);
//convert this into an int array 
	 //return an int array thats ready to go
	 
	 //n is the number of encode bits that we can specify
	 int n = 4;
	 int x = 1;
	 int i = 1,o = 1;
	 int inputLen = 0;
	 inputLen = (inputArr.length * 8) / n;
	 System.out.println("input size: " + inputLen);

	 int[] outputArry = new int[inputLen];
	 System.out.println("outputArry size: "+ outputArry);
	 
	 while(x<= inputLen){
		 outputArry[o] = (inputArr[i] & mask) | outputArry[o];
		 outputArry[o] = outputArry[o] << 8;
		 inputArr[i] = inputArr[i] >>> n;
		 if(x == 4){
			 o++;
		 }
		 if(x == (8/n)){
			 i++;
		 }
//		 if((o % 4) == 0){
//			 o++;
//		 }
//		 if((i % (8/n)) == 0){
//			 i++;
//		 }
		 
		 x++;
		 System.out.println("outputArry: " + outputArry);
	 }
	 System.out.println("outputArry: " + outputArry);
  
  for (int i1 = 0; i < imageRGB.length; i++)
  {
   //int encodeData = (encodeRGB[i1] & encodeMask) >>> (8 - encodeBits);
	  //imageRGB[i1] = (imageRGB[i1] & hostMask) | (inputArr[i1] & ~hostMask);
   imageRGB[i1] = (imageRGB[i1] & hostMask) | (outputArry[i1] & ~hostMask);
   imageRGB[i1] = (imageRGB[i1] & hostMask) | outputArry[i1];
  }

  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
  
 }

 public Image getImage()
 {
  return image;
 }

 public Steganography(BufferedImage image)
 {
  this.image = image;
 }
 
 
}
