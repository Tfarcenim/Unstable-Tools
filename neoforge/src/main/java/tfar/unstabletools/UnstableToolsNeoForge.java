package tfar.unstabletools;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.commons.lang3.tuple.Pair;
import tfar.unstabletools.datagen.Datagen;
import tfar.unstabletools.init.*;
import tfar.unstabletools.item.UnstableIngotItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod(value = UnstableTools.MOD_ID)
public class UnstableToolsNeoForge {

    public static Map<Registry<?>, List<Pair<ResourceLocation, Supplier<?>>>> registerLater = new HashMap<>();

    public UnstableToolsNeoForge(IEventBus bus, Dist dist, ModContainer container) {
        NeoForge.EVENT_BUS.addListener(this::onDrops);
        NeoForge.EVENT_BUS.addListener(this::reload);
        NeoForge.EVENT_BUS.addListener(this::onItemDrop);
        NeoForge.EVENT_BUS.addListener(this::playertick);
        NeoForge.EVENT_BUS.addListener(this::onContainerClose);
        NeoForge.EVENT_BUS.addListener(this::onSacrifice);
        if (dist.isClient()) {
            ModClientNeoForge.init(bus);
        }
        bus.addListener(this::registerBlock);
        bus.addListener(Datagen::gather);
        bus.addListener(this::onInitialize);
        container.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);

        ((MappedRegistry<ArmorMaterial>)BuiltInRegistries.ARMOR_MATERIAL).unfreeze();
        UnstableTools.init();
    }

    public void onSacrifice(LivingDeathEvent e) {
        UnstableTools.livingDeath(e.getEntity(),e.getSource());
    }

    void registerBlock(RegisterEvent event) {
        for (Map.Entry<Registry<?>,List<Pair<ResourceLocation, Supplier<?>>>> entry : registerLater.entrySet()) {
            Registry<?> registry = entry.getKey();
            List<Pair<ResourceLocation, Supplier<?>>> toRegister = entry.getValue();
            for (Pair<ResourceLocation,Supplier<?>> pair : toRegister) {
                event.register((ResourceKey<? extends Registry<Object>>)registry.key(),pair.getLeft(),(Supplier<Object>)pair.getValue());
            }
        }
    }

    public void onInitialize(FMLCommonSetupEvent e) {
        registerLater.clear();
    }

    private void reload(AddReloadListenerEvent event) {
        event.addListener(UnstableTools.manager);
    }

    private void onDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof WitherBoss && event.getSource().getEntity() instanceof Player) {

            ItemStack itemStackToDrop = new ItemStack(ModItems.INACTIVE_DIVISION_SIGN);
            event.getDrops().add(new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), itemStackToDrop));
        }
    }

    void onItemDrop(ItemTossEvent e) {
        Player p = e.getPlayer();
        ItemEntity entityItem = e.getEntity();
        ItemStack stack = entityItem.getItem();
        if (UnstableIngotItem.checkExplosion(stack)) {
            UnstableIngotItem.boom(p);
            e.setCanceled(true);
        }
    }

    void playertick(PlayerTickEvent.Post e) {
        if (e.getEntity() instanceof ServerPlayer serverPlayer) {
            UnstableTools.playerTick(serverPlayer);
        }
    }

    void onContainerClose(PlayerContainerEvent.Close e) {
        UnstableTools.containerClosed(e.getContainer(),e.getEntity());
    }
}
