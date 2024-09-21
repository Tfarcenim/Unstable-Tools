package tfar.unstabletools.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tfar.unstabletools.UnstableTools;
import tfar.unstabletools.item.UnstableIngotItem;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagsProvider extends DamageTypeTagsProvider {

    public ModDamageTypeTagsProvider(PackOutput p_270719_, CompletableFuture<HolderLookup.Provider> p_270256_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_270719_, p_270256_, UnstableTools.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(DamageTypeTags.BYPASSES_ARMOR).add(UnstableIngotItem.DIVIDE_BY_DIAMOND);
    }
}
