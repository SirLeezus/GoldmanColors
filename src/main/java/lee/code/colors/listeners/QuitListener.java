package lee.code.colors.listeners;

import lee.code.colors.Colors;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final Colors colors;

    public QuitListener(Colors colors) {
        this.colors = colors;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        colors.getScoreboardManager().removePlayerBoard(e.getPlayer().getUniqueId());
    }
}
