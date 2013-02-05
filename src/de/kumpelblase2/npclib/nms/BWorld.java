package de.kumpelblase2.npclib.nms;

// original provided by Topcat, modified by kumpelblase2 , and modified again by MiniDigger ;D
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.server.v1_4_R1.AxisAlignedBB;
import net.minecraft.server.v1_4_R1.Entity;
import net.minecraft.server.v1_4_R1.EntityPlayer;
import net.minecraft.server.v1_4_R1.PlayerChunkMap;
import net.minecraft.server.v1_4_R1.WorldProvider;
import net.minecraft.server.v1_4_R1.WorldServer;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_4_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BWorld
{
	private BServer server;
	private final World world;
	private CraftWorld cWorld;
	private WorldServer wServer;
	private WorldProvider wProvider;

	public BWorld(final BServer server, final String worldName)
	{
		this.server = server;
		this.world = server.getServer().getWorld(worldName);
		try
		{
			this.cWorld = (CraftWorld)this.world;
			this.wServer = this.cWorld.getHandle();
			this.wProvider = this.wServer.worldProvider;
		}
		catch(final Exception ex)
		{
			Logger.getLogger("Minecraft").log(Level.SEVERE, null, ex);
		}
	}

	public BWorld(final World world)
	{
		this.world = world;
		try
		{
			this.cWorld = (CraftWorld)world;
			this.wServer = this.cWorld.getHandle();
			this.wProvider = this.wServer.worldProvider;
		}
		catch(final Exception ex)
		{
			Logger.getLogger("Minecraft").log(Level.SEVERE, null, ex);
		}
	}

	public PlayerChunkMap getChunkMap() 
	{
		return wServer.getPlayerChunkMap();
	}

	public CraftWorld getCraftWorld()
	{
		return this.cWorld;
	}

	public WorldServer getWorldServer()
	{
		return this.wServer;
	}

	public WorldProvider getWorldProvider()
	{
		return this.wProvider;
	}

	@SuppressWarnings("unchecked")
	public void removeEntity(final String name, final Player player, final JavaPlugin plugin)
	{
		this.server.getServer().getScheduler().callSyncMethod(plugin, new Callable<Object>()
		{
			@Override
			public Object call() throws Exception
			{
				final Location loc = player.getLocation();
				final CraftWorld craftWorld = (CraftWorld)player.getWorld();
				final CraftPlayer craftPlayer = (CraftPlayer)player;
				final double x = loc.getX() + 0.5;
				final double y = loc.getY() + 0.5;
				final double z = loc.getZ() + 0.5;
				final double radius = 10;
				List<Entity> entities = new ArrayList<Entity>();
				final AxisAlignedBB bb = AxisAlignedBB.a(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
				entities = craftWorld.getHandle().getEntities(craftPlayer.getHandle(), bb);
				for(final Entity o : entities)
					if(!(o instanceof EntityPlayer))
						o.getBukkitEntity().remove();
				return null;
			}
		});
	}
}