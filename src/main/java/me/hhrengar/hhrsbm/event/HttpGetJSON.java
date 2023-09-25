package me.hhrengar.hhrsbm.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.yggdrasil.response.Response;
import me.hhrengar.hhrsbm.HHRSBM;
import net.minecraft.nbt.NbtHelper;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpGetJSON {
    public static Response response;
    private static String jsonStringofProfile;
    public static void getJSONFromURL(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {

            String inline = "";
            Scanner scanner = new Scanner(url.openStream());

            //Write all the JSON data into a string using a scanner
            while (scanner.hasNext()) {
                inline += scanner.nextLine();
            }

            //Close the scanner
            scanner.close();
            jsonStringofProfile=inline;
        }
    }
    public static void getSomeoneInv_Contents(String uuid){
        try{
            getJSONFromURL(new URL("https://api.hypixel.net/skyblock/profiles?key=74419e6e-25a3-40a4-b279-8f814cb12520&uuid="+uuid));
        }
        catch (IOException e){

        }
        JsonParser parser = new JsonParser();
        JsonObject rootObj = parser.parse(jsonStringofProfile).getAsJsonObject();
        JsonArray profiles = rootObj.getAsJsonArray("profiles");
        profiles.forEach(profile ->{
            if(profile.getAsJsonObject().get("selected").getAsBoolean()){
                HHRSBM.LOGGER.info("Selected profile is: "+profile.getAsJsonObject().get("cute_name").getAsString());
            }
        });
    }

}
