package org.voxelutopia.ultramarine.common.menu;

import net.minecraft.world.entity.player.Inventory;
import org.voxelutopia.ultramarine.common.tile.ContainerDecorativeBlockEntity;

@FunctionalInterface
public interface ContainerDecorativeBlockMenuProvider<M extends ContainerDecorativeBlockMenu> {
    M getMenu(int id, Inventory inventory, ContainerDecorativeBlockEntity blockEntity);
}
