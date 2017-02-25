//Arnoldas Kurbanovas

//matlab code for reading in .wav file and extracting the audio and frequncey smaples
//and then code for saving the audio samples as a text file that gets read into java
/*
[y,fs] = audioread('C:/Users/akurb/Desktop/president_speech.wav');
%variable y holds the samples of the audio file
%variable fs holds the samling frequency
t=linspace(0,length(y)/fs,length(y));
%linspace is a built in function that creates the time vector
%parameter 0 is the starting time, length y/fs is teh ending time
%and length y is the number of samples in y
plot(t,y);
%the x axis is the time in seconds which is 1min22sec or 82sec
		 */

//matlab code to read in the text file that was created by java
//and then copnvert it back to a .wav file to be able to play it back
/*
A = importdata('G:\Users\akurb\workspace\proj1CSI526\output.txt');
audiowrite('G:\Users\akurb\workspace\proj1CSI526\output.wav',A,fs);
 */

package proj1CSI526;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class ImageHiding extends JFrame implements ActionListener
{
 BufferedImage hostImage;


 JPanel controlPanel;
 JPanel imagePanel;

 
 static JTextField encodeBitsText;
 JButton encodeBitsPlus;
 JButton encodeBitsMinus;
 JButton strategy2;
 JButton strategy1;
 
 static JTextField seconds;
 JTextField audioTime;

 JTextField nBitsText;
 JButton nBitsPlus;
 JButton nBitsMinus;

 ImageCanvas hostCanvas;
 ImageCanvas secretCanvas;

 static Steganography s;
 static int[] imageArray;
 static byte[] imageByte;

 public static BufferedImage getHostImage()
 {
  BufferedImage img = null;

  try
  {
   img = ImageIO.read(new File("host_image.JPG"));
  }
  catch (IOException ioe) { ioe.printStackTrace(); }

  return img;
 }
 
 /*
 //method i created to get the path to the .wav file 
 //then i convert the file from a byte array output stream
 //to a byte array and return that from my method
 public byte[] getByteArray() throws IOException, UnsupportedAudioFileException{
	 ByteArrayOutputStream out = new ByteArrayOutputStream();
		//BufferedInputStream in = new BufferedInputStream(new FileInputStream("president_speech.wav"));
		//BufferedInputStream in = new BufferedInputStream(AudioSystem.getAudioInputStream(File "president_speech.wav"));
		//AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("president_speech.wav"));
		
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("president_speech.wav"));
		int read;
		byte[] buff = new byte[1024];
		while ((read = audioInputStream.read(buff)) > 0)
		{
		    out.write(buff, 0, read);
		}
		out.flush();
		byte[] dataBytes = out.toByteArray();
		System.out.println("Test");
     
		System.out.println("size of dataBytes: "+dataBytes.length);
		return dataBytes;
 }
 */
 
 
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
 
 
//method i crteated to get the path to text file of the .wav file
//then conver the text file into a byte array 
 public static byte[] getByteArray() throws IOException 
 {
	 Path path = Paths.get("pres_speech.txt");
	 byte[] data = Files.readAllBytes(path);
	 return data;
	 //used to make data smaller
	 /*
	 byte[] newByte = new byte[data.length];
	 
//	 for(int i=0; i<data.length; i++){
//		 newByte[k] = data[i];
//	 }
	 
	 int k =0;
	 //method to reduce the file in half by just taking every other value
	 for(int i=0; i<data.length; i++){
		 for(int j=0; j<18; j++){
			 newByte[k] = data[i];
			 i++;
			 k++;
		 }
		 i=i+17;
	 }
	 
	 return newByte;
	 */
	 
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
 
 
 
 public static int getBits()
 {
  return Integer.parseInt(encodeBitsText.getText())+1;
 }
 
 
 
 public void actionPerformed(ActionEvent event)
 {
  Object source = event.getSource();
  
  if(source == strategy1){
	  byte[] bytArr = null;
	   byte[] byte1 = null;
	try {
		byte1 = new byte[getByteArray().length];
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
		try {
			bytArr =  getByteArray();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	   System.out.println("bytArr(): "+ bytArr);
	   
	   for (int i = 0; i < Steganography.getEncodedSize(); i++)
	   {
		   byte1[i] = bytArr[i];
	 	 
	   }
	   
	   byteToFile(byte1);
	   System.out.println("length of byte1: "+byte1.length);
  }
  
  if(source == strategy2){
	  double sec = Steganography.getSeconds();
	   System.out.println("sec "+ sec);
	   seconds.setText(Double.toString(sec)); 
  }
  
  if (source == encodeBitsPlus)
  {
   int bits = ImageHiding.getBits() + 1;

   if (bits > 8) { bits = 8; }

   encodeBitsText.setText(Integer.toString(bits));
   
  
   
   s = new Steganography(ImageHiding.getHostImage());
   try {
	s.encode(this.getByteArray(), bits);
//	double sec = Steganography.getSeconds();  
//	seconds.setText(Double.toString(sec));
	System.out.println("getByteArray(): "+ this.getByteArray());
	System.out.println("Length of byte array: "+ this.getByteArray().length);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

   hostCanvas.setImage(s.getImage());
   hostCanvas.repaint();
   
   
   

 //  s = new Steganography(this.getSecretImage());
 //  s.getMaskedImage(bits);

 //  secretCanvas.setImage(s.getImage());
//   secretCanvas.repaint();
  }
  else if (source == encodeBitsMinus)
  {
   
	  
   
   int bits = ImageHiding.getBits() - 1;  // text bits

   if (bits < 1) { bits = 1; }
   
   //noChars.setText(Integer.toString((bits*(3*width*height))/8));  // based on formula given in class.

   encodeBitsText.setText(Integer.toString(bits));

   s = new Steganography(ImageHiding.getHostImage());
   try {
	s.encode(this.getByteArray(), bits);
	System.out.println("getByteArray(): "+this.getByteArray());
	System.out.println("Length of byte array: "+ this.getByteArray().length);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

   hostCanvas.setImage(s.getImage());
   hostCanvas.repaint();

  }
 }

 public ImageHiding() throws UnsupportedAudioFileException
 {
  GridBagLayout layout = new GridBagLayout();
  GridBagConstraints gbc = new GridBagConstraints();
  this.setTitle("Encoding wav file");

  Container container = this.getContentPane();

  this.setLayout(layout);

  this.add(new JLabel("Bits to encode into host image:"));

  encodeBitsText = new JTextField("0", 5);
  encodeBitsText.setEditable(false);
  
  
  
  
  
  seconds = new JTextField("0", 10);
  seconds.setEditable(false);
  //seconds.setText(t);
  

  gbc.weightx = -1.0;
  layout.setConstraints(encodeBitsText, gbc);
  this.add(encodeBitsText);
   
  gbc.weightx = 1.0;
  layout.setConstraints(seconds, gbc);
  this.add(seconds);

  encodeBitsPlus = new JButton("+");
  encodeBitsPlus.addActionListener(this);

  encodeBitsMinus = new JButton("-");
  encodeBitsMinus.addActionListener(this);

  strategy1 = new JButton("strategy1");
  strategy1.addActionListener(this);
  
  gbc.weightx = 1.0;
  layout.setConstraints(strategy1, gbc);
  this.add(strategy1);
  
  strategy2 = new JButton("strategy2");
  strategy2.addActionListener(this);
  
  gbc.weightx = 1.0;
  layout.setConstraints(strategy2, gbc);
  this.add(strategy2);
  
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
  JLabel secretImageLabel = new JLabel("length of the audio encoded: ");   //added JLabel

  imagePanel.add(hostImageLabel);

  imageGBC.gridwidth = GridBagConstraints.REMAINDER;
  imageGridbag.setConstraints(secretImageLabel, imageGBC);
  imagePanel.add(secretImageLabel);

  hostCanvas = new ImageCanvas(ImageHiding.getHostImage());  


  imagePanel.add(hostCanvas);


  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(imagePanel, gbc);
  this.add(imagePanel);

  Steganography host = new Steganography(ImageHiding.getHostImage());
  try {
	host.encode(this.getByteArray(), ImageHiding.getBits());
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
  hostCanvas.setImage(host.getImage());


  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.pack();

  this.setVisible(true);
 }

 
 
 public static void main(String[] args) throws UnsupportedAudioFileException, IOException
 {
  ImageHiding frame = new ImageHiding();
  frame.setVisible(true);
  //File file = ...;
  //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("president_speech.wav"));
 // imageArray;

  AudioFormat format = audioInputStream.getFormat();
  long frames = audioInputStream.getFrameLength();
  double durationInSeconds = (frames+0.0) / format.getFrameRate();  
  System.out.println("audio file time: "+ durationInSeconds);
  
  
  //System.out.println("getimageArray: "+ Steganography.getimageArray());
  
  
  
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

class Steganography
{
 BufferedImage image;
 static int[] imageArray;
 static byte[] imageByte = new byte[9999999];
 static double seconds = 0;
 static int encodedSize = 0;
 //static byte[] imageByte = new byte[ImageHiding.getByteArray().length];
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

 public void encode(byte[] wavByteArry, int encodeBits) throws FileNotFoundException   // encode will take in a byte array (text) instead of originally (BufferedImage)
 {
   //byte[] imageByte = new byte[wavByteArry.length];
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));
  //System.out.println("wavByteArry: "+wavByteArry.length);
  
  int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
  int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

  int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;
  int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;
  System.out.println(imageRGB.length);
  
  System.out.println("encodeBits: "+ encodeBits);
  int maskBits = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
  int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;
  
  //call my getByteArray function 
  byte[] byteTxtArr = wavByteArry;
  System.out.println("byteTxtArr: "+ byteTxtArr.length);
  //store that into an int array[]
  int[] inputArr = ImageHiding.toIntArray(byteTxtArr);
  System.out.println("inputArr: "+ inputArr.length);
//convert this into an int array 
	 //return an int array thats ready to go
	 
	 //n is the number of encode bits that we can specify
	 int n = encodeBits;
	 int x = 1;
	 int i = 1,o = 1;
	 int inputLen = 0;
	 
	 inputLen = (inputArr.length ) ;
	 System.out.println("input size: " + inputLen);

	 int[] outputArry = new int[inputLen*(8/n)];
	 System.out.println("outputArry size: "+ outputArry.length);
	 
	 while(x< inputLen ){
		 outputArry[o] = (inputArr[i] & mask) | outputArry[o];
		 outputArry[o] = outputArry[o] << 8;
		 inputArr[i] = inputArr[i] >>> n;
		 if((x % 4) == 0){
			 o++;
		 }
		 if((x % (8/n)) == 0){
			 i++;
		 }
		 x++;
		 //System.out.println("outputArry: " + outputArry);
	 }
	 
	 
	 seconds = ((i)*(0.00000504));
	 
	 encodedSize = i;
	 
	 System.out.println("value of i: " + i);
	 System.out.println("Seconds encoded: " + seconds);
//	 
//	 while(x<= inputLen){
//		 outputArry[o] = (wavByteArry[i] & mask) | outputArry[o];
//		 outputArry[o] = outputArry[o] << 8;
//		 wavByteArry[i] = (byte) (wavByteArry[i] >>> n);
//		 if((x % 4) == 0){
//			 o++;
//		 }
//		 if((x % (8/n)) == 0){
//			 i++;
//		 }
//	 }
  
  
  
  
  for (int i1 = 0; i1 < imageRGB.length; i1++)
  {
	  /*
	  imageByte[i1] = wavByteArry[i1];
	 
	  //System.out.println("imageByte[i1]: "+imageByte[i1]);
	  int encodeData = (wavByteArry[i1] & encodeMask) >>> (8 - encodeBits);   // Will Encode the text byte array..
  //System.out.println("encodeData: "+encodeData);
	
   imageRGB[i1] = (imageRGB[i1] & hostMask) | (encodeData & ~hostMask);   // stored into imageRGB[i]...
   //System.out.println("imageRGB[i1]: "+(byte)imageRGB[i]);
   */
   //changes for using int output array
   //int encodeData = (encodeRGB[i1] & encodeMask) >>> (8 - encodeBits);
	  
   //imageRGB[i1] = (imageRGB[i1] & hostMask) | (outputArry[i1] & ~hostMask);
   imageRGB[i1] = (imageRGB[i1] & hostMask) | outputArry[i1];
  }
  System.out.println("imageRGB[i] size: "+ imageRGB.length);
  imageArray = imageRGB;
  System.out.println("imageByte[i]: "+imageByte);
  
  //ImageHiding.byteToFile(getimageByte());
  
  
  
  image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
  int[] imageRGB2 = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
  System.out.println("imageRGB2: "+imageRGB2);
  PrintWriter pr = new PrintWriter("intoutput.txt");        
  for (int j=0; j<imageRGB2.length ; j++){            
  pr.println("" + imageRGB2[j]);        
  }        
  pr.close();
  
  ByteBuffer byteBuffer = ByteBuffer.allocate(imageArray.length * 4);        
  IntBuffer intBuffer = byteBuffer.asIntBuffer();
  intBuffer.put(imageArray);
/*
  byte[] array = byteBuffer.array();

  for (int i=0; i < array.length; i++)
  {
      //System.out.println(i + ": " + array[i]);
  }
  
  */
  
 }
 public static int getEncodedSize(){
	 return encodedSize;
 }
 
 public static double getSeconds(){
	 
	return seconds;
 }
 
 public static int[] getimageArray(){
	 
	 return imageArray;
 }
 
 public static byte[] getimageByte(){
	 System.out.println("imageByte[i]: "+imageByte);
	 return imageByte;
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