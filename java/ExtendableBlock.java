package net.minecraft.src;

import java.util.Random;

import clojure.lang.IFn;

public class ExtendableBlock extends Block {

	final private IFn droppedfn;
	final private IFn quantityfn;

	public ExtendableBlock(Integer par1, Integer par2, Material par3Material, IFn droppedfn, IFn quantityfn) {
		super(par1, par2, par3Material);
		this.droppedfn = droppedfn;
		this.quantityfn = quantityfn;
	}
	
	@Override
	public int idDropped(int i, Random random, int j) {
		return (Integer) droppedfn.invoke(i, random, j);
	}
	
	@Override
	public int quantityDropped(Random random) {
		return (Integer) quantityfn.invoke(random);
	}

	public Block hardness(float par1) {
		return super.setHardness(par1);
	}
	
	public Block lightValue(float par1) {
		return super.setLightValue(par1);
	}
	
	public Block blockName(String par1Str) {
		return super.setBlockName(par1Str);
	}
	
	public void setTexture(int val) {
		blockIndexInTexture = val;
	}

}
