package org.voxelutopia.ultramarine.init.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.voxelutopia.ultramarine.init.registry.ModItems;

/**
 * Name: Ultramarine / ItemEntityMixin
 * Author: Tapio
 * CreateTime: 2023/12/11 22:23
 */
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow
    public abstract ItemStack getItem();

    @Shadow
    public abstract void setItem(ItemStack itemStack);

    @Shadow
    private int age;

    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/ItemEntity;discard()V"
            ),
            cancellable = true
    )
    private void inject$tick(CallbackInfo ci) {
        final ItemStack item = getItem();
        if (!item.is(ModItems.FIRED_BRICK) || !isInWater()) return;
        ci.cancel();
        setItem(new ItemStack(ModItems.CYAN_BRICK, item.getCount()));
        level().playSound(null, this, SoundEvents.LAVA_EXTINGUISH, SoundSource.NEUTRAL, 0.5f, 1.0f);
        age = 0;
    }
}
