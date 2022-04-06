package lv.exosmium.raisnowman;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.scheduler.BukkitRunnable;

public class SnowmanRunnable extends BukkitRunnable {
    private final Player player;
    private static String name = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Settings.snowman-name"));
    private Integer radius = Main.getInstance().getConfig().getInt("Settings.spawn-radius");
    private boolean snowmanExists = false;
    private static Snowman snowMan;

    public SnowmanRunnable(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if (snowmanExists) {
            snowMan.remove();
            SnowmanCommand.cancelTask();
        }

        Location playerLocation = player.getLocation();
        World world = player.getWorld();
        Integer snowX = getRandomNumber(playerLocation.getX()-radius/2, playerLocation.getX()+radius/2);
        Integer snowZ= getRandomNumber(playerLocation.getZ()-radius/2, playerLocation.getZ()+radius/2);
        Integer snowY = world.getHighestBlockAt(snowX, snowZ).getY();
        Location snowLocation = new Location(world, snowX, snowY, snowZ);

        snowMan = (Snowman) Bukkit.getWorld("world").spawnEntity(snowLocation, EntityType.SNOWMAN);
        snowMan.setCustomName(name);
        snowMan.setAI(false);
        snowmanExists = true;
    }

    private int getRandomNumber(double min, double max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getSnowmanName() {
        return name;
    }

    public static void removeSnowman() { snowMan.remove(); }
}
