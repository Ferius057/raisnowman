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
    private final Player p;
    private static Snowman snowMan;
    private static String name = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Settings.snowman-name"));
    private Integer radius = Main.getInstance().getConfig().getInt("Settings.spawn-radius");
    private boolean snowmanExists = false;

    public SnowmanRunnable(Player player) {
        this.p = player;
    }

    @Override
    public void run() {
        if (snowmanExists) {
            snowMan.remove();
            SnowmanCommand.getTask().cancel();
        }

        Location pLocation = p.getLocation();
        World world = p.getWorld();
        Integer snowX = getRandomNumber(pLocation.getX()-radius/2, pLocation.getX()+radius/2);
        Integer snowZ= getRandomNumber(pLocation.getZ()-radius/2, pLocation.getZ()+radius/2);
        Integer snowY = world.getHighestBlockAt(snowX, snowZ).getY();
        Location snowLocation = new Location(world, snowX, snowY, snowZ);

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            snowMan = (Snowman) Bukkit.getWorld("world").spawnEntity(snowLocation, EntityType.SNOWMAN);
            snowMan.setCustomName(name);
            snowMan.setAI(false);
            System.out.println(snowLocation);
            snowmanExists = true;
        }, 1L);
    }

    public int getRandomNumber(double min, double max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getSnowmanName() {
        return name;
    }

    public static Snowman getSnowman() {
        return snowMan;
    }
}
