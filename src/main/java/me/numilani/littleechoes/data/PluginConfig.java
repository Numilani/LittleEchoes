package me.numilani.littleechoes.data;

import lombok.Getter;
import lombok.Setter;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class PluginConfig
{
  public static final BuilderCodec<PluginConfig> CODEC = BuilderCodec.builder(PluginConfig.class, PluginConfig::new)
  .append(new KeyedCodec<String>("UnusedValue", Codec.STRING),
  (cfg,val) -> cfg.unused = val,
  (cfg) -> cfg.unused).add()
  .build();

  @Getter @Setter private String unused = "testVal";
}

