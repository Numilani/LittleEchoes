package me.numilani.littleechoes;

import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.util.Config;
import com.hypixel.hytale.server.npc.NPCPlugin;

import me.numilani.littleechoes.data.PluginConfig;
import me.numilani.littleechoes.npc.components.BuilderActionBreakBlock;
import me.numilani.littleechoes.npc.components.BuilderActionDamageRadius;
import me.numilani.littleechoes.npc.components.BuilderActionDropInventory;

import javax.annotation.Nonnull;

public class Main extends JavaPlugin {

  public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
  public final Config<PluginConfig> cfg;

  public Main(@Nonnull JavaPluginInit init) {
    super(init);
    cfg = this.withConfig("PluginConfig", PluginConfig.CODEC);
  }

  @Override
  protected void setup() {
    super.setup();
    cfg.save();

    NPCPlugin.get().registerCoreComponentType("BreakBlock", BuilderActionBreakBlock::new);
    NPCPlugin.get().registerCoreComponentType("DamageRadius", BuilderActionDamageRadius::new);
    NPCPlugin.get().registerCoreComponentType("DropInventory", BuilderActionDropInventory::new);
  }

  @Override
  protected void start() {
  }

}
