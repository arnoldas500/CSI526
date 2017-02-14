package proj1CSI526;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.*;
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

   s = new Steganography(this.getHostImage());
   s.encode(this.getSecretImage(), bits);

   hostCanvas.setImage(s.getImage());
   hostCanvas.repaint();

   s = new Steganography(this.getSecretImage());
   s.getMaskedImage(bits);

   secretCanvas.setImage(s.getImage());
   secretCanvas.repaint();
  }
  else if (source == encodeBitsMinus)
  {
   int bits = this.getBits() - 1;

   if (bits < 0) { bits = 0; }

   encodeBitsText.setText(Integer.toString(bits));

   s = new Steganography(this.getHostImage());
   s.encode(this.getSecretImage(), bits);

   hostCanvas.setImage(s.getImage());
   hostCanvas.repaint();

   s = new Steganography(this.getSecretImage());
   s.getMaskedImage(bits);

   secretCanvas.setImage(s.getImage());
   secretCanvas.repaint();
  }
 }

 public ImageHiding()
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
  JLabel secretImageLabel = new JLabel("Secret image:");

  imagePanel.add(hostImageLabel);

  imageGBC.gridwidth = GridBagConstraints.REMAINDER;
  imageGridbag.setConstraints(secretImageLabel, imageGBC);
  imagePanel.add(secretImageLabel);

  hostCanvas = new ImageCanvas(this.getHostImage());  
  secretCanvas = new ImageCanvas(this.getSecretImage());

  imagePanel.add(hostCanvas);
  imagePanel.add(secretCanvas);

  gbc.gridwidth = GridBagConstraints.REMAINDER;
  layout.setConstraints(imagePanel, gbc);
  this.add(imagePanel);

  Steganography host = new Steganography(this.getHostImage());
  host.encode(this.getSecretImage(), this.getBits());
  hostCanvas.setImage(host.getImage());

  Steganography secret = new Steganography(this.getSecretImage());
  //look here
  secret.getMaskedImage(this.getBits());
  secretCanvas.setImage(secret.getImage());

  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.pack();

  this.setVisible(true);
 }

 
 
 
 public static void main(String[] args) throws IOException
 {
  ImageHiding frame = new ImageHiding();
  frame.setVisible(true);
  
  byte[] test = getByteArray();
  byteToFile(test);
  
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

 public void encode(BufferedImage encodeImage, int encodeBits)
 {
  int[] encodeRGB = encodeImage.getRGB(0, 0, encodeImage.getWidth(null), encodeImage.getHeight(null), null, 0, encodeImage.getWidth(null));
  int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

  int encodeByteMask = (int)(Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
  int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

  int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;
  int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;

  for (int i = 0; i < imageRGB.length; i++)
  {
   int encodeData = (encodeRGB[i] & encodeMask) >>> (8 - encodeBits);
   imageRGB[i] = (imageRGB[i] & hostMask) | (encodeData & ~hostMask);
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
