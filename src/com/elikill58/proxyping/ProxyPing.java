package com.elikill58.proxyping;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.mariuszgromada.math.mxparser.Expression;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

public class ProxyPing extends Plugin implements Listener {

	private Configuration config;
	private static ProxyPing instance;

	public static ProxyPing getInstance() {
		return instance;
	}

	public Configuration getConfig() {
		return config;
	}

	@Override
	public void onEnable() {
		instance = this;
		load();
		PluginManager pm = getProxy().getPluginManager();
		pm.registerListener(this, this);
		pm.registerCommand(this, new ProxyPingCommand());
	}
	
	public void load() {
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Config.loadConfig("config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onProxyPing(ProxyPingEvent e) {
		if (e.getResponse() == null)
			return;
		ServerPing ping = e.getResponse();
		ServerPing.Players playerPing = ping.getPlayers();

		if (config.getBoolean("sample.enable", true)) {
			List<String> sampleMsg = config.getStringList("sample.list");
			ServerPing.PlayerInfo[] sample = new ServerPing.PlayerInfo[sampleMsg.size()];
			for (int i = 0; i < sample.length; i++)
				sample[i] = new ServerPing.PlayerInfo(Utils.applyColorCodes(placeHolders(sampleMsg.get(i))),
						UUID.fromString("0-0-0-0-0"));
			playerPing.setSample(sample);
		}
		if (config.getBoolean("players.max.enable", true))
			playerPing.setMax(config.getInt("players.max.amount", 400));
		if(config.getBoolean("players.online.enable", false)) {
			double online = new Expression(config.getString("players.online.calculation", "%online%")
					.replaceAll("%online%", String.valueOf(getProxy().getPlayers().size()))).calculate();
			playerPing.setOnline((int) online);
		}
		ping.setPlayers(playerPing);

		if (config.getBoolean("motd.enable", true))
			ping.setDescriptionComponent(new TextComponent(getRandomDescription()));
		if (config.getBoolean("protocol.enable", true))
			ping.setVersion(new Protocol(Utils.applyColorCodes(placeHolders(config.getString("protocol.message"))),
					config.getInt("protocol.protocol_id", 1)));
		e.setResponse(ping);
	}

	public String getRandomDescription() {
		List<String> list = config.getStringList("motd.list");
		return Utils.applyColorCodes(placeHolders(list.get(new Random().nextInt(list.size()))));
	}

	public String placeHolders(String s) {
		int maxPlayers = config.getInt("max_players.amount", 400);
		return s.replaceAll("%maxplayer%", String.valueOf(maxPlayers))
				.replaceAll("%maxplayers%", String.valueOf(maxPlayers))
				.replaceAll("%online%", String.valueOf(ProxyServer.getInstance().getPlayers().size()));
	}
}
