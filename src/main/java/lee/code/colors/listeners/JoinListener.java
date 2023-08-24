package lee.code.colors.listeners;

import lee.code.colors.Colors;
import lee.code.colors.database.CacheManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private final Colors colors;

    public JoinListener(Colors colors) {
        this.colors = colors;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        final CacheManager cacheManager = colors.getCacheManager();
        final UUID uuid = e.getPlayer().getUniqueId();
        if (!cacheManager.getCachePlayers().hasPlayerData(uuid)) cacheManager.getCachePlayers().createPlayerData(uuid);
        cacheManager.updatePlayerColor(e.getPlayer());
    }
}
