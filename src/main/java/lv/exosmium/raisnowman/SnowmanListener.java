package lv.exosmium.raisnowman;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class SnowmanListener implements Listener {
    private String snowCommand = Main.getInstance().getConfig().getString("Settings.snowman-reward");

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        if (event.getRightClicked().getType().equals(EntityType.SNOWMAN) && event.getRightClicked().getCustomName().equals(SnowmanRunnable.getSnowmanName()) && event.getHand().equals(EquipmentSlot.HAND)) {

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), snowCommand.replace("%player%", p.getName()));
            respawnSnowman(p);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Messages.snowman-found")));
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler (priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlaceBlock(EntityBlockFormEvent event) {
        if (event.getEntity().getCustomName().equals(SnowmanRunnable.getSnowmanName())) {
            event.setCancelled(true);
        }
    }

    private void respawnSnowman(Player p) {
        SnowmanRunnable.removeSnowman();
        SnowmanCommand.cancelTask();
        SnowmanCommand.setTask(new SnowmanRunnable(p).runTaskTimer(Main.getInstance(), 1L, 24000L));
    }
}
