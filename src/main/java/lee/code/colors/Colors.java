package lee.code.colors;

import lee.code.colors.database.CacheManager;
import lee.code.colors.database.DatabaseManager;
import lee.code.colors.listeners.HealthListener;
import lee.code.colors.listeners.JoinListener;
import lee.code.colors.listeners.QuitListener;
import lee.code.colors.scoreboard.ScoreboardManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Colors  extends JavaPlugin {
  @Getter private static Colors instance;
  @Getter private ScoreboardManager scoreboardManager;
  @Getter private CacheManager cacheManager;
  private DatabaseManager databaseManager;

  @Override
  public void onEnable() {
    instance = this;
    this.databaseManager = new DatabaseManager(this);
    this.cacheManager = new CacheManager(this, databaseManager);
    this.scoreboardManager = new ScoreboardManager();
    databaseManager.initialize(false);
    registerListeners();
  }

  @Override
  public void onDisable() {
    databaseManager.closeConnection();
  }

  private void registerListeners() {
    getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    getServer().getPluginManager().registerEvents(new QuitListener(this), this);
    getServer().getPluginManager().registerEvents(new HealthListener(this), this);
  }
}
