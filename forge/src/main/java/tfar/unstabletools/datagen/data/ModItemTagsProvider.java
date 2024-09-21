package tfar.unstabletools.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.unstabletools.UnstableTools;
import tfar.unstabletools.UnstableToolsForge;
import tfar.unstabletools.init.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput p_126511_, CompletableFuture<HolderLookup.Provider> pLookupProvider, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126511_,pLookupProvider,blockTagsProvider.contentsGetter(), UnstableTools.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(ModItems.unstable_ingot);
    }
}
