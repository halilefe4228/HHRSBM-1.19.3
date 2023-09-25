package me.hhrengar.hhrsbm.event;

import it.unimi.dsi.fastutil.Hash;
import me.hhrengar.hhrsbm.utils.SlayerUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMessageGetter{
    public static boolean doDebug = true;
    public static HashMap<MessageSignatureData,Text> messages = new HashMap<MessageSignatureData,Text>();
    public static final Logger LOGGER = LoggerFactory.getLogger("hhrsbm");
    public static Boolean getScoreboard = false;
    public static void messageList(MessageSignatureData signature,Text message)
    {
        if(doDebug) messages.put(signature,message);
    }
    public static void mainMessageFunction() {
        HashMap<MessageSignatureData,Text> messagesTemp = new HashMap<MessageSignatureData,Text>(messages);
        messagesTemp.forEach((key,value)->{
                    assert MinecraftClient.getInstance().player != null;
                    String flatText = extractAndJoinLiterals(value.toString());
                    switch (flatText){
                        case "  SLAYER QUEST COMPLETE!":
                            SlayerUtils.onSlayerKill();
                            break;
                        case "  SLAYER QUEST STARTED!":
                            SlayerUtils.onSlayerStart();
                            break;
                        default:
                            int matchID = 0;
                            List<Pattern> patterns = new ArrayList<>();
                            patterns.add(Pattern.compile("Your Implosion hit (\\\\d+) enemies for (\\\\d+\\\\.\\\\d+) damage\\\\."));
                            patterns.add(Pattern.compile("Your Implosion hit 1 enemy for (\\\\d+\\\\.\\\\d+) damage\\\\."));
                            patterns.add(Pattern.compile("pattern3"));

                            // Iterate through the patterns
                            for (Pattern pattern : patterns) {
                                // Create a Matcher for the current pattern
                                Matcher matcher = pattern.matcher(flatText);

                                // Find and process matches
                                while (matcher.find()) {
                                    String match = matcher.group(); // Get the matched text
                                    matchID = patterns.indexOf(pattern);
                                }
                            }
                            switch (matchID){
                                case 0:
                                    break;
                                case 1:
                                    break;
                            }
                            break;
                    }
                    messages.clear();
        });
    }
    public static String extractAndJoinLiterals(String string) {
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("literal\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            String literal = matcher.group(1);
            result.append(literal);
        }

        return result.toString();
    }
    public static void registerMessageGetter(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            mainMessageFunction();
        });
    }
}
