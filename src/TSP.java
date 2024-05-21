import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//Graphics code here
public class TSP extends JFrame
{
	String in;
	public String[] s;
	
	public TSP(String title)
	{
	  super(title);
	  this.setSize(800,540);
	  this.setLocation(250,50);
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	  //image file inserted
	  JLabel map = new JLabel(new ImageIcon("map.png"));
	  map.setBounds(-60, 0, 700, 700);
	  
	  //left panel of jframe for output text field
	  JPanel leftPanel = new JPanel();
	  leftPanel.setBackground(Color.BLUE);
	  leftPanel.setBounds(0, 0, 200, 400);
	  leftPanel.setLayout(null);
	  
	 //right panel of jframe for image file
	  JPanel rightPanel = new JPanel();
	  rightPanel.setBackground(Color.RED);
	  rightPanel.setBounds(212, 0, 587, 400);
	  rightPanel.setLayout(null);
	  
	  //html string "output here" to indicate what the text field below will contain
	  final String formattedHTMLString = "<html>Output here </html>";
	  JLabel info = new JLabel(formattedHTMLString);
	  info.setBounds(50, -45, 175, 120);
  	  info.setFont(new Font("SansSerif", Font.BOLD, 18));
  	  info.setFocusable(false);
  	  info.setForeground(Color.BLACK);
  	  
  	  //Text Area for the output solution to be copied 
  	  JTextArea output = new JTextArea();
  	  output.setBounds(5, 30, 200, 365);
	  output.setFont(new Font("SansSerif", Font.BOLD, 16));
	  output.setFocusable(true);
	  output.setLineWrap(true);
	  output.setWrapStyleWord(true);
	  output.setBorder(BorderFactory.createBevelBorder(1));
	  output.setEditable(false);
	  
	  //Text Area for the input to be pasted
  	  TextArea input = new TextArea();
  	  input.setBounds(10, 10, 580, 80);
  	  input.setFont(new Font("SansSerif", Font.BOLD, 16));
   	  input.setFocusable(true);
	  
   	  //button to compute the tsp app 
	  JButton button1 = new JButton("Compute");
	  button1.setBounds(610, 20, 140, 60);
	  button1.setBackground(Color.YELLOW);
	  button1.setFont(new Font("SansSerif", Font.BOLD, 18));
	  button1.setBorder(BorderFactory.createBevelBorder(1));
  	  button1.addActionListener(new ActionListener() {
          // this method gets called when the user clicks this button 
  		  //and prints out the solution 
          @Override
          public void actionPerformed(ActionEvent e) {
        	  
        	   in = input.getText();//collects the input from text field
        	   
        	   String str[] = in.split("\r\n");//splits it in rows
        	   Driver driver = new Driver(); //calls driver method
        	   for(int i=0;i<str.length;i++)//for loop that splits the rows of strings into strings by commas
        	   {
        		  s =str[i].split(",");
        		  Place p =  new Place(s[0],s[1],s[2],s[3],s[4]);//creates place object from input data
        		  driver.startPlace.add(p);//adds to an arraylist in the driver class
        	   }
        	  
        	  ArrayList<Place> places = new ArrayList<Place>();
        	  places.addAll(driver.startPlace);
        	  String y = driver.printShortest(new NearestNeighbor().findShortest(places));
        	  String x = y.substring(1,y.length()-2);
              output.setText(x);
          }
  	});
	   //bottom panel that contains the input text field and the compute button
	  JPanel bottomPanel = new JPanel();
	  bottomPanel.setBackground(Color.GREEN);
	  bottomPanel.setBounds(0, 400, 800, 140);
	  bottomPanel.setLayout(null);
	  
	  //all elements added to different panels
	  rightPanel.add(map);
	  leftPanel.add(output);
	  leftPanel.add(info);
	  bottomPanel.add(button1);
	  bottomPanel.add(input);
	  this.add(rightPanel);
	  this.add(bottomPanel);
	  this.add(leftPanel);
	}
	//main method to run the program
    public static void main(String[] args) {
        TSP tsp = new TSP("TSP App");
        tsp.setVisible(true);
    }
    //classes and methods for functionality code here
    //Place class that contains the data in an input 
    class Place
    {
    	private static final double EARTH_RADIUS = 6371;
    	private static final double DEGREES_TO_RADIANS = Math.PI/180;
    	private double longitude;
    	private double latitude;
    	private String inumber;
    	private String ad;
    	private String num;
    	public Place(String inumber, String ad, String num, String latitud, String longitud)
    	{
    		this.inumber = inumber;
    		this.ad = ad;
    		this.num = num;
    	    latitude = Double.parseDouble(latitud);
    	    longitude = Double.parseDouble(longitud);
    		this.latitude = latitude * DEGREES_TO_RADIANS;
    		this.longitude = longitude * DEGREES_TO_RADIANS;
    	}
    	public String getInumber()
    	{
    		return inumber;
    	}
    	public String getAd()
    	{
    		return ad;
    	}
    	public String getNum()
    	{
    		return num;
    	}
    	public double getLatitude()
    	{
    		return latitude;
    	}
    	public double getLongitude()
    	{
    		return longitude;
    	}
    	//Calculates the distance between points
    	public double distance(Place place)
    	{
    		double dlat = (place.getLatitude() - this.getLatitude());
    		double dlon = (place.getLongitude() - this.getLongitude());
    		double a = Math.pow(Math.sin(dlat / 2), 2)
                    + Math.cos(this.getLatitude()) * Math.cos(place.getLatitude())
                    * Math.pow(Math.sin(dlon / 2),2);
    		return EARTH_RADIUS * 2 * Math.asin(Math.sqrt(a));
    	}
    	public String toString() {return this.inumber;}
    }
    //Travel class that calculates the total distance of a route
    class Travel
    {
    	private ArrayList<Place> places = new ArrayList<Place>();
    	public Travel(ArrayList<Place> places)
    	{
    		this.places.addAll(places);
    	}
    	public ArrayList<Place> getPlaces(){return places;}
    	public int totalDistance()
    	{
    		int pSize = this.getPlaces().size();
    		return (int) (this.getPlaces().stream().mapToDouble(x -> {
    			int placeInd = this.getPlaces().indexOf(x);
    			double value = 0;
    			if(placeInd < pSize -1) 
    				value = x.distance(this.getPlaces().get(placeInd + 1));
    			return value;
    		}).sum()+this.getPlaces().get(pSize - 1).distance(this.getPlaces().get(0))); 
    	}
    	public String toString() {return Arrays.toString(places.toArray());}
    }
    //Nearest Neighbor class implements nearest neighbor algorithm
    class NearestNeighbor{
    	public Travel findShortest(ArrayList<Place> places) {
    		ArrayList<Place> shortestTravel = new ArrayList<Place>(places.size());
            Place place = places.get((int)(places.size() * Math.random()));
            updateTravel(shortestTravel, places, place);
            while(places.size() >= 1)
            {
            	place = getNextPlace(places, place);
            	updateTravel(shortestTravel, places, place);
            }
            return new Travel(shortestTravel);
    	}
    	//method to update an arraylist used to monitor the routes
    	private void updateTravel(ArrayList<Place> shortestTravel,ArrayList<Place> places, Place place)
    	{
    		shortestTravel.add(place);//adds place to arraylist
    		places.remove(place);// removes a place from arraylist
    	}
    	//method used to go to the next place in an arraylist
    	private Place getNextPlace(ArrayList<Place> places, Place place) 
    	{
    		return places.stream().min((p1, p2) -> {
    			int f =0;
    			if(p1.distance(place) < p2.distance(place))
    			{
    				f = -1;
    			}
    			else if(p1.distance(place) > p2.distance(place))
    			{
    				f = 1;
    			}
    			return f;
    		}).get();
    	}
    }
    //driver class that collects the initial data in an arraylist
    class Driver{
    	
    	//initial data will go to this arraylist
    	private ArrayList<Place> startPlace = new ArrayList<Place>(Arrays.asList());
    	//method that will return the order number from the input data to be printed	
    	//this will be the shortest route from one point to another
    	private String printShortest(Travel shortestTravel)
    	{
    		Travel s1 = shortestTravel;
    		String s2 = s1.toString()+",";
    		return s2;
    	}
    	
    }
}

