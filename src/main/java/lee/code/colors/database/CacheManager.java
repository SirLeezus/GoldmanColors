package lee.code.colors.database;

import lee.code.colors.Colors;
import lee.code.colors.database.cache.CachePlayers;
import lee.code.colors.database.tables.PlayerTable;
import lee.code.colors.scoreboard.system.BoardBuilder;
import lee.code.colors.scoreboard.system.CollisionRule;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CacheManager {
  private final Colors colors;
  @Getter private final CachePlayers cachePlayers;

  public CacheManager(Colors colors, DatabaseManager databaseManager) {
    this.colors = colors;
    this.cachePlayers = new CachePlayers(databaseManager);
  }

  public void updatePlayerColorData(OfflinePlayer player, String prefix, String suffix, String priority, ChatColor color) {
    PlayerTable playerTable = cachePlayers.getPlayerTable(player.getUniqueId());
    playerTable.setPrefix(prefix);
    playerTable.setSuffix(suffix);
    playerTable.setPriority(priority);
    playerTable.setColor(color.name());
    cachePlayers.updatePlayerDatabase(playerTable);
  }

  public void updatePlayerColor(Player player) {
    final UUID uuid = player.getUniqueId();
    new BoardBuilder(colors, player)
      .collisionRule(CollisionRule.NEVER)
      .nameColor(cachePlayers.getColor(uuid))
      .prefix(cachePlayers.getPrefix(uuid))
      .suffix(cachePlayers.getSuffix(uuid))
      .priority(cachePlayers.getPriority(uuid))
      .create();
  }
}
