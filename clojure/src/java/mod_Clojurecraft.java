package net.minecraft.src;

import java.io.IOException;

import clojure.lang.IFn;
import clojure.lang.RT;
import clojure.lang.Var;

public class mod_Clojurecraft extends BaseMod {
	

	@Override
	public String getVersion() {
		return "0.1";
	}
	
	@Override
	public void load() {
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		try {
			//Don't do this: use user home.
			clojure.lang.Compiler.loadFile("/Users/danielribeiro/minecraft_modding/clojure/src/minecraftcl.clj");
			clojure.lang.RT.var("minecraftcl", "call").invoke(MinecraftConstants.blocks,
			MinecraftConstants.items, MinecraftConstants.materials, ExtendableBlock.class, ItemStack.class,
				ModLoader.class);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}


}
