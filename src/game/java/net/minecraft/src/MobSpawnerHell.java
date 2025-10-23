package net.minecraft.src;

import net.peyton.eagler.minecraft.suppliers.EntitySupplier;

public class MobSpawnerHell extends MobSpawnerBase {
	public MobSpawnerHell() {
		this.biomeMonsters = new EntitySupplier[]{EntityGhast::new, EntityPigZombie::new};
		this.biomeCreatures = new EntitySupplier[0];
	}
}
