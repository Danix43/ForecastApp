import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class JavaForecastObject extends JavaForecast {
	
	private static Scanner INPUT = new Scanner(System.in);
	private static String URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	private static String API_KEY;
	private static String fURL;
	
	
	public static void main(String args[]) {
		
		System.out.println("Enter your api key");
		API_KEY = INPUT.nextLine();
		if("".equals(API_KEY)) {
			// here to read from a file the api key
			// readKey(File key); writeKey(String key);
			System.out.println("Enter your api key!");
			API_KEY = INPUT.nextLine();
			}
		System.out.println("Enter the city for the forecast");
		String city = INPUT.nextLine();
		System.out.println("The system will get the data in metric units");
		fURL = URL + city + "&units=metric" + "&APPID=" + API_KEY;
		try {
			System.out.println("Getting data");
			getJson(fURL);
			System.out.println("Printing the data");
			readTemperature();
			readWind();
		} catch (IOException IO) {
			System.err.println("Error : " + IO.getMessage());
		} finally {
			INPUT.close();
		}
		
		
	}
	
}
