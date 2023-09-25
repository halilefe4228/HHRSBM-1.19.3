package me.hhrengar.hhrsbm.event;

import me.hhrengar.hhrsbm.utils.SlayerUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class ScoreboardGetter {
    public static void mainScoreboardFunction() {
        if (MinecraftClient.getInstance().player == null) return;
        Scoreboard scb = MinecraftClient.getInstance().player.getScoreboard();
        for (ScoreboardPlayerScore score : scb.getAllPlayerScores(scb.getObjectiveForSlot(1))) {
            Team team = scb.getPlayerTeam(score.getPlayerName());
            if (team != null) {
                String line = team.getPrefix().getString() + team.getSuffix().getString();
                switch (line) {
                    case "Slay the boss!":
                        SlayerUtils.onBossSpawn();
                        break;
                    case "a":
                        //a
                        break;
                }
            }
        }
    }
    public static void registerScoreboardGetter(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            mainScoreboardFunction();
        });
    }
}
