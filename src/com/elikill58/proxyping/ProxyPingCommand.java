package com.elikill58.proxyping;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ProxyPingCommand extends Command {

	public ProxyPingCommand() {
		super("proxyping");
	}
	
	@Override
	public void execute(CommandSender sender, String[] arg) {
		if(sender instanceof ProxiedPlayer && !sender.hasPermission("")) {
			sender.sendMessage(new TextComponent("You don't have permission to do that."));
			return;
		}
		if(arg.length == 0) {
			sender.sendMessage(new TextComponent("----- ProxyPing | v" + ProxyPing.getInstance().getDescription().getVersion() + " -----"));
			sender.sendMessage(new TextComponent("/proxyping reload : Reload the plugin"));
		} else if(arg[0].equalsIgnoreCase("reload")) {
			ProxyPing.getInstance().load();
			sender.sendMessage(new TextComponent("Plugin ProxyPing reloaded !"));
		} else {
			sender.sendMessage(new TextComponent("Unknown sub-command."));
		}
	}
}
