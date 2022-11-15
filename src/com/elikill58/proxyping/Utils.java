package com.elikill58.proxyping;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Utils {

	public static boolean isConnectedPlayer(String arg) {
		return ProxyServer.getInstance().getPlayers().contains(ProxyServer.getInstance().getPlayer(arg));
	}

	public static String applyColorCodes(String message) {
		return message.replaceAll("&r", ChatColor.RESET.toString()).replaceAll("&0", ChatColor.BLACK.toString())
				.replaceAll("&1", ChatColor.DARK_BLUE.toString()).replaceAll("&2", ChatColor.DARK_GREEN.toString())
				.replaceAll("&3", ChatColor.DARK_AQUA.toString()).replaceAll("&4", ChatColor.DARK_RED.toString())
				.replaceAll("&5", ChatColor.DARK_PURPLE.toString()).replaceAll("&6", ChatColor.GOLD.toString())
				.replaceAll("&7", ChatColor.GRAY.toString()).replaceAll("&8", ChatColor.DARK_GRAY.toString())
				.replaceAll("&9", ChatColor.BLUE.toString()).replaceAll("&a", ChatColor.GREEN.toString())
				.replaceAll("&b", ChatColor.AQUA.toString()).replaceAll("&c", ChatColor.RED.toString())
				.replaceAll("&d", ChatColor.LIGHT_PURPLE.toString()).replaceAll("&e", ChatColor.YELLOW.toString())
				.replaceAll("&f", ChatColor.WHITE.toString()).replaceAll("&l", ChatColor.BOLD.toString())
				.replaceAll("&o", ChatColor.ITALIC.toString()).replaceAll("&m", ChatColor.STRIKETHROUGH.toString())
				.replaceAll("&n", ChatColor.UNDERLINE.toString()).replaceAll("&k", ChatColor.MAGIC.toString());
	}

	@SuppressWarnings("deprecation")
	public static TextComponent createMessage(String message, ChatColor color, boolean bold,
			net.md_5.bungee.api.chat.HoverEvent.Action hoverAction, String hoverMsg,
			net.md_5.bungee.api.chat.ClickEvent.Action clickAction, String clickMsg, String... extras) {
		TextComponent tc = new TextComponent(applyColorCodes(message));
		tc.setBold(bold);
		tc.setColor(color);
		tc.setClickEvent(new ClickEvent(clickAction, applyColorCodes(clickMsg)));
		tc.setHoverEvent(new HoverEvent(hoverAction, new ComponentBuilder(applyColorCodes(hoverMsg)).create()));
		for (String ex : extras) {
			tc.addExtra(ex);
		}
		return tc;
	}

	@SuppressWarnings("deprecation")
	public static TextComponent createMessage(String message, ChatColor color, boolean bold,
			net.md_5.bungee.api.chat.HoverEvent.Action hoverAction, String hoverMsg, String... extras) {
		TextComponent tc = new TextComponent(message);
		tc.setBold(bold);
		tc.setColor(color);
		tc.setHoverEvent(new HoverEvent(hoverAction, new ComponentBuilder(hoverMsg).create()));
		for (String ex : extras) {
			tc.addExtra(ex);
		}
		return tc;
	}

	public static String getMessage(String... args) {
		StringBuilder msg = new StringBuilder();
		for (String s : args)
			msg.append(s).append(' ');

		return msg.toString();
	}
}
