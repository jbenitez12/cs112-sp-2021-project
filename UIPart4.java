package CS112ProjectP2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

public class UIPart4 extends JPanel {

    private static final long serialVersionUID = 1L;
    private int labelPadding = 40;
    private Color lineColor = new Color(255, 255, 254);

    private Color pointColor = new Color(55, 0, 255);
    private Color blue = new Color(0,0,255);
    private Color cyan = new Color(0, 255, 255);
    private Color yellow = new Color(255,255,0);
    private Color red = new Color(255,0,0);
    
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    private static int pointWidth = 5;

    private int numXGridLines = 6;
    private int numYGridLines = 6;
    private int padding = 40;

    private List<DataPoint> data;
	private int K;


    public void Graph(int K, String fileName) {
    	this.K = K;
    	
    	Predictor obj = new KNNPredictor(K);
    	this.data = obj.readData(fileName);
       

		
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minF1 = getMinF1Data();
        double maxF1 = getMaxF1Data();
        double minF2 = getMinF2Data();
        double maxF2 = getMaxF2Data();

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - 
        		labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);

        double yGridRatio = (maxF2 - minF2) / numYGridLines;
        for (int i = 0; i < numYGridLines + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
            		labelPadding)) / numYGridLines + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = String.format("%.2f", (minF2 + (i * yGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        double xGridRatio = (maxF1 - minF1) / numXGridLines;
        for (int i = 0; i < numXGridLines + 1; i++) {
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numXGridLines) + padding + labelPadding;
            int x1 = x0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                String xLabel = String.format("%.2f", (minF1 + (i * xGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
        		padding, getHeight() - padding - labelPadding);

        paintPoints(g2, minF1, maxF1, minF2, maxF2);
    }

    private void paintPoints(Graphics2D g2, double minF1, double maxF1, double minF2, double maxF2) {
    	Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        double xScale = ((double) getWidth() - (3 * padding) - labelPadding) /(maxF1 - minF1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxF2 - minF2);
        g2.setStroke(oldStroke);
        for (int i = 0; i < data.size(); i++) {
            int x1 = (int) ((data.get(i).getF1() - minF1) * xScale + padding + labelPadding);
            int y1 = (int) ((maxF2 - data.get(i).getF2()) * yScale + padding);
            int x = x1 - pointWidth / 2;
            int y = y1 - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            
                        
    		String labeldata = data.get(i).getLabel();

    		String lol = test(this.data.get(i));
    		if(lol == null) {	
    			
    		}
    		else if(lol.equals("1") && labeldata.equals("1")) {
    			 g2.setColor(blue);
    		}
    		else if(lol.equals("1") && labeldata.equals("0")) {
    			 g2.setColor(cyan);
    		}
    		else if(lol.equals("0") && labeldata.equals("1")) {
    			 g2.setColor(yellow);
    		}
    		else if(lol.equals("0") && labeldata.equals("0")) {
    			 g2.setColor(red);
    		}
            g2.fillOval(x, y, ovalW, ovalH);
        }
         

    }


	private String test(DataPoint dataPoint) {
		String most = null;
		int totaltest = 0;
		int totaltrain = 0;
		ArrayList<DataPoint> train = new ArrayList<DataPoint>();
		ArrayList<DataPoint> test = new ArrayList<DataPoint>();
		
		if(dataPoint.getTest() == true) {
			for(int i = 0; i < this.data.size(); i++) {
				if(this.data.get(i).getTest() == false) {
					train.add(this.data.get(i));	
					}
				else if(this.data.get(i).getTest() == true) {
					test.add(this.data.get(i));
					}
				}
			Double[][] arr = new Double[train.size()][2];
			for(int i = 0; i < train.size(); i++) {
				DataPoint data1 = train.get(i);
				double fin = getDistance(dataPoint, data1);
				arr[i][0] = fin;
				if(this.data.get(i).getLabel().equals("1")) {
					arr[i][1] = 1.0;
					}
				else if(this.data.get(i).getLabel().equals("0")) {
					arr[i][1] = 0.0;
					}
			}
			java.util.Arrays.sort(arr,new java.util.Comparator<Double[]>(){
				public int compare(Double[]a,Double[]b){
					return a[0].compareTo(b[0]);
					}
				});
			
			for(int i = 0; i < this.K ; i ++) {
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
		else if(dataPoint.getTest() == false) {
			}
		return most;
	}

	private double getDistance(DataPoint dataPoint, DataPoint data1) {
		double x1 = dataPoint.getF1();
		double y1 = dataPoint.getF2();
		double x2 = data1.getF1();
		double y2 = data1.getF2();
		return Math.sqrt(Math.pow(x2-x1, 2.0)+Math.pow(y2-y1,2.0));
		
	}

    private double getMinF1Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF1());
        }
        return minData;
    }

    private double getMinF2Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF2());
        }
        return minData;
    }


    private double getMaxF1Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF1());
        }
        return maxData;
    }

    private double getMaxF2Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF2());
        }
        return maxData;
    }

    public void setData(List<DataPoint> data) {
        this.data = data;
        invalidate();
        this.repaint();
    }

    public List<DataPoint> getData() {
        return data;
    }

    private static void createAndShowGui(int K, String fileName) {

        Graph mainPanel = new Graph(K, fileName);        
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
	public void stateChanged(ChangeEvent e) {
		
	}
    public static void main(String[] args) {
        int K = 33;    
        String fileName = "titanic.csv"; 
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui(K, fileName);
            }
        });
    }
}
