package CS112ProjectP2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class Sliderclass implements ChangeListener{
	
	JPanel panel;
	JLabel label;
	JSlider slider;
	JButton button;
	
	Sliderclass(JFrame frame){
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel sliderLabel = new JLabel("Choose the majority value");
		sliderLabel.setHorizontalAlignment(JLabel.CENTER);
		slider = new JSlider(2,25);
		
        label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        
        slider.setPreferredSize(new Dimension(150,100));
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(1);
        
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(5);
        
        slider.setPaintLabels(true);
        
        slider.setFont(new Font("MV Boli",Font.PLAIN,15));
        
        slider.setSnapToTicks(true);
        
		
        
		label.setText("K = " + slider.getValue());
        slider.addChangeListener(this);
          	
    	button = new JButton("Run Test");
    	button.addActionListener(new ActionListener() {
    		 public void actionPerformed(ActionEvent e) {
    			 if(e.getSource()==button) {
    				 frame.setVisible(false);
    				 int num = (slider.getValue()+ slider.getValue())+1;
    				 System.out.println("K Value: " + slider.getValue());
    				 System.out.println("New K Value: " + num);

    				 Predictor obj = new KNNPredictor(num);
    				 ArrayList<DataPoint> x = obj.readData("titanic.csv");
    				 long acura = Math.round(100*obj.getAccuracy(x));
    				 long integra = Math.round(100*obj.getPercision(x));
    				 System.out.println("Accuracy: " + acura + "%" + " " + "Percision: " + integra + "%");
    				 
    				 Graph mainPanel = new Graph(num, "titanic.csv"); 
    			     mainPanel.setPreferredSize(new Dimension(900, 700));
    			     JFrame frame = new JFrame("CS 112 Lab Part 3");
    			     
    			     frame.setLayout(new BorderLayout());
    			     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    			     frame.add(mainPanel, BorderLayout.NORTH);  
    			     Sliderclass slidz = new Sliderclass(frame);
    			     frame.pack();
    			     frame.setLocationRelativeTo(null);
    			     frame.setVisible(true);
    			 }					
             }
         });
    	
        panel.add(sliderLabel,BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        panel.add(slider,BorderLayout.SOUTH);
        panel.add(label,BorderLayout.WEST);
        frame.add(panel);
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		label.setText("K = " + slider.getValue());
		
	}
	
}
