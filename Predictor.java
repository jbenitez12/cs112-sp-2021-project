package CS112ProjectP2;

import java.util.ArrayList;



public abstract class Predictor extends DataPoint {
	abstract ArrayList<DataPoint> readData(String filename);
	abstract String test(DataPoint data);
	abstract Double getAccuracy(ArrayList<DataPoint> data);
	abstract Double getPercision(ArrayList<DataPoint> data);
}
