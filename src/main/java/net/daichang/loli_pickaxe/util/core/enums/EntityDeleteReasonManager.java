package net.daichang.loli_pickaxe.util.core.enums;

import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class EntityDeleteReasonManager {
    private static final Map<Entity, IRemove> entityDeleteReasons = new HashMap<>();

    public static void setDeleteReason(Entity entity, IRemove reason) {
        entityDeleteReasons.put(entity, reason);
    }

    public static IRemove getDeleteReason(Entity entity) {
        return entityDeleteReasons.getOrDefault(entity, null);
    }
}
