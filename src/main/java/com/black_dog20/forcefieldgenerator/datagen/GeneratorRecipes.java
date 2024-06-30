package com.black_dog20.forcefieldgenerator.datagen;

import com.black_dog20.bml.datagen.BaseRecipeProvider;
import com.black_dog20.forcefieldgenerator.ForceFieldGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

import static com.black_dog20.forcefieldgenerator.items.ModItems.*;

public class GeneratorRecipes extends BaseRecipeProvider {

    public GeneratorRecipes(DataGenerator generator) {
        super(generator.getPackOutput(), ForceFieldGenerator.MOD_ID);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(FORCE_FIELD_GENERATOR_TEMPLATE.get()), Ingredient.of(Tags.Items.INGOTS_NETHERITE), Ingredient.of(Tags.Items.NETHER_STARS), RecipeCategory.MISC, STAR_INFUSED_NETHERITE_INGOT.get())
                .unlocks("has_netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
                .unlocks("has_nether_star", has(Tags.Items.NETHER_STARS))
                .save(consumer, getRecipeId(STAR_INFUSED_NETHERITE_INGOT.get()));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FORCE_FIELD_GENERATOR_TEMPLATE.get())
                .define('o', Tags.Items.INGOTS_NETHERITE)
                .define('d', Tags.Items.GEMS_EMERALD)
                .define('s', Tags.Items.NETHER_STARS)
                .pattern("oso")
                .pattern("odo")
                .pattern("ooo")
                .unlockedBy("has_netherite_ingot", has(Tags.Items.INGOTS_NETHERITE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FORCE_FIELD_GENERATOR_TEMPLATE.get())
                .define('t', FORCE_FIELD_GENERATOR_TEMPLATE.get())
                .define('d', Tags.Items.GEMS_EMERALD)
                .define('o', Tags.Items.INGOTS_NETHERITE)
                .pattern("dtd")
                .pattern("dod")
                .pattern("ddd")
                .unlockedBy("has_template", has(FORCE_FIELD_GENERATOR_TEMPLATE.get()))
                .save(consumer, new ResourceLocation(ForceFieldGenerator.MOD_ID, "force_field_generator_template_duplication"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FORCE_FIELD_GENERATOR_PLATE.get())
                .define('n', STAR_INFUSED_NETHERITE_INGOT.get())
                .define('e', Tags.Items.GEMS_EMERALD)
                .pattern(" n ")
                .pattern("nen")
                .pattern(" n ")
                .unlockedBy("has_star_infused_netherite_ingot", has(STAR_INFUSED_NETHERITE_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FORCE_FIELD_GENERATOR_CORE.get())
                .define('n', FORCE_FIELD_GENERATOR_PLATE.get())
                .define('e', Tags.Items.STORAGE_BLOCKS_EMERALD)
                .define('s', Tags.Items.NETHER_STARS)
                .define('d', Items.DRAGON_BREATH)
                .define('r', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .pattern("nen")
                .pattern("dsd")
                .pattern("nrn")
                .unlockedBy("has_force_field_generator_plate", has(FORCE_FIELD_GENERATOR_PLATE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FORCE_FIELD_GENERATOR.get())
                .define('n', FORCE_FIELD_GENERATOR_PLATE.get())
                .define('e', Tags.Items.STORAGE_BLOCKS_EMERALD)
                .define('d', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .define('c', FORCE_FIELD_GENERATOR_CORE.get())
                .pattern("nen")
                .pattern("dcd")
                .pattern("nen")
                .unlockedBy("has_force_field_generator_core", has(FORCE_FIELD_GENERATOR_CORE.get()))
                .save(consumer);

    }

    private ResourceLocation getRecipeId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}
