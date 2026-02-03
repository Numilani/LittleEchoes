package me.numilani.littleechoes.npc.components;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.holder.AssetHolder;
import com.hypixel.hytale.server.npc.asset.builder.validators.AssetValidator;
import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleRangeValidator;
import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleSequenceValidator;
import com.hypixel.hytale.server.npc.asset.builder.validators.asset.ItemDropListExistsValidator;
import com.hypixel.hytale.server.npc.asset.builder.validators.asset.ItemExistsValidator;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionWithDelay;
import com.hypixel.hytale.server.npc.instructions.Action;
import com.hypixel.hytale.server.npc.util.AimingHelper;
import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
import com.hypixel.hytale.server.npc.util.expression.Scope;
import com.hypixel.hytale.server.npc.validators.NPCLoadTimeValidationHelper;
import java.util.List;
import javax.annotation.Nonnull;

public class BuilderActionDropInventory extends BuilderActionWithDelay {
  protected float throwSpeed;

  @Nonnull
  @Override
  public String getShortDescription() {
    return "Drop an item";
  }

  @Nonnull
  @Override
  public String getLongDescription() {
    return "Drop an item. Can be a specific item, or from a drop table";
  }

  @Nonnull
  public Action build(@Nonnull BuilderSupport builderSupport) {
    return new ActionDropInventory(this, builderSupport);
  }

  @Nonnull
  public BuilderActionDropInventory readConfig(@Nonnull JsonElement data) {
    this.getFloat(
        data,
        "ThrowSpeed",
        s -> this.throwSpeed = s,
        1.0F,
        DoubleRangeValidator.fromExclToIncl(0.0, Float.MAX_VALUE),
        BuilderDescriptorState.Stable,
        "The throw speed to use",
        null);
    return this;
  }

  @Nonnull
  @Override
  public BuilderDescriptorState getBuilderDescriptorState() {
    return BuilderDescriptorState.Stable;
  }

  @Override
  public boolean validate(
      String configName, @Nonnull NPCLoadTimeValidationHelper validationHelper, ExecutionContext context,
      Scope globalScope, @Nonnull List<String> errors) {
    boolean result = super.validate(configName, validationHelper, context, globalScope, errors);
    return result;
  }
  public float getThrowSpeed() {
    return this.throwSpeed;
  }
}
