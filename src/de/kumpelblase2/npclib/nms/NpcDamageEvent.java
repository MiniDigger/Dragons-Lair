package de.kumpelblase2.npclib.nms;

// original provided by Topcat, modified by kumpelblase2 , and modified again by MiniDigger ;D
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NpcDamageEvent extends EntityDamageByEntityEvent
{
	public NpcDamageEvent(final Entity damager, final Entity damagee, final DamageCause cause, final int damage)
	{
		super(damager, damagee, cause, damage);
	}
}
