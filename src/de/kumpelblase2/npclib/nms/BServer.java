package de.kumpelblase2.npclib.nms;

// original provided by Topcat, modified by kumpelblase2 , and modified again by MiniDigger ;D
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.server.v1_4_R1.DedicatedPlayerList;
import net.minecraft.server.v1_4_R1.DedicatedServer;
import net.minecraft.server.v1_4_R1.MinecraftServer;
import net.minecraft.server.v1_4_R1.PropertyManager;
import net.minecraft.server.v1_4_R1.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_4_R1.CraftServer;
import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;

/**
 * Server hacks for Bukkit
 * 
 * @author Kekec852
 */
public class BServer
{
	private static BServer ins;
	private MinecraftServer mcServer;
	private CraftServer cServer;
	private final Server server;
	private final HashMap<String, BWorld> worlds = new HashMap<String, BWorld>();

	private BServer()
	{
		this.server = Bukkit.getServer();
		try
		{
			this.cServer = (CraftServer)this.server;
			this.mcServer = this.cServer.getServer();
		}
		catch(final Exception ex)
		{
			Logger.getLogger("Minecraft").log(Level.SEVERE, null, ex);
		}
	}

	public void disablePlugins()
	{
		this.cServer.disablePlugins();
	}

	public void dispatchCommand(final CommandSender sender, final String msg)
	{
		this.cServer.dispatchCommand(sender, msg);
	}

	public DedicatedPlayerList getHandle() 
	{
		return cServer.getHandle();
	}

	public ConsoleReader getReader()
	{
		return this.cServer.getReader();
	}

	public void loadPlugins()
	{
		this.cServer.loadPlugins();
	}

	public void stop()
	{
		this.mcServer.safeShutdown();
	}

	public void sendConsoleCommand(final String cmd)
	{
		if(this.mcServer.isRunning())
			((DedicatedServer)this.mcServer).issueCommand(cmd, this.mcServer);
	}

	public Logger getLogger()
	{
		return this.cServer.getLogger();
	}

	public List<WorldServer> getWorldServers()
	{
		return this.mcServer.worlds;
	}

	public int getSpawnProtationRadius()
	{
		return this.mcServer.server.getSpawnRadius();
	}

	public PropertyManager getPropertyManager()
	{
		return this.mcServer.getPropertyManager();
	}

	public Server getServer()
	{
		return this.server;
	}

	public BWorld getWorld(final String worldName)
	{
		if(this.worlds.containsKey(worldName))
			return this.worlds.get(worldName);
		final BWorld w = new BWorld(this, worldName);
		this.worlds.put(worldName, w);
		return w;
	}

	public static BServer getInstance()
	{
		if(ins == null)
			ins = new BServer();
		return ins;
	}

	public MinecraftServer getMCServer()
	{
		return this.mcServer;
	}
}