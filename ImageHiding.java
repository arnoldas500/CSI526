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
 
 //static JTextField noChars;
 JTextField audioTime;

 JTextField nBitsText;
 JButton nBitsPlus;
 JButton nBitsMinus;

 ImageCanvas hostCanvas;
 ImageCanvas secretCanvas;

 Steganography s;

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
 
 
//method i crteated to get the path to text file of the .wav file
//then conver the text file into a byte array 
 public byte[] getByteArray() throws IOException 
 {
	 Path path = Paths.get("pres_speech.txt");
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
  return Integer.parseInt(encodeBitsText.getText());
 }
 
 
 
 public void actionPerformed(ActionEvent event)
 {
  Object source = event.getSource();

  if (source == encodeBitsPlus)
  {
   int bits = ImageHiding.getBits() + 1;

   if (bits > 8) { bits = 8; }

   encodeBitsText.setText(Integer.toString(bits));
   
   
   
   
   //noChars.setText(Integer.toString((bits*(3*width*height))/8));  // number of characters to string
   

   s = new Steganography(ImageHiding.getHostImage());
   try {
	s.encode(this.getByteArray(), bits);
	System.out.println("getByteArray(): "+getByteArray());
	System.out.println("Length of byte array: "+ getByteArray().length);
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

   if (bits < 0) { bits = 0; }
   
   //noChars.setText(Integer.toString((bits*(3*width*height))/8));  // based on formula given in class.

   encodeBitsText.setText(Integer.toString(bits));

   s = new Steganography(ImageHiding.getHostImage());
   try {
	s.encode(this.getByteArray(), bits);
	System.out.println("getByteArray(): "+getByteArray());
	System.out.println("Length of byte array: "+ getByteArray().length);
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
  
  //noChars = new JTextField("0", 5);
  //noChars.setEditable(false);
  

  gbc.weightx = -1.0;
  layout.setConstraints(encodeBitsText, gbc);
  this.add(encodeBitsText);
   
  //gbc.weightx = 1.0;
  //layout.setConstraints(noChars, gbc);
  //this.add(noChars);

  encodeBitsPlus = new JButton("+");
  encodeBitsPlus.addActionListener(this);

  encodeBitsMinus = new JButton("-");
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


  AudioFormat format = audioInputStream.getFormat();
  long frames = audioInputStream.getFrameLength();
  double durationInSeconds = (frames+0.0) / format.getFrameRate();  
  System.out.println("audio file time: "+ durationInSeconds);
  //void numChars = noChars.setText(Integer.toString((getBits()*(3*width*height))/8));
  //System.out.println("noChars: "+ );
  
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

 public void encode(byte[] wavByteArry, int encodeBits)   // encode will take in a byte array (text) instead of originally (BufferedImage)
 {
   
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));
  //System.out.println("wavByteArry: "+wavByteArry.length);
  
  int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
  int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

  int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;
  int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;

  for (int i = 0; i < imageRGB.length; i++)
  {
    int encodeData = ((char)wavByteArry[i] & encodeMask) >>> (8 - encodeBits);   // Will Encode the text byte array..
  //System.out.println("encodeData: "+encodeData);
	
   imageRGB[i] = (imageRGB[i] & hostMask) | (encodeData & ~hostMask);   // stored into imageRGB[i]...
   //System.out.println("imageRGB[i]: "+imageRGB[i]);
  }
  System.out.println("imageRGB[i]: "+imageRGB);
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