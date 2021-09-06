package fr.krishenk.castel.client.constants;

public enum EnumCastelMaterials {
	WOOD("WOOD", 0, 0, 59, 2.0F, 0, 15),
	IRON("IRON", 1, 2, 250, 6.0F, 2, 14);
	
	private final int harvestLevel;
	private final int maxUses;
	private final float efficiencyOnPropperMaterial;
	private final int damageVsEntity;
	private final int enchantability;
	// $FF: synthetic field
	private static final EnumCastelMaterials[] $VALUES = new EnumCastelMaterials[] {WOOD, IRON};
	
	private EnumCastelMaterials(String material, int id, int harvestLevel, int maxUses, float efficiencyOnPropperMaterial, int damageVsEntity, int enchantability) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiencyOnPropperMaterial = efficiencyOnPropperMaterial;
		this.damageVsEntity = damageVsEntity;
		this.enchantability = enchantability;
	}

	public int getHarvestLevel() {
		return this.harvestLevel;
	}

	public int getMaxUses() {
		return this.maxUses;
	}

	public float getEfficiencyOnPropperMaterial() {
		return this.efficiencyOnPropperMaterial;
	}

	public int getDamageVsEntity() {
		return this.damageVsEntity;
	}

	public int getEnchantability() {
		return this.enchantability;
	}
	
	
	
}
