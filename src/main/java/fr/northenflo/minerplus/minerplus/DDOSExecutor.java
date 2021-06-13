package fr.northenflo.minerplus.minerplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class DDOSExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length > 0) {
            if (!args[0].isEmpty()) {
                OfflinePlayer pt = Bukkit.getServer().getOfflinePlayer(args[0]);
                if(pt.isOnline()){
                    pt.getPlayer().kickPlayer("Ah batard tu te fais DDOS ! x)");
                    Bukkit.broadcastMessage(ChatColor.AQUA+" "+commandSender.getName()+" ! Ah batard tu DDOS "+pt.getName()+" !");
                }else
                    commandSender.sendMessage(ChatColor.RED+"Le joueur est hors ligne");
            }else
                commandSender.sendMessage(ChatColor.RED+"/ddos <player>");
        }else
            commandSender.sendMessage(ChatColor.RED+"/ddos <player>");
        return true;
    }
}
