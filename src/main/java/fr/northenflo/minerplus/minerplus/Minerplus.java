package fr.northenflo.minerplus.minerplus;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandMap;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class Minerplus extends JavaPlugin implements Listener {

    CommandManager commandManager;

    public void onEnable() {
        System.out.println("Load Event and Recipe..");
        getServer().getPluginManager().registerEvents(this, this);

        this.getCommand("ddos").setExecutor(new DDOSExecutor());
        this.getCommand("elycomb").setExecutor(new ELYCOMBExecutor());

        setupRecipe();
    }

    public void onDisable() {}

    public void setupRecipe() {

        //ROTTEN FLESH
        ItemStack resultRotten = new ItemStack(Material.COOKED_BEEF);
        Material sourceRotten = Material.ROTTEN_FLESH;
        FurnaceRecipe furnaceRecipeRotten = new FurnaceRecipe(resultRotten, sourceRotten);
        Bukkit.addRecipe(furnaceRecipeRotten);
        System.out.println("Add Recipe Furnace => RottenFlesh to CookedBeef");
        //END ROTTEN FLESH

        //ELYTRA
        ItemStack itemElytra = new ItemStack(Material.ELYTRA);
        ShapedRecipe recipeElytra = new ShapedRecipe(itemElytra);
        recipeElytra.shape("PMP", "PEP", "PFP")
                .setIngredient('P', Material.FEATHER)
                .setIngredient('M', Material.PHANTOM_MEMBRANE)
                .setIngredient('E', Material.BLAZE_ROD)
                .setIngredient('F', Material.STRING);
        Bukkit.addRecipe(recipeElytra);
        //END ELYTRA

        //HEAD ZOMBIE
        ItemStack itemHeadZombie = new ItemStack(Material.ZOMBIE_HEAD);
        ShapedRecipe recipeHeadZombie = new ShapedRecipe(itemHeadZombie);
        recipeHeadZombie.shape("BBB", "BVB", "BBB")
                .setIngredient('B', Material.BONE)
                .setIngredient('V', Material.ROTTEN_FLESH);
        Bukkit.addRecipe(recipeHeadZombie);
        //END ELYTRA

        //SADDLE
        ItemStack itemSaddle = new ItemStack(Material.SADDLE);
        ShapedRecipe recipeSaddle = new ShapedRecipe(itemSaddle);
        recipeSaddle.shape("CCC", "CFC", "F F")
                .setIngredient('C', Material.LEATHER)
                .setIngredient('F', Material.IRON_NUGGET);
        Bukkit.addRecipe(recipeSaddle);
        //END SADDLE

        //SPAWNER
        ItemStack itemSpawner = new ItemStack(Material.SPAWNER);
        ShapedRecipe recipeSpawner = new ShapedRecipe(itemSpawner);
        BlockStateMeta metaSpawner = (BlockStateMeta) itemSpawner.getItemMeta();
        CreatureSpawner creatureSpawner = (CreatureSpawner) metaSpawner.getBlockState();
        creatureSpawner.setSpawnedType(EntityType.PIG);
        creatureSpawner.setDelay(0);
        creatureSpawner.setSpawnCount(10);
        metaSpawner.setBlockState(creatureSpawner);
        itemSpawner.setItemMeta(metaSpawner);
        recipeSpawner.shape("FFF", "FDF", "FFF")
                .setIngredient('F', Material.IRON_BARS)
                .setIngredient('D', Material.DIAMOND);
        Bukkit.addRecipe(recipeSpawner);
        //END SPAWNER

        //SPAWNER ZOMBIE
        setSpawnerCraft("Zombie", Material.ROTTEN_FLESH, EntityType.ZOMBIE);
        //END SPAWNER ZOMBIE

        //SPAWNER SKELETON
        setSpawnerCraft("Squelette", Material.BONE, EntityType.SKELETON);
        //END SPAWNER SKELETON

        //SPAWNER CREEPER
        setSpawnerCraft("Creeper", Material.GUNPOWDER, EntityType.CREEPER);
        //END SPAWNER CREEPER

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event){
        if(event.getEntity() instanceof Creeper)
            event.setCancelled(true);
    }

    public void setSpawnerCraft(String mobName, Material itemMob, EntityType entityType){
        ItemStack itemSpawner = new ItemStack(Material.SPAWNER);
        BlockStateMeta metaSpawner = (BlockStateMeta) itemSpawner.getItemMeta();
        metaSpawner.setDisplayName("Spawner à "+mobName);
        CreatureSpawner creatureSpawner = (CreatureSpawner) metaSpawner.getBlockState();
        creatureSpawner.setSpawnedType(entityType);
        metaSpawner.setBlockState(creatureSpawner);
        itemSpawner.setItemMeta(metaSpawner);
        ShapedRecipe recipeSpawne = new ShapedRecipe(itemSpawner);
        recipeSpawne.shape("   ", " SI", "   ");
        recipeSpawne.setIngredient('S', Material.SPAWNER);
        recipeSpawne.setIngredient('I', itemMob);
        Bukkit.addRecipe(recipeSpawne);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        e.setJoinMessage(ChatColor.AQUA+e.getPlayer().getName()+", Ce/cette batard(e) a rejoint !");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        e.setQuitMessage(ChatColor.AQUA+e.getPlayer().getName()+", , Ce/cette batard(e) a quitté !");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        e.setDeathMessage(ChatColor.AQUA+e.getEntity().getName()+", Ah batard tes mort ! (Raison: "+e.getEntity().getLastDamageCause().getCause().name()+")");
    }

    @EventHandler
    public void onBreackBlock(BlockBreakEvent event) {
        Player p = event.getPlayer();
        ItemStack pHandItem = p.getItemInHand();
        boolean dropItemOriginal = false;
        if (event.getBlock().getDrops(pHandItem).size() <= 0)
            return;
        if (pHandItem.getItemMeta() == null)
            return;
        if (pHandItem.getItemMeta().getEnchants().containsKey(Enchantment.SILK_TOUCH))
            dropItemOriginal = true;

        Location blockDropLoc = p.getLocation();
        if (event.getBlock().getType() == Material.IRON_ORE && !dropItemOriginal) {
            event.setDropItems(false);
            ((ExperienceOrb)p.getWorld().spawn(blockDropLoc, ExperienceOrb.class)).setExperience(1);
            p.getWorld().dropItem(blockDropLoc, new ItemStack(Material.IRON_INGOT));
        } else if (event.getBlock().getType() == Material.GOLD_ORE && !dropItemOriginal) {
            event.setDropItems(false);
            ((ExperienceOrb)p.getWorld().spawn(blockDropLoc, ExperienceOrb.class)).setExperience(1);
            p.getWorld().dropItem(blockDropLoc, new ItemStack(Material.GOLD_INGOT));
        } else if (event.getBlock().getType() == Material.NETHER_QUARTZ_ORE && !dropItemOriginal) {
            event.setDropItems(false);
            ((ExperienceOrb)p.getWorld().spawn(blockDropLoc, ExperienceOrb.class)).setExperience(8);
            p.getWorld().dropItem(blockDropLoc, new ItemStack(Material.QUARTZ));
        }
    }
}
