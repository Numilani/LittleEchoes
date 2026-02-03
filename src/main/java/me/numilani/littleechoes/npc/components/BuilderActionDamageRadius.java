package me.numilani.littleechoes.npc.components;

import java.util.EnumSet;

import javax.annotation.Nonnull;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.Feature;
import com.hypixel.hytale.server.npc.asset.builder.holder.BooleanHolder;
import com.hypixel.hytale.server.npc.asset.builder.holder.DoubleHolder;
import com.hypixel.hytale.server.npc.asset.builder.validators.DoubleSingleValidator;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.corecomponents.world.ActionPlaceBlock;
import com.hypixel.hytale.server.npc.instructions.Action;

public class BuilderActionDamageRadius extends BuilderActionBase {
   protected final DoubleHolder radius = new DoubleHolder();
   protected final DoubleHolder damage = new DoubleHolder();

   @Nonnull
   @Override
   public String getShortDescription() {
      return "Break a block (chosen by another action) at a position returned by a Sensor if close enough";
   }

   @Nonnull
   @Override
   public String getLongDescription() {
      return this.getShortDescription();
   }

   @Nonnull
   public Action build(@Nonnull BuilderSupport builderSupport) {
      return new ActionDamageRadius(this, builderSupport);
   }

   @Nonnull
   @Override
   public BuilderDescriptorState getBuilderDescriptorState() {
      return BuilderDescriptorState.Stable;
   }

   @Nonnull
   public BuilderActionDamageRadius readConfig(@Nonnull JsonElement data) {
      this.getDouble(
         data,
         "Radius",
         this.radius,
         3.0,
         DoubleSingleValidator.greater0(),
         BuilderDescriptorState.Stable,
         "The range to target position before block will be placed",
         null
      );
      this.getDouble(
      data,
      "Damage",
      this.damage,
      1.0,
      DoubleSingleValidator.greater0(),
      BuilderDescriptorState.Stable,
      "The amount of damage dealt to any entity in radius (except players)",
      null
    );
      return this;
   }

   public double getRadius(@Nonnull BuilderSupport support) {
      return this.radius.get(support.getExecutionContext());
   }

   public double getDamage(@Nonnull BuilderSupport support) {
      return this.damage.get(support.getExecutionContext());
   }

}

