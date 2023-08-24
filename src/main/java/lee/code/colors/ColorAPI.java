package lee.code.colors;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ColorAPI {

    public static void setPlayerColorData(OfflinePlayer player, String prefix, String suffix, String priority, ChatColor color) {
        Colors.getInstance().getCacheManager().updatePlayerColorData(player, prefix, suffix, priority, color);
        updatePlayerColorData(player);
    }

    public static void setPrefix(OfflinePlayer player, String prefix) {
        Colors.getInstance().getCacheManager().getCachePlayers().setPrefix(player.getUniqueId(), prefix);
        updatePlayerColorData(player);
    }

    public static void setSuffix(OfflinePlayer player, String suffix) {
        Colors.getInstance().getCacheManager().getCachePlayers().setSuffix(player.getUniqueId(), suffix);
        updatePlayerColorData(player);
    }

    public static void setPriority(OfflinePlayer player, String priority) {
        Colors.getInstance().getCacheManager().getCachePlayers().setPriority(player.getUniqueId(), priority);
        updatePlayerColorData(player);
    }

    public static void setColor(OfflinePlayer player, ChatColor color) {
        Colors.getInstance().getCacheManager().getCachePlayers().setColor(player.getUniqueId(), color);
        updatePlayerColorData(player);
    }

    public static char getColorChar(UUID uuid) {
        return Colors.getInstance().getCacheManager().getCachePlayers().getColor(uuid).getChar();
    }

    public static void updatePlayerColorData(OfflinePlayer player) {
        if (player.isOnline()) {
            final Player onlinePlayer = player.getPlayer();
            if (onlinePlayer == null) return;
            Colors.getInstance().getCacheManager().updatePlayerColor(onlinePlayer);
        }
    }

    public static void updatePlayerColorData(Player player) {
        Colors.getInstance().getCacheManager().updatePlayerColor(player);
    }
}
