package net.minecraft.src;

class HashEntry {
	final int hashEntry;
	Object valueEntry;
	HashEntry nextEntry;
	final int slotHash;

	HashEntry(int var1, int var2, Object var3, HashEntry var4) {
		this.valueEntry = var3;
		this.nextEntry = var4;
		this.hashEntry = var2;
		this.slotHash = var1;
	}

	public final int getHash() {
		return this.hashEntry;
	}

	public final Object getValue() {
		return this.valueEntry;
	}

	public final boolean equals(Object var1) {
		if(!(var1 instanceof HashEntry)) {
			return false;
		} else {
			HashEntry var2 = (HashEntry)var1;
			Integer var3 = Integer.valueOf(this.getHash());
			Integer var4 = Integer.valueOf(var2.getHash());
			if(var3 == var4 || var3 != null && var3.equals(var4)) {
				Object var5 = this.getValue();
				Object var6 = var2.getValue();
				if(var5 == var6 || var5 != null && var5.equals(var6)) {
					return true;
				}
			}

			return false;
		}
	}

	public final int hashCode() {
		return MCHashTable.getHash(this.hashEntry);
	}

	public final String toString() {
		return this.getHash() + "=" + this.getValue();
	}
}
