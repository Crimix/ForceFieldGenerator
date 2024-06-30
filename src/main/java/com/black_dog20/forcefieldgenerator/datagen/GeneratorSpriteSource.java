package com.black_dog20.forcefieldgenerator.datagen;

import com.black_dog20.bml.datagen.BaseSpriteSourceProvider;
import com.black_dog20.forcefieldgenerator.ForceFieldGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GeneratorSpriteSource extends BaseSpriteSourceProvider {

    public GeneratorSpriteSource(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, ForceFieldGenerator.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addSources() {
        blockAtlas("item/empty_forcefield_generator_slot");
    }
}
