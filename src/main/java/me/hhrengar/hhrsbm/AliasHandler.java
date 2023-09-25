package me.hhrengar.hhrsbm;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AliasHandler {
    public static HashMap<String,String> aliases = new HashMap<String,String>();
    public static HashMap<String,String> saveMap = new HashMap<String,String>();

    public static void saveAliases(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter("aliases.json")) {
            gson.toJson(saveMap, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadAliases() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader("aliases.json")) {
            Type typeObj = new TypeToken<HashMap>(){}.getType();
            aliases = gson.fromJson(reader, typeObj);
            ClientCommandManager.registerCommands();
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
