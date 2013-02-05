package de.kumpelblase2.npclib.nms;

// original provided by Topcat, modified by kumpelblase2 , and modified again by MiniDigger ;D
import net.minecraft.server.v1_4_R1.DamageSource;
import net.minecraft.server.v1_4_R1.Entity;
import net.minecraft.server.v1_4_R1.EntityHuman;
import net.minecraft.server.v1_4_R1.EntityPlayer;
import net.minecraft.server.v1_4_R1.EnumGamemode;
import net.minecraft.server.v1_4_R1.PlayerInteractManager;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_4_R1.entity.CraftEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent;
import de.kumpelblase2.dragonslair.DragonsLairMain;
import de.kumpelblase2.npclib.NPCManager;

public class NPCEntity extends EntityPlayer
{
	private int lastTargetId;
	private long lastBounceTick;
	private int lastBounceId;

	public NPCEntity(NPCManager npcManager, BWorld world, String s, PlayerInteractManager itemInWorldManager) {
		super(npcManager.getServer().getMCServer(), world.getWorldServer(), s, itemInWorldManager);

		itemInWorldManager.b(EnumGamemode.SURVIVAL);

		playerConnection = new NPCNetHandler(npcManager, this);
		lastTargetId = -1;
		lastBounceId = -1;
		lastBounceTick = 0;

		fauxSleeping = true;
	}

	public void setBukkitEntity(CraftEntity entity) 
	{
		bukkitEntity = entity;
	}

	@Override
	public boolean a(final EntityHuman entity)
	{
		final EntityTargetEvent event = new NpcEntityTargetEvent(this.getBukkitEntity(), entity.getBukkitEntity(), NpcEntityTargetEvent.NpcTargetReason.NPC_RIGHTCLICKED);
		Bukkit.getPluginManager().callEvent(event);
		return super.a(entity);
	}
	
	@Override
	public void c_(final EntityHuman entity)
	{
		if((this.lastBounceId != entity.id || System.currentTimeMillis() - this.lastBounceTick > 1000) && entity.getBukkitEntity().getLocation().distanceSquared(this.getBukkitEntity().getLocation()) <= 1)
		{
			final EntityTargetEvent event = new NpcEntityTargetEvent(this.getBukkitEntity(), entity.getBukkitEntity(), NpcEntityTargetEvent.NpcTargetReason.NPC_BOUNCED);
			Bukkit.getPluginManager().callEvent(event);
			this.lastBounceTick = System.currentTimeMillis();
			this.lastBounceId = entity.id;
		}
		if(this.lastTargetId == -1 || this.lastTargetId != entity.id)
		{
			final EntityTargetEvent event = new NpcEntityTargetEvent(this.getBukkitEntity(), entity.getBukkitEntity(), NpcEntityTargetEvent.NpcTargetReason.CLOSEST_PLAYER);
			Bukkit.getPluginManager().callEvent(event);
			this.lastTargetId = entity.id;
		}
		super.c_(entity);
	}

	@Override
	public void c(final Entity entity)
	{
		if(this.lastBounceId != entity.id || System.currentTimeMillis() - this.lastBounceTick > 1000)
		{
			final EntityTargetEvent event = new NpcEntityTargetEvent(this.getBukkitEntity(), entity.getBukkitEntity(), NpcEntityTargetEvent.NpcTargetReason.NPC_BOUNCED);
			Bukkit.getPluginManager().callEvent(event);
			this.lastBounceTick = System.currentTimeMillis();
		}
		this.lastBounceId = entity.id;
		super.c(entity);
	}

	@Override
	protected int c(final DamageSource damage, int i)
	{
		if(damage.getEntity() instanceof EntityPlayer)
		{
			final EntityDamageByEntityEvent event = new NpcDamageEvent(this.getBukkitEntity(), damage.getEntity().getBukkitEntity(), DamageCause.ENTITY_ATTACK, i);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled())
				return 0;
			i = event.getDamage();
		}
		if(DragonsLairMain.getSettings().getNPCByName(this.name).isInvincible())
			i = 0;
		return super.c(damage, i);
	}

	@Override
	public void move(final double arg0, final double arg1, final double arg2)
	{
		this.setPosition(arg0, arg1, arg2);
	}
}