package test.api;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InternalExchangeOffice {}


    private InternalExchangeOffice() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Calendar calendarObj = Calendar.getInstance();

        URL url = new URL("https://api.exchangeratesapi.io/latest");
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("base", "EUR");
        connection.setRequestProperty("date", dateFormat.format(calendarObj.getTime()));
        int responseCode = connection.getResponseCode();




        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = input.readLine()) != null) {
                response.append(readLine);
            } input .close();

            String jsonString = response.toString();
            ObjectMapper mapper = new ObjectMapper();
            try{
                Currency base = mapper.readValue(jsonString, Currency.class);
                Gson gson = new Gson();
                Currency currency = gson.fromJson(jsonString,Currency.class);
            }catch(IOException e){e.printStackTrace();}

        } else {
            System.out.println("GET DOES'T WORK");
        }


    }
    public static InternalExchangeOffice getInstance() throws IOException {
        if (exchangeInstance == null){
            return new InternalExchangeOffice();
        }

        return exchangeInstance;
    }
