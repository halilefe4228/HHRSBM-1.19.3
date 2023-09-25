package me.hhrengar.hhrsbm;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.util.Attribute;
import it.unimi.dsi.fastutil.Hash;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class ConfigHandler {
    public static HashMap<String,Boolean> config = new HashMap<String,Boolean>();
    public static int upToDate = 0;
    public static void setConfig(String name,Boolean bool){
        config.put(name, bool);
        upToDate++;
        saveConfig();
    }
    public static void saveConfig(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter("config.json")) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadConfig() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader("config.json")) {
            Type typeObj = new TypeToken<HashMap>(){}.getType();
            config = gson.fromJson(reader, typeObj);
            upToDate++;
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }


}
