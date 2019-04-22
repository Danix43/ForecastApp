import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;


/** Java Weather App
 *  This program uses OpenWeatherMap's API to display weather data 
 *  Now it can only display temperature of the location in Celsius degrees
 *  
 *  Also, this program is still in Beta and it can suffer major changes
 *  But I don't think is going to happen too soon
 *  
 *  Java Maven Project
 *  Dependencies used :
 *  - GSON ("https://github.com/google/gson")
 * 	
 * 
 *  @author Danix43
 *  @version 1.1 Beta
 *  @since 13.12.2018
 */

public class JavaForecast {
	
	protected static Scanner SCAN;
	// you can change the API_KEY with own or you can leave as it is
	private static String API_KEY; //58628db8c282e54ba5006ec5556f75b0
	protected final static String URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	protected static JsonElement ROOT;
	protected static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static boolean checkAPIKEY(String path) throws FileNotFoundException {
		File key = new File(path);
		SCAN = new Scanner(key);
		if(SCAN.hasNextLine()) {
			API_KEY = SCAN.nextLine();
			return true;
		}
		return false;
	}
	
	public static void getJson(String fURL) throws IOException {
		// for connecting to the api
		URL url = new URL(fURL);
		URLConnection request = url.openConnection();
		request.connect();
		// for parsing the json
		JsonParser jp = new JsonParser();
		ROOT = jp.parse(
				new InputStreamReader((InputStream) request.getInputStream()));
	}
	
	public static void readTemperature() { 
		JsonObject rootCity = ROOT.getAsJsonObject();
		JsonObject cityTemp = rootCity.getAsJsonObject("main");
		JsonPrimitive temp = cityTemp.getAsJsonPrimitive("temp");
		JsonPrimitive humidity = cityTemp.getAsJsonPrimitive("humidity");
		JsonPrimitive tempMin = cityTemp.getAsJsonPrimitive("temp_min");
		JsonPrimitive tempMax = cityTemp.getAsJsonPrimitive("temp_max");
		String Temp = GSON.toJson(temp);
		String Humidity = GSON.toJson(humidity);
		String TempMin = GSON.toJson(tempMin);
		String TempMax = GSON.toJson(tempMax);
		System.out.println("Temperature now: " + Temp + " Celsius degrees");
		System.out.println("Humidity: " + Humidity + "%");
		System.out.println("Minim temperature: " + TempMin + " Celsius degrees");
		System.out.println("Max temperature: " + TempMax + " Celsius degrees");
	}
	
	public static void readWind() {
		JsonObject rootCity = ROOT.getAsJsonObject();
		JsonObject cityWind = rootCity.getAsJsonObject("wind");
		JsonPrimitive windSpeed = cityWind.getAsJsonPrimitive("speed");
		JsonPrimitive windDirection = cityWind.getAsJsonPrimitive("deg");
		String s = GSON.toJson(windDirection);
		double WINDDEG = Double.parseDouble(s);
		String WINDSPEED = GSON.toJson(windSpeed) + " m/s";
		String winddeg = calculateWindDegree(WINDDEG);
		System.out.println("Wind is blowing from " + winddeg + " with a speed of " + WINDSPEED);	
	}
	
	public static String calculateWindDegree(double WINDDEG) {
		String direction;
		if(WINDDEG >= 348.75 && WINDDEG <= 11.25) {
			direction = "N";
		} else if(WINDDEG >= 11.26 && WINDDEG <= 33.75) {
			direction = "NNE";
		} else if(WINDDEG >= 33.76 && WINDDEG <= 56.25) {
			direction = "NE";
		} else if(WINDDEG >= 56.26 && WINDDEG <= 78.75) {
			direction = "ENE";
		} else if(WINDDEG >= 78.76 && WINDDEG <= 101.25) {
			direction = "E";
		} else if(WINDDEG >= 101.26 && WINDDEG <= 123.75) {
			direction = "ESE";
		} else if(WINDDEG >= 123.76 && WINDDEG <= 146.25) {
			direction = "SE";
		} else if(WINDDEG >= 146.26 && WINDDEG <= 168.75) {
			direction = "SSE";
		} else if(WINDDEG >= 168.76 && WINDDEG <= 191.25) {
			direction = "S";
		} else if(WINDDEG >= 191.26 && WINDDEG <= 213.75) {
			direction = "SSW";
		} else if(WINDDEG >= 213.76 && WINDDEG <= 236.25) {
			direction = "SW";
		} else if(WINDDEG >= 236.26 && WINDDEG <= 258.75) {
			direction = "WSW";
		} else if(WINDDEG >= 258.76 && WINDDEG <= 281.25) {
			direction = "W";
		} else if(WINDDEG >= 281.26 && WINDDEG <= 303.75) {
			direction = "WNW";
		} else if(WINDDEG >= 303.76 && WINDDEG <= 326.25) {
			direction = "NW";
		} else if(WINDDEG >= 326.26 && WINDDEG <= 348.75) {
			direction = "NNW";
		} else {
			direction = null;
		}
		return direction;
	}
	
	public static void main(String[] args) {
		//initial setup
		System.out.println("Java Forecast");
		System.out.println("danix43");
		System.out.println("Stil in beta, the app may change");
		SCAN = new Scanner(System.in);
		System.out.println("Enter the city for you want to see the forecast");
		String city = SCAN.nextLine();
		//for making the url
		try {
			if(!checkAPIKEY("D:\\Programe\\Eclipse Workspace\\JavaForecastApp\\src\\main\\java\\key.txt")) {
				System.out.println("Enter your key for the API");
				API_KEY = SCAN.nextLine();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		// change metric for your prefered units of measure
		// metric for Celsius, imperial for Fahrenheit and delete unit for Kelvin
		String unit = "&units=metric";
		String fURL = URL + city + unit + "&APPID=" + API_KEY;
		try {
			// the code below is for more data display 
			System.out.println("All the data from the city " + city);
			getJson(fURL);
			readTemperature();
			readWind();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			SCAN.close();
		}
	}
		
}
