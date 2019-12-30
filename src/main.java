import java.util.*;
import java.io.*;
import org.json.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOExceptionWithCause;

import com.google.gson.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class main {
	public static void main(String[] args) 
	{
		databaseUtil.buildConnection();
//		databaseUtil.initDatabase();
		ReadHotelList();
		HotelPreference program = new HotelPreference();
		program.setVisible(true);
	}
	public static void ReadHotelList()  {
		try (Reader reader = new InputStreamReader(main.class.getResourceAsStream("HotelList.json"), "big5")) {
			// try (BufferedReader reader = new BufferedReader(new FileReader(file)) {
			Gson gson = new GsonBuilder().create();
			HotelList = gson.fromJson(reader, Hotel[].class);
			for (Hotel h : HotelList)
				h.init();
			for (Hotel h : HotelList)
				System.out.println(h);
		} catch (Exception e) {
			System.out.println("cannot find the file.");
		}
	}
}