package fr.northenflo.minerplus.minerplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ELYCOMBExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            ItemStack itemCombine = p.getItemInHand();
            ItemStack itemPlastron = p.getInventory().getChestplate();
            if(itemPlastron != null){
                if(itemPlastron.getType() == Material.ELYTRA){
                    if(itemCombine.getType() == Material.DIAMOND_CHESTPLATE){
                        itemPlastron.setItemMeta(itemCombine.getItemMeta());
                        p.getInventory().setChestplate(itemPlastron);
                        p.getInventory().setItemInHand(new ItemStack(Material.AIR));
                    }
                }
            }
            return true;
        }
        return false;
    }
}
