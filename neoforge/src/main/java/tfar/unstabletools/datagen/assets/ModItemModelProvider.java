package tfar.unstabletools.datagen.assets;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tfar.unstabletools.UnstableTools;
import tfar.unstabletools.init.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, UnstableTools.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        registerArmors();
    }

    public void registerArmors() {
        armorWithTrim(ModItems.UNSTABLE_HELMET,modLoc("item/"+ BuiltInRegistries.ITEM.getKey(ModItems.UNSTABLE_HELMET).getPath()));
        armorWithTrim(ModItems.UNSTABLE_CHESTPLATE,modLoc("item/"+ BuiltInRegistries.ITEM.getKey(ModItems.UNSTABLE_CHESTPLATE).getPath()));
        armorWithTrim(ModItems.UNSTABLE_LEGGINGS,modLoc("item/"+ BuiltInRegistries.ITEM.getKey(ModItems.UNSTABLE_LEGGINGS).getPath()));
        armorWithTrim(ModItems.UNSTABLE_BOOTS,modLoc("item/"+ BuiltInRegistries.ITEM.getKey(ModItems.UNSTABLE_BOOTS).getPath()));

    }

    protected ItemModelBuilder generated(ItemLike itemLike, ResourceLocation texture) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath(), "item/generated").texture("layer0", texture);
    }

    protected ItemModelBuilder armorWithTrim(ArmorItem armorItem, ResourceLocation texture) {
        ItemModelBuilder builder = generated(armorItem, texture);
        for (ItemModelGenerators.TrimModelData trimModelData : ItemModelGenerators.GENERATED_TRIM_MODELS) {
            String trimId = trimModelData.name(armorItem.getMaterial());
            ItemModelBuilder override = withExistingParent(builder.getLocation().withSuffix("_" + trimId + "_trim").getPath(), "item/generated")
                    .texture("layer0", texture)
                    .texture("layer1", ResourceLocation.withDefaultNamespace("trims/items/" + armorItem.getType().getName() + "_trim_" + trimId));
            builder.override()
                    .predicate(ItemModelGenerators.TRIM_TYPE_PREDICATE_ID, trimModelData.itemModelIndex())
                    .model(override);
        }
        return builder;
    }
}
