package proj1CSI526;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class Test extends JFrame implements ActionListener{
	
	public Test() {
        super("Swing JRadioButton Demo");
 
        JRadioButton option1 = new JRadioButton("Linux");
        JRadioButton option2 = new JRadioButton("Windows");
        JRadioButton option3 = new JRadioButton("Macintosh");
 
        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
 
        setLayout(new FlowLayout());
 
        add(option1);
        add(option2);
        add(option3);
 
        pack();
        
        JRadioButton optionLinux = new JRadioButton("Linux");
        JRadioButton optionWin = new JRadioButton("Win");
        JRadioButton optionMac = new JRadioButton("optionMac");
        Test actionListener = new Test();
		optionLinux.addActionListener(actionListener);
		optionWin.addActionListener(actionListener);
		optionMac.addActionListener(actionListener);
    }
	
	@Override
    public void actionPerformed(ActionEvent event) {
        JRadioButton button = (JRadioButton) event.getSource();
 
        if (button == optionLinux) {
 
            // option Linux is selected
 
        } else if (button == optionWin) {
 
            // option Windows is selected
 
        } else if (button == optionMac) {
 
            // option Macintosh is selected
        }
    }
	
	public static void main(String[] args) throws IOException{
		
		Test actionListener = new Test();
		optionLinux.addActionListener(actionListener);
		optionWin.addActionListener(actionListener);
		optionMac.addActionListener(actionListener);
		
		
		SwingUtilities.invokeLater(new Runnable() {
			 
            @Override
            public void run() {
                new Test().setVisible(true);
                JRadioButton optionLinux = new JRadioButton("Linux");
                
            }
        });
		
		
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//BufferedInputStream in = new BufferedInputStream(new FileInputStream("president_speech.wav"));
		//BufferedInputStream in = new BufferedInputStream(AudioSystem.getAudioInputStream(File "president_speech.wav"));
		//AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("president_speech.wav"));
		try 
        {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("president_speech.wav"));
			int read;
			byte[] buff = new byte[1024];
			while ((read = audioInputStream.read(buff)) > 0)
			{
			    out.write(buff, 0, read);
			}
			out.flush();
			byte[] audioBytes = out.toByteArray();
			System.out.println("Test");
        }
        catch(Exception e)
        {
            System.out.println("Error trying to get wav file");
            e.printStackTrace( );
        }
		System.out.println("Test");
		
		
		
		
	}
	
	
	
}
