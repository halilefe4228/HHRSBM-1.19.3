package me.hhrengar.hhrsbm;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//asdasd
public class HHRSBM implements ModInitializer {
	//AAAA
	public static final String MOD_ID ="hhrsbm";
    public static final Logger LOGGER = LoggerFactory.getLogger("hhrsbm");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}