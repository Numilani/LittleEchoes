package me.numilani.littleechoes.npc.components;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.entity.EntityUtils;
import com.hypixel.hytale.server.core.entity.ItemUtils;
import com.hypixel.hytale.server.core.entity.LivingEntity;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionWithDelay;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;

public class ActionDropInventory extends ActionWithDelay {
 protected final Vector3d dropDirection = new Vector3d(0,0,0);
  protected float throwSpeed;

  public ActionDropInventory(@Nonnull BuilderActionDropInventory builder, @Nonnull BuilderSupport support) {
    super(builder, support);
    this.throwSpeed = builder.getThrowSpeed();
  }

  @Override
  public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt,
      @Nonnull Store<EntityStore> store) {
    return super.canExecute(ref, role, sensorInfo, dt, store) && !this.isDelaying();
  }

  @Override
  public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt,
      @Nonnull Store<EntityStore> store) {
    super.execute(ref, role, sensorInfo, dt, store);

    LivingEntity entity = (LivingEntity) EntityUtils.getEntity(ref, store);
    var storage = entity.getInventory().getStorage();

    for (var stack : storage.dropAllItemStacks()) {
      ItemUtils.throwItem(ref, store, stack, new Vector3d(0, 0, 0), 0);
    }
    return true;
  }

}
