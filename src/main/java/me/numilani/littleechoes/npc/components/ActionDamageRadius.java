package me.numilani.littleechoes.npc.components;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.block.BlockUtil;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.math.util.MathUtil;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.entity.EntityUtils;
import com.hypixel.hytale.server.core.entity.ItemUtils;
import com.hypixel.hytale.server.core.entity.LivingEntity;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.collision.WorldUtil;
import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.TargetUtil;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.corecomponents.world.builders.BuilderActionPlaceBlock;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.CachedPositionProvider;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import com.hypixel.hytale.server.npc.util.BlockPlacementHelper;
import com.hypixel.hytale.server.npc.util.InventoryHelper;

public class ActionDamageRadius extends ActionBase {
  protected static final ComponentType<EntityStore, BoundingBox> BOUNDING_BOX_COMPONENT_TYPE = BoundingBox
      .getComponentType();
  protected final double radius;
  protected final double damage;
  protected final NPCEntity npc;

  public ActionDamageRadius(@Nonnull BuilderActionDamageRadius builder, @Nonnull BuilderSupport support) {
    super(builder);
    this.radius = builder.getRadius(support);
    this.damage = builder.getDamage(support);
    this.npc = support.getEntity();
  }

  @Override
  public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt,
      @Nonnull Store<EntityStore> store) {
    super.execute(ref, role, sensorInfo, dt, store);

    var tc = store.getComponent(ref, TransformComponent.getComponentType());
    var entitiesInRange = TargetUtil.getAllEntitiesInSphere(tc.getPosition(), this.radius, store);

    for (var e : entitiesInRange) {
      if (store.getComponent(e, PlayerRef.getComponentType()) != null)
        continue;

      Damage damageEntry = new Damage(new Damage.EntitySource(npc.getReference()), DamageCause.PHYSICAL,
          (float) this.damage);
      if (npc.getReference().equals(e))continue;
      DamageSystems.executeDamage(e, store, damageEntry);

      // var stats = store.getComponent(e, EntityStatMap.getComponentType());
      // if (stats != null) {
      //   if (stats.get(DefaultEntityStatTypes.getHealth()).get() - (float) damage <= 0f) {
      //     stats.setStatValue(DefaultEntityStatTypes.getHealth(), 0f);
      //   } else {
      //     stats.setStatValue(DefaultEntityStatTypes.getHealth(),
      //         stats.get(DefaultEntityStatTypes.getHealth()).get() - (float) damage);
      //   }
      // }
    }

    return true;
  }
}
