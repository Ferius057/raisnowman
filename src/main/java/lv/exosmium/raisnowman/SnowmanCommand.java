package lv.exosmium.raisnowman;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class SnowmanCommand implements CommandExecutor {
    private static BukkitTask snowTask;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            snowTask = new SnowmanRunnable(p).runTaskTimerAsynchronously(Main.getInstance(), 1L, 24000L);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Messages.snowman-spawn")));
            return true;
        }

        sender.sendMessage(ChatColor.AQUA + "Команда доступна только для игроков!");
        return true;
    }

    public static BukkitTask getTask() {
        return snowTask;
    }

    public static void setTask(BukkitTask task) {
        snowTask = task;
    }
}
