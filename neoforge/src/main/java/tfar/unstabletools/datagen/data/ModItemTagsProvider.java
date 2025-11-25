package tfar.unstabletools.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.unstabletools.UnstableTools;
import tfar.unstabletools.init.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput p_126511_, CompletableFuture<HolderLookup.Provider> pLookupProvider, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126511_,pLookupProvider,blockTagsProvider.contentsGetter(), UnstableTools.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(ModItems.UNSTABLE_INGOT);
        tag(ItemTags.AXES).add(ModItems.UNSTABLE_AXE);
        tag(ItemTags.HOES).add(ModItems.UNSTABLE_HOE);
        tag(ItemTags.PICKAXES).add(ModItems.UNSTABLE_PICKAXE);
        tag(ItemTags.SHOVELS).add(ModItems.UNSTABLE_SHOVEL);
        tag(ItemTags.SWORDS).add(ModItems.UNSTABLE_SWORD);

        tag(ItemTags.BOW_ENCHANTABLE).add(ModItems.UNSTABLE_BOW);
        tag(ItemTags.MINING_ENCHANTABLE).add(ModItems.UNSTABLE_SHEARS);

        tag(ItemTags.HEAD_ARMOR).add(ModItems.UNSTABLE_HELMET);
        tag(ItemTags.CHEST_ARMOR).add(ModItems.UNSTABLE_CHESTPLATE);
        tag(ItemTags.LEG_ARMOR).add(ModItems.UNSTABLE_LEGGINGS);
        tag(ItemTags.FOOT_ARMOR).add(ModItems.UNSTABLE_BOOTS);

        tag(Tags.Items.TOOLS_BOW).add(ModItems.UNSTABLE_BOW);
        tag(Tags.Items.TOOLS_FISHING_ROD).add(ModItems.UNSTABLE_FISHING_ROD);
    }
}
