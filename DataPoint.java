package CS112ProjectP2;

public class DataPoint {
	private double f1;
	private double f2;
	private String label;
	private boolean isTest;
	
	public DataPoint(Double f, Double ff, String label,Boolean isTest) {
		this.f1 = f;
		this.f2 = ff;
		this.label = label;	
		this.isTest = isTest;
	
	}
	public DataPoint() {
		this.f1 = 0;
		this.f2 = 0;
		this.label = null;
		this.isTest = false;
	}
	
	public Double getF1() {
		return this.f1;
	}
	public Double getF2() {
		return this.f2;
	}

	public String getLabel() {
		return this.label;
	}
	public Boolean getTest() {
		return this.isTest;
	}
	
	public void setF1(Double f) {
		if(f<0) {
			return;
		}
		this.f1 =f;
	}
	public void setF2(Double ff) {
		if(ff<0) {
			return;
		}
		this.f2 =ff;
	}
	public void setTest(Boolean isTest) {
		return;
	}
	private void setLabel(String label) {
		if(!(label.equals("Good")||label.equals("Bad"))) {
			
		}
		this.label = label;
	}
	  public String toString() {
		    return  "("+this.f1 + ", " + this.f2+")";
		  }	
}