package me.hhrengar.hhrsbm.gui;
import com.mojang.datafixers.TypeRewriteRule;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import me.hhrengar.hhrsbm.AliasHandler;
import me.hhrengar.hhrsbm.ConfigHandler;
import me.hhrengar.hhrsbm.HHRSBM;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class ConfigGUI extends LightweightGuiDescription {
    public static int windowWidth = 0;
    public static int windowHeight = 0;
    public static double windowScale = 0;
    public static Boolean screenOpen = false;
    public static Boolean reOpen = false;
    public static int reOpenIndex = -1;
    private static TextRenderer textRenderer;
    private static Consumer<String>[] aliasTextBoxes = new Consumer[100];
    private static Consumer<String>[] commandTextBoxes = new Consumer[100];
    private static String[] keys = new String[100];
    private static String[] values = new String[100];
    public static Boolean forceOpenScreen = false;
    public ConfigGUI() {
        screenOpen=true;
        windowScale = MinecraftClient.getInstance().getWindow().getScaleFactor();
        MinecraftClient.getInstance().getWindow().setScaleFactor(2.0);
        WTabPanel AllTabs = new WTabPanel();
        windowWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        windowHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        textRenderer= MinecraftClient.getInstance().textRenderer;
        aliasTextBoxes = new Consumer[100];
        commandTextBoxes = new Consumer[100];
        keys = new String[100];
        values = new String[100];

        //region Combat Tabs
        WTabPanel CombatTabs = new WTabPanel();
        CombatTabs.setSize((int)(windowWidth*0.8),(int)(windowHeight*0.8));

        WPlainPanel SlayerTabPanel = new WPlainPanel();
        WScrollPanel SlayerTabSlider = new WScrollPanel(SlayerTabPanel);
        SlayerTabSlider.setSize((int)(windowWidth*0.8)-2,(int)(windowHeight*0.8)-2);
        SlayerTabSlider.setScrollingHorizontally(TriState.FALSE);
        SlayerTabSlider.setScrollingVertically(TriState.TRUE);
        SlayerTabPanel.setSize((int)(windowWidth*0.8),(int)(windowHeight*0.8));


        WToggleButton onSlayerKill = new WToggleButton(Text.literal("§fWrite in chat,how long it took for you to spawn and kill the boss."));
        onSlayerKill.setToggle(ConfigHandler.config.get("onSlayerKill"));
        onSlayerKill.setOnToggle(bool ->{ConfigHandler.setConfig("onSlayerKill",bool);});

        WText onSlayerKillTooltip = new WText(Text.of("§6 [SLAYER] §f Boss took §615s §fto spawn.\n§6 [SLAYER] §f Boss took §65s §fto kill.\n§6 [SLAYER] §f Boss took §620s §fto spawn and kill."));

        SlayerTabPanel.add(onSlayerKill,centerX("Write in chat,how long it took for you to spawn and kill the boss.")-11,15);
        SlayerTabPanel.add(onSlayerKillTooltip,(int)(windowWidth*0.4)+centerX(" [SLAYER]  Boss took 20s to spawn and kill.")-11 ,12,(int)(windowWidth*0.4),30);

        CombatTabs.add(SlayerTabSlider,tab->tab.icon(new TextureIcon(new Identifier("hhrsbm:textures/item/maddox.png"))).title(Text.of(" Slayer ")));

        //endregion

        //region Farming Tabs

        WTabPanel FarmingTabs = new WTabPanel();
        FarmingTabs.setSize((int)(windowWidth*0.8),(int)(windowHeight*0.8));

        WPlainPanel GardenTab = new WPlainPanel();
        GardenTab.setSize((int)(windowWidth*0.8),(int)(windowHeight*0.8));

        FarmingTabs.add(GardenTab,tab->tab.title(Text.of(" Garden ")));




        //endregion

        //region Mining Tabs

        WTabPanel MiningTabs = new WTabPanel();
        MiningTabs.setSize((int)(windowWidth*0.8),(int)(windowHeight*0.8));

        WGridPanel CHollowsTab = new WGridPanel();
        CHollowsTab.setSize((int)(windowWidth*0.8),(int)(windowHeight*0.8));

        MiningTabs.add(CHollowsTab,tab->tab.title(Text.of(" Crystal Hollows ")));

        //endregion

        //region Other Tabs

            WTabPanel OtherTabs = new WTabPanel();
            OtherTabs.setSize((int)(windowWidth*0.8),(int)(windowHeight*0.8));

            //region Chat
                WPlainPanel ChatTabPanel = new WPlainPanel();
                WScrollPanel ChatTabSlider = new WScrollPanel(ChatTabPanel);
                ChatTabSlider.setSize((int)(windowWidth*0.8)-2,(int)(windowHeight*0.8)-2);
                ChatTabSlider.setScrollingHorizontally(TriState.FALSE);
                ChatTabSlider.setScrollingVertically(TriState.TRUE);
                ChatTabPanel.setSize((int)(windowWidth*0.8),(int)(windowHeight*0.8));

                WToggleButton hideImplosionText = new WToggleButton(Text.literal("§fStop showing implosion text messages in chat."));
                hideImplosionText.setToggle(ConfigHandler.config.get("hideImplosionText"));
                hideImplosionText.setOnToggle(bool ->{ConfigHandler.setConfig("hideImplosionText",bool);});

                WToggleButton showImplosionText = new WToggleButton(Text.literal("§fShow implosion text messages in a seperate GUI."));
                showImplosionText.setToggle(ConfigHandler.config.get("showImplosionText"));
                showImplosionText.setOnToggle(bool ->{ConfigHandler.setConfig("showImplosionText",bool);});

                WToggleButton hideBlockOnWayText = new WToggleButton(Text.literal("§fStop showing \"There are blocks on the way!\"."));
                hideBlockOnWayText.setToggle(ConfigHandler.config.get("hideBlockOnWayText"));
                hideBlockOnWayText.setOnToggle(bool ->{ConfigHandler.setConfig("hideBlockOnWayText",bool);});

                WToggleButton hideMSBExpired = new WToggleButton(Text.literal("§fStop showing Mining Speed Boost Expired message."));
                hideMSBExpired.setToggle(ConfigHandler.config.get("hideMSBExpired"));
                hideMSBExpired.setOnToggle(bool ->{ConfigHandler.setConfig("hideMSBExpired",bool);});

                WToggleButton hideAbilityOnCD = new WToggleButton(Text.literal("§fStop showing ability is on cooldown message."));
                hideAbilityOnCD.setToggle(ConfigHandler.config.get("hideAbilityOnCD"));
                hideAbilityOnCD.setOnToggle(bool ->{ConfigHandler.setConfig("hideAbilityOnCD",bool);});

                WToggleButton showAbilityOnCD = new WToggleButton(Text.literal("§fShow ability cooldown messages in a seperate GUI."));
                showAbilityOnCD.setToggle(ConfigHandler.config.get("showAbilityOnCD"));
                showAbilityOnCD.setOnToggle(bool ->{ConfigHandler.setConfig("showAbilityOnCD",bool);});

                WToggleButton hideMSBReady = new WToggleButton(Text.literal("§fStop showing Mining Speed Boost available message."));
                hideMSBReady.setToggle(ConfigHandler.config.get("hideMSBReady"));
                hideMSBReady.setOnToggle(bool ->{ConfigHandler.setConfig("hideMSBReady",bool);});

                WToggleButton hideMSBUsed = new WToggleButton(Text.literal("§fStop showing Mining Speed Boost used message."));
                hideMSBUsed.setToggle(ConfigHandler.config.get("hideMSBUsed"));
                hideMSBUsed.setOnToggle(bool ->{ConfigHandler.setConfig("hideMSBUsed",bool);});


                ChatTabPanel.add(hideImplosionText,centerX("Stop showing implosion text messages in chat.")-11,15);
                ChatTabPanel.add(showImplosionText,(int)(windowWidth*0.4)+centerX("Show implosion text messages in a seperate GUI.")-11,15,(int)(windowWidth*0.4),30);
                ChatTabPanel.add(hideBlockOnWayText,centerX("Stop showing \"There are blocks on the way!\"")-11,30);
                ChatTabPanel.add(hideMSBExpired,(int)(windowWidth*0.4)+centerX("Stop showing Mining Speed Boost Expired message")-11,30);
                ChatTabPanel.add(hideAbilityOnCD,centerX("Stop showing ability is on cooldown message")-11,45);
                ChatTabPanel.add(showAbilityOnCD,(int)(windowWidth*0.4)+centerX("Show ability cooldown messages in a seperate GUI.")-11,45);
                ChatTabPanel.add(hideMSBReady,centerX("Stop showing Mining Speed Boost available message.")-11,60);
                ChatTabPanel.add(hideMSBUsed,(int)(windowWidth*0.4)+centerX("Stop showing Mining Speed Boost used message.")-11,60);
            //endregion


            //region Aliases
                    WPlainPanel AliasesTabPanel = new WPlainPanel();
                    WScrollPanel AliasesTabSlider = new WScrollPanel(AliasesTabPanel);
                    AliasesTabSlider.setSize((int)(windowWidth*0.8)-2,(int)(windowHeight*0.8)-2);
                    AliasesTabSlider.setScrollingHorizontally(TriState.FALSE);
                    AliasesTabSlider.setScrollingVertically(TriState.TRUE);
                    AliasesTabPanel.setSize((int)(windowWidth*0.8),(int)(windowHeight*0.8));

                    AliasHandler.loadAliases();

                    WButton addAliasButton = new WButton(Text.literal("Add new alias")).setOnClick(()->{
                        for (int number = 0;number<100;number++){
                            HHRSBM.LOGGER.info("number "+number);
                            Boolean dupe = false;
                            for(String key : keys){
                                if(Objects.equals(key, "alias" + number)){
                                    HHRSBM.LOGGER.info("dupe found");
                                    dupe=true;
                                }
                            }
                            if(!dupe) {
                                keys[99] = "alias" + number;
                                values[99] = "alias" + number;
                                break;
                            }
                        }
                        saveAliases();
                        reOpen=true;
                        reOpenIndex=0;
                        MinecraftClient.getInstance().setScreen(new ConfigScreen(new ConfigGUI()));});

                    List<String> aliasKeys = new ArrayList<String>(AliasHandler.aliases.keySet());
                    for (int count = 0;count<aliasKeys.size();count++){

                        WTextField aliasBox = new WTextField(Text.literal("dh"));
                        aliasBox.setText(aliasKeys.get(count));
                        keys[count]=aliasKeys.get(count);
                        aliasTextBoxes[count]=new Consumer<String>() {
                            @Override
                            public void accept(String s) {
                                for(int i = 0;i<aliasTextBoxes.length;i++){
                                    if(aliasTextBoxes[i]==this){
                                        keys[i]=s;
                                    }
                                }
                            }
                        };
                        aliasBox.setChangedListener(aliasTextBoxes[count]);


                        WButton removeAliasButton = new WButton().setIcon(new TextureIcon(new Identifier("hhrsbm:textures/item/trash.png")));
                        int finalCount = count;
                        removeAliasButton.setOnClick(()->{
                            keys[finalCount]="";
                            saveAliases();
                            reOpen=true;
                            reOpenIndex=0;
                            MinecraftClient.getInstance().setScreen(new ConfigScreen(new ConfigGUI()));

                        });

                        WTextField commandBox = new WTextField(Text.literal("warp dhub"));
                        commandBox.setText(AliasHandler.aliases.get(aliasKeys.get(count)));
                        values[count]=AliasHandler.aliases.get(aliasKeys.get(count));
                        commandTextBoxes[count]=new Consumer<String>() {
                            @Override
                            public void accept(String s) {
                                for(int i = 0;i<commandTextBoxes.length;i++){
                                    if(commandTextBoxes[i]==this){
                                        values[i]=s;
                                    }
                                }
                            }
                        };
                        commandBox.setChangedListener(commandTextBoxes[count]);


                        AliasesTabPanel.add(aliasBox,(int)(windowWidth*0.4)-95-(((count%3)-1)*-250),40+((int)Math.floor(count/3)*40),80,20);
                        AliasesTabPanel.add(removeAliasButton,(int)(windowWidth*0.4)-10-(((count%3)-1)*-250),40+((int)Math.floor(count/3)*40),20,20);
                        AliasesTabPanel.add(commandBox,(int)(windowWidth*0.4)+15-(((count%3)-1)*-250),40+((int)Math.floor(count/3)*40),80,20);
                    }
                    AliasesTabPanel.add(addAliasButton,(int)(windowWidth*0.4)-40,15,80,20);
            //endregion


            OtherTabs.add(ChatTabSlider,tab->tab.icon(new TextureIcon(new Identifier("hhrsbm:textures/item/chaticon.png"))).title(Text.of(" Chat ")));
            OtherTabs.add(AliasesTabSlider,tab->tab.icon(new TextureIcon(new Identifier("hhrsbm:textures/item/aliasicon.png"))).title(Text.of(" Aliases ")));

        //endregion

        setRootPanel(AllTabs);
        AllTabs.add(CombatTabs, tab -> tab.icon(new ItemIcon(new ItemStack(Items.DIAMOND_SWORD))).title(Text.literal(" Combat ")));
        AllTabs.add(FarmingTabs, tab -> tab.icon(new ItemIcon(new ItemStack(Items.GOLDEN_HOE))).title(Text.literal(" Farming ")));
        AllTabs.add(MiningTabs, tab -> tab.icon(new ItemIcon(new ItemStack(Items.PRISMARINE_SHARD))).title(Text.literal(" Mining ")));
        AllTabs.add(OtherTabs, tab -> tab.icon(new ItemIcon(new ItemStack(Items.REPEATER))).title(Text.literal(" Other ")));
        AllTabs.setSize((int)(windowWidth*0.8),(int)(windowHeight*0.8));
        if(reOpen){
            switch (reOpenIndex){
                case 0:
                    AllTabs.setSelectedIndex(3);
                    OtherTabs.setSelectedIndex(1);
                    reOpen=false;
                    reOpenIndex=-1;
                    break;
                case 1:
                    break;
            }
        }
    }
    public static int centerX(String text){
        return ((int)(windowWidth*0.4)-textRenderer.getWidth(text))/2;
    }
    public static void guiChecker(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (screenOpen && MinecraftClient.getInstance().currentScreen==null) {
                MinecraftClient.getInstance().getWindow().setScaleFactor(windowScale);
                saveAliases();
                screenOpen = false;
            }
            if(forceOpenScreen) {
                openConfigGUI();
                forceOpenScreen=false;
            }
        });
    }
    public static void saveAliases(){
        if(!AliasHandler.saveMap.isEmpty()) AliasHandler.saveMap.clear();
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null && values[i] != null) {
                if (!keys[i].trim().isEmpty() && !values[i].trim().isEmpty()) {
                    AliasHandler.saveMap.put(keys[i], values[i]);
                }
            }
        }
        AliasHandler.saveAliases();
    }
    public static void openConfigGUI(){
        HHRSBM.LOGGER.info("OPENING");
        MinecraftClient.getInstance().setScreen(new ConfigScreen(new ConfigGUI()));
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        thread.start();
    }
}
