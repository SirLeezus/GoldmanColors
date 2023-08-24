package lee.code.colors;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class ColorAPI {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void setPlayerColorData(OfflinePlayer player, String prefix, String suffix, String priority, ChatColor color) {
        lock.lock();
        try {
            Colors.getInstance().getCacheManager().updatePlayerColorData(player, prefix, suffix, priority, color);
            updatePlayerColorData(player);
        } finally {
            lock.unlock();
        }
    }

    public static void setColor(OfflinePlayer player, ChatColor color) {
        lock.lock();
        try {
            Colors.getInstance().getCacheManager().getCachePlayers().setColor(player.getUniqueId(), color);
            updatePlayerColorData(player);
        } finally {
            lock.unlock();
        }
    }

    public static String getColorChar(UUID uuid) {
        if (!Colors.getInstance().getCacheManager().getCachePlayers().hasPlayerData(uuid)) return "&6";
        return "&" + Colors.getInstance().getCacheManager().getCachePlayers().getColor(uuid).getChar();
    }

    public static String getNameColor(UUID uuid, String name) {
        if (!Colors.getInstance().getCacheManager().getCachePlayers().hasPlayerData(uuid)) return "&6";
        return "&" + Colors.getInstance().getCacheManager().getCachePlayers().getColor(uuid).getChar() + name;
    }

    private static void updatePlayerColorData(OfflinePlayer player) {
        if (player.isOnline()) {
            final Player onlinePlayer = player.getPlayer();
            if (onlinePlayer == null) return;
            Colors.getInstance().getCacheManager().updatePlayerColor(onlinePlayer);
        }
    }

    private static void updatePlayerColorData(Player player) {
        Colors.getInstance().getCacheManager().updatePlayerColor(player);
    }
}
