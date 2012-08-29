package de.kumpelblase2.dragonslair.api;

import org.bukkit.entity.LivingEntity;

public class EventMonster
{
	private final LivingEntity mob;
	private final Event event;
	private final ActiveDungeon dungeon;
	
	public EventMonster(Event e, ActiveDungeon ad, LivingEntity entity)
	{
		this.event = e;
		this.dungeon = ad;
		this.mob = entity;
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(object instanceof LivingEntity)
			return this.mob.equals(object);
		else if(object instanceof EventMonster)
			return ((EventMonster)object).getMonster().equals(this.mob);
		return false;
	}
	
	public ActiveDungeon getDungeon()
	{
		return this.dungeon;
	}
	
	public Event getEvent()
	{
		return this.event;
	}
	
	public LivingEntity getMonster()
	{
		return this.mob;
	}
}
