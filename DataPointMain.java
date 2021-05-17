package CS112ProjectP2;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class DataPointMain {

	public static void main(String[] args) {
		Predictor obj = new KNNPredictor(437);
		ArrayList<DataPoint> x = obj.readData("titanic.csv");

		
		JFrame myFrame = new JFrame("CS112 PROJECT PART 1");
		Container contentPane = myFrame.getContentPane();
		
		contentPane.setLayout(new GridLayout(2,0));
		JButton button = new JButton("Get Accuracy: " + Math.round(100*obj.getAccuracy(x))+"%");
		
		button.addActionListener(e -> System.out.println(Math.round(100*obj.getAccuracy(x))+"%"));
		contentPane.add(button);
		
		JButton button1 = new JButton("Get Percision: " + Math.round(100*obj.getPercision(x))+"%");
		
		button1.addActionListener(e -> System.out.println(Math.round(100*obj.getPercision(x))+"%"));
		contentPane.add(button1);

		myFrame.setPreferredSize(new Dimension(600,600));
		myFrame.pack();
		myFrame.setVisible(true);
	}


}
