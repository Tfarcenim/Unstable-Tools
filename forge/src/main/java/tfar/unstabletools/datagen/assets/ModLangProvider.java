package tfar.unstabletools.datagen.assets;

import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;
import tfar.unstabletools.TranslationKeys;
import tfar.unstabletools.UnstableTools;

import java.util.function.Supplier;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, UnstableTools.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("item.unstabletools.unstable_ingot", "Unstable Ingot");
        add("block.unstabletools.unstable_block", "Unstable Block");

        add("item.unstabletools.unstable_paxel", "Unstable Paxel");
        add("item.unstabletools.unstable_fishing_rod", "Unstable Fishing Rod");
        add("item.unstabletools.unstable_bow", "Unstable Bow");

        add("item.unstabletools.unstable_pickaxe", "Unstable Pickaxe");
        add("item.unstabletools.unstable_axe", "Unstable Axe");
        add("item.unstabletools.unstable_sword", "Unstable Sword");
        add("item.unstabletools.unstable_shovel", "Unstable Shovel");
        add("item.unstabletools.unstable_hoe", "Unstable Hoe");
        add("item.unstabletools.unstable_shears", "Unstable Shears");

        add("item.unstabletools.division_sign", "Division Sign");
        add("item.unstabletools.stable_division_sign", "Stable Division Sign");

        add("item.unstabletools.unstable_helmet", "Unstable Helmet");
        add("item.unstabletools.unstable_chestplate", "Unstable Chestplate");
        add("item.unstabletools.unstable_leggings", "Unstable Leggings");
        add("item.unstabletools.unstable_boots", "Unstable Boots");
        add("block.unstabletools.ethereal_glass", "Ethereal Glass");

        add("itemGroup.unstabletools", "Unstable Tools");

        addTranslatableComponent(TranslationKeys.EARLY, "Too early");
         addTranslatableComponent(TranslationKeys.ON_TIME, "The time is right");
         addTranslatableComponent(TranslationKeys.LATE, "Too late");
         addTranslatableComponent(TranslationKeys.INCOMPLETE_REDSTONE, "Missing one or more redstone wires");
         addTranslatableComponent(TranslationKeys.NO_SKY, "Can't see sky");
         addTranslatableComponent(TranslationKeys.READY, "Perform the sacrifice");
         addTranslatableComponent(TranslationKeys.INACTIVE, "Inactive");
         addTranslatableComponent(TranslationKeys.ACTIVE, "Active");
         addTranslatableComponent(TranslationKeys.STABLE, "Stable");
         addTranslatableComponent(TranslationKeys.DROPS_FROM_WITHER,"Drops from Wither");
         addTranslatableComponent(TranslationKeys.UNSTABLE_INGOT_TOOLTIP, "The product of dividing iron by diamond, handle with care");

         addTranslatableComponent(TranslationKeys.usesLeft(0),"Uses Left: %s");
         addTranslatableComponent(TranslationKeys.timeLeft(0),"Time Left: %s");

        add("death.attack.divide_by_diamond", "%1$s tried to divide by diamond");
        add("gui.rei.unstabletools.category.block_conversions", "Block Conversions");
    }

    protected void addDefaultItem(Supplier<? extends Item> supplier) {
        addItem(supplier,getNameFromItem(supplier.get()));
    }

    protected void addDefaultBlock(Supplier<? extends Block> supplier) {
        addBlock(supplier,getNameFromBlock(supplier.get()));
    }

    protected void addDefaultEnchantment(Supplier<? extends Enchantment> supplier) {
        addEnchantment(supplier,getNameFromEnchantment(supplier.get()));
    }

    protected void addDefaultEntityType(Supplier<EntityType<?>> supplier) {
        addEntityType(supplier,getNameFromEntity(supplier.get()));
    }

    public static String getNameFromItem(Item item) {
        return StringUtils.capitaliseAllWords(item.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromBlock(Block block) {
        return StringUtils.capitaliseAllWords(block.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromEnchantment(Enchantment enchantment) {
        return StringUtils.capitaliseAllWords(enchantment.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    public static String getNameFromEntity(EntityType<?> entity) {
        return StringUtils.capitaliseAllWords(entity.getDescriptionId().split("\\.")[2].replace("_", " "));
    }

    protected void addTranslatableComponent(MutableComponent component, String text) {
        ComponentContents contents = component.getContents();
        if (contents instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(),text);
        } else {
            throw new UnsupportedOperationException(component +" is not translatable");
        }
    }

}
