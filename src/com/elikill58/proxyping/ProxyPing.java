package com.elikill58.proxyping;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

public class ProxyPing extends Plugin implements Listener {

	public static Configuration CONF;
	private static ProxyPing instance;

	public static ProxyPing getInstance() {
		return instance;
	}
	
	private int maxPlayers;

	@Override
	public void onEnable() {
		instance = this;
		try {
			CONF = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Config.loadConfig("config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		getProxy().getPluginManager().registerListener(this, this);
		maxPlayers = CONF.getInt("max_players");
	}

	@EventHandler
	public void onProxyPing(ProxyPingEvent e) {
		if (e.getResponse() == null)
			return;
		ServerPing ping = e.getResponse();
        ServerPing.Players playerPing = ping.getPlayers();
        
		List<String> sampleMsg = CONF.getStringList("Sample");
        ServerPing.PlayerInfo[] sample = new ServerPing.PlayerInfo[sampleMsg.size()];
        for (int i = 0; i < sample.length; i++)
            sample[i] = new ServerPing.PlayerInfo(Utils.applyColorCodes(placeHolders(sampleMsg.get(i))), UUID.fromString("0-0-0-0-0"));
        playerPing.setSample(sample);
        playerPing.setMax(maxPlayers);
        ping.setPlayers(playerPing);
        
		ping.setDescriptionComponent(new TextComponent(getRandomDescription()));
		ping.setVersion(new Protocol(Utils.applyColorCodes(placeHolders(CONF.getString("protocol"))), 1));
		e.setResponse(ping);
	}
	
	public String getRandomDescription() {
	    List<String> list = CONF.getStringList("motd");
	    return Utils.applyColorCodes(placeHolders(list.get(new Random().nextInt(list.size()))));
	}
	
	public String placeHolders(String s) {
		return s.replaceAll("%maxplayer%", String.valueOf(maxPlayers)).replaceAll("%maxplayers%", String.valueOf(maxPlayers))
					.replaceAll("%online%", String.valueOf(ProxyServer.getInstance().getPlayers().size()));
	}
}
