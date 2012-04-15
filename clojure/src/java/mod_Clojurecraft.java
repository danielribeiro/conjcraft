package net.minecraft.src;

import java.io.File;
import java.io.IOException;

public class mod_Clojurecraft extends BaseMod {

	@Override
	public String getVersion() {
		return "0.1";
	}
	
	@Override
	public void load() {
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		try {
			File file = new File(new File(System.getProperty("user.home"), "clojurecraft"), "clojurecraft_main.clj");
			clojure.lang.Compiler.loadFile(file.getAbsolutePath());
			clojure.lang.RT.var("minecraftcl", "call").invoke(MinecraftConstants.blocks,
			MinecraftConstants.items, MinecraftConstants.materials, ExtendableBlock.class, ItemStack.class,
				ModLoader.class);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
