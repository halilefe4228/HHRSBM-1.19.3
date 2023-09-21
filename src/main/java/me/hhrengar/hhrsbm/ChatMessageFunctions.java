package me.hhrengar.hhrsbm;

import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatMessageFunctions{
    public static boolean doDebug = true;
    public static final Logger LOGGER = LoggerFactory.getLogger("hhrsbm");
    public static void oneAndOnly(Text message)
    {
        if(doDebug) LOGGER.info(message.toString());
    }


}