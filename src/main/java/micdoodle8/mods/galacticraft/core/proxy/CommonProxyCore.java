package micdoodle8.mods.galacticraft.core.proxy;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.item.EnumExtendedInventorySlot;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.entities.player.IPlayerServer;
import micdoodle8.mods.galacticraft.core.entities.player.PlayerServer;
import micdoodle8.mods.galacticraft.core.fluid.FluidNetwork;
import micdoodle8.mods.galacticraft.core.tick.TickHandlerServer;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.wrappers.PartialCanister;
import micdoodle8.mods.galacticraft.core.wrappers.PlayerGearData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxyCore
{
    public IPlayerServer player = new PlayerServer();

    public void preInit(FMLPreInitializationEvent event)
    {
    }

    public void registerVariants()
    {

    }

    public void init(FMLInitializationEvent event)
    {
    }

    public void postInit(FMLPostInitializationEvent event)
    {
    }

    public void registerCanister(PartialCanister container)
    {
    }

    public void registerFluidTexture(Fluid fluid, ResourceLocation submergedTexture)
    {
    }

    public World getClientWorld()
    {
        return null;
    }

    public void spawnParticle(String particleID, Vector3 position, Vector3 motion, Object[] otherInfo)
    {
    }

    public World getWorldForID(int dimensionID)
    {
        MinecraftServer theServer = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (theServer == null)
        {
            return null;
        }
        return theServer.worldServerForDimension(dimensionID);
    }

    public EntityPlayer getPlayerFromNetHandler(INetHandler handler)
    {
        if (handler instanceof NetHandlerPlayServer)
        {
            return ((NetHandlerPlayServer) handler).playerEntity;
        }
        else
        {
            return null;
        }
    }

    public void postRegisterItem(Item item)
    {
    }

    public void unregisterNetwork(FluidNetwork fluidNetwork)
    {
        if (GCCoreUtil.getEffectiveSide().isServer())
        {
            TickHandlerServer.removeFluidNetwork(fluidNetwork);
        }
    }

    public void registerNetwork(FluidNetwork fluidNetwork)
    {
        if (GCCoreUtil.getEffectiveSide().isServer())
        {
            TickHandlerServer.addFluidNetwork(fluidNetwork);
        }
    }

    public boolean isPaused()
    {
        return false;
    }
    
    public PlayerGearData getGearData(EntityPlayer player)
    {
        GCPlayerStats stats = GCPlayerStats.get(player);
        
        int mask = GalacticraftRegistry.findMatchingGearID(stats.getMaskInSlot(), EnumExtendedInventorySlot.MASK);
        int gear = GalacticraftRegistry.findMatchingGearID(stats.getGearInSlot(), EnumExtendedInventorySlot.GEAR);
        int leftTank = GalacticraftRegistry.findMatchingGearID(stats.getTankInSlot1(), EnumExtendedInventorySlot.LEFT_TANK);
        int rightTank = GalacticraftRegistry.findMatchingGearID(stats.getTankInSlot2(), EnumExtendedInventorySlot.RIGHT_TANK);
        int frequencyModule = GalacticraftRegistry.findMatchingGearID(stats.getFrequencyModuleInSlot(), EnumExtendedInventorySlot.FREQUENCY_MODULE);
        int[] thermalPadding = new int[4];
        thermalPadding[0] = GalacticraftRegistry.findMatchingGearID(stats.getThermalHelmetInSlot(), EnumExtendedInventorySlot.THERMAL_HELMET);
        thermalPadding[1] = GalacticraftRegistry.findMatchingGearID(stats.getThermalChestplateInSlot(), EnumExtendedInventorySlot.THERMAL_CHESTPLATE);
        thermalPadding[2] = GalacticraftRegistry.findMatchingGearID(stats.getThermalLeggingsInSlot(), EnumExtendedInventorySlot.THERMAL_LEGGINGS);
        thermalPadding[3] = GalacticraftRegistry.findMatchingGearID(stats.getThermalBootsInSlot(), EnumExtendedInventorySlot.THERMAL_BOOTS);
        //TODO: Parachute
        return new PlayerGearData(player, mask, gear, leftTank, rightTank, frequencyModule, thermalPadding);
    }
}
