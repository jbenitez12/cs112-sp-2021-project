package CS112ProjectP2;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Math;

public class KNNPredictor extends Predictor {
	private int k;
	private boolean testortrain;
	private int trainnum = 0;
	private int testnum = 0;
	private ArrayList<DataPoint> testrain = new ArrayList<DataPoint>();
	public KNNPredictor(int k) {
		this.k = k;
	
	}
	private List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		try(Scanner rowScanner = new Scanner(line)){
			rowScanner.useDelimiter(",");
			while(rowScanner.hasNext()){
				values.add(rowScanner.next());
				values.remove("age");
				values.remove("sex");
				values.remove("survived");
				values.remove("pclass");
				values.remove("fare");
				values.remove("name");
			}
		}
		return values;
	}
		
	@Override
	ArrayList<DataPoint> readData(String filename) {
		try(Scanner scanner = new Scanner(new File(filename));){
			while(scanner.hasNextLine()) {
				List<String> records = getRecordFromLine(scanner.nextLine());
				Random rand = new Random();
				double randNum = rand.nextDouble();
				if(randNum < 0.9) {
					testortrain = false;
					trainnum = trainnum +1;
					for(int i = 0; i <= records.size(); i ++) {
						if (i == 5) {			
							String survive = records.get(1);	
							if(records.get(5).isEmpty()){
								double x = 0;
								String s1 = records.get(6);
								double y = Double.parseDouble(s1);
								DataPoint hello = new DataPoint(x,y,survive, testortrain);
								testrain.add(hello);
							}
							else {
								String s = records.get(5);
								double x = Double.parseDouble(s);
								if(records.size() < 7) {
									double y = 0;
									DataPoint hello = new DataPoint(x,y,survive, testortrain);
									testrain.add(hello);
								}
								else {
									String s1 = records.get(6);
									double y = Double.parseDouble(s1);
									DataPoint hello = new DataPoint(x,y,survive, testortrain);
									testrain.add(hello);
							}
						}
					}
				}
					
				}
				else {
					testortrain = true;
					for(int i = 0; i <= records.size(); i ++) {
						if (i == 5) {
							String survive = records.get(1);
							testnum = testnum +1;
							if(records.get(5).isEmpty()){
								double x = 0;
								String s1 = records.get(6);
								double y = Double.parseDouble(s1);
								DataPoint hello = new DataPoint(x,y,survive, testortrain);
								testrain.add(hello);
							}
							else {
								String s = records.get(5);
								double x = Double.parseDouble(s);
								if(records.size() < 7) {
									double y = 0;
									DataPoint hello = new DataPoint(x,y,survive, testortrain);
									testrain.add(hello);
								}
								else {
									String s1 = records.get(6);
									double y = Double.parseDouble(s1);
									DataPoint hello = new DataPoint(x,y,survive, testortrain);
									testrain.add(hello);
							}
						}
					}
				}
			}
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println("Total amount of passengers (Train): " + trainnum); //Will print train size but was commented to present data for Graph.java and UIPart4.java
		
		return testrain;
		
	}
	private double getDistance(DataPoint p1, DataPoint p2){
		double x1 = p1.getF1();
		double y1 = p1.getF2();
		double x2 = p2.getF1();
		double y2 = p2.getF2();
		return Math.sqrt(Math.pow(x2-x1, 2.0)+Math.pow(y2-y1,2.0));
		
	}

	@Override
	String test(DataPoint data) {
		String most = null;
		int totaltest = 0;
		int totaltrain = 0;
		ArrayList<DataPoint> train = new ArrayList<DataPoint>();
		ArrayList<DataPoint> test = new ArrayList<DataPoint>();
		
		if(data.getTest() == true) {
			for(int i = 0; i < testrain.size(); i++) {
				if(testrain.get(i).getTest() == false) {
					train.add(testrain.get(i));	
					}
				else if(testrain.get(i).getTest() == true) {
					test.add(testrain.get(i));
					}
				}
			Double[][] arr = new Double[train.size()][2];
			for(int i = 0; i < train.size(); i++) {
				DataPoint data1 = train.get(i);
				double fin = getDistance(data, data1);
				arr[i][0] = fin;
				if(testrain.get(i).getLabel().equals("1")) {
					arr[i][1] = 1.0;
					}
				else if(testrain.get(i).getLabel().equals("0")) {
					arr[i][1] = 0.0;
					}
			}
			java.util.Arrays.sort(arr,new java.util.Comparator<Double[]>(){
				public int compare(Double[]a,Double[]b){
					return a[0].compareTo(b[0]);
					}
				});
			
			for(int i = 0; i < k ; i ++) {
				if(arr[i][1] == 0.0) {
					totaltest = totaltest + 1;
				}
				else if(arr[i][1] == 1.0) {
					totaltrain = totaltrain + 1;
				}
					
			}
			if(totaltest > totaltrain) {
				most = "0";
			}
			else if(totaltest < totaltrain) {		
				most = "1";
			}
		}
		else if(data.getTest() == false) {
			}
		return most;
	}


	@Override
	Double getAccuracy(ArrayList<DataPoint> data) {
		Double truePositive = 0.0;
		Double falsePositive = 0.0;
		Double trueNegative = 0.0;
		Double falseNegative = 0.0;
		for(int i = 0; i < data.size(); i++) {
			String labeldata = data.get(i).getLabel();
			String lol = test(data.get(i));
			if(lol == null) {	
				
			}
			else if(lol.equals("1") && labeldata.equals("1")) {
				truePositive = truePositive +1;
			}
			else if(lol.equals("1") && labeldata.equals("0")) {
				falsePositive =falsePositive + 1;
			}
			else if(lol.equals("0") && labeldata.equals("1")) {
				falseNegative = falseNegative + 1;
			}
			else if(lol.equals("0") && labeldata.equals("0")) {
				trueNegative = trueNegative + 1;
				
			}
			}
		return (truePositive + trueNegative)/(truePositive + trueNegative + falsePositive + falseNegative);
	}

	@Override
	Double getPercision(ArrayList<DataPoint> data) {
		Double truePositive = 0.0;
		Double falseNegative = 0.0;
		for(int i = 0; i < data.size(); i++) {			
			String labeldata = data.get(i).getLabel();
			String lol = test(data.get(i));
			if(lol == null) {		
			}
			else if(lol.equals("1") && labeldata.equals("1")) {
				truePositive = truePositive +1;
			}
			else if(lol.equals("0") && labeldata.equals("1")) {
				falseNegative = falseNegative + 1;
				
			}
			
		}
		return truePositive/(truePositive+falseNegative);
	}

}
