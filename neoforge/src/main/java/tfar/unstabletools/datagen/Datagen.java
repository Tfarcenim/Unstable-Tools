package tfar.unstabletools.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import tfar.unstabletools.datagen.assets.ModItemModelProvider;
import tfar.unstabletools.datagen.assets.ModLangProvider;
import tfar.unstabletools.datagen.assets.ModModelProvider;
import tfar.unstabletools.datagen.data.*;

import java.util.concurrent.CompletableFuture;

public class Datagen {

    public static void gather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        boolean server = event.includeServer();
        boolean client = event.includeClient();
        generator.addProvider(server,new BlockConversionProvider(output));
        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output,lookupProvider,helper);
        generator.addProvider(server,blockTagsProvider);
        generator.addProvider(server,new ModItemTagsProvider(output,lookupProvider,blockTagsProvider,helper));
        generator.addProvider(server,new ModRecipeProvider(output,lookupProvider));
        generator.addProvider(server,new ModDamageTypeTagsProvider(output,lookupProvider,helper));
        generator.addProvider(server, ModLootTableProvider.create(output,lookupProvider));

        generator.addProvider(client,new ModModelProvider(output));
        generator.addProvider(client,new ModLangProvider(output));
        generator.addProvider(client,new ModItemModelProvider(output,helper));
    }
}
