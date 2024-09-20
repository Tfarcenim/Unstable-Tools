package tfar.unstabletools.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import tfar.unstabletools.item.ItemUnstableIngot;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public ModDamageTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider) {
        super(pOutput, pLookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(DamageTypeTags.BYPASSES_ARMOR).add(ItemUnstableIngot.DIVIDE_BY_DIAMOND);
    }

    //patch forge bug
    @Override
    protected TagBuilder getOrCreateRawBuilder(TagKey<DamageType> pTag) {
        return this.builders.computeIfAbsent(pTag.location(), (p_236442_) -> {
        ///    if (existingFileHelper != null) {
         //       existingFileHelper.trackGenerated(p_236442_, resourceType);
         //   }
            return TagBuilder.create();
        });
    }

}
