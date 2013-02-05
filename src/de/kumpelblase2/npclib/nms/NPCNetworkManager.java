package de.kumpelblase2.npclib.nms;

// original provided by Topcat, modified by kumpelblase2 , and modified again by MiniDigger ;D
import java.io.IOException;
import java.lang.reflect.Field;

import net.minecraft.server.v1_4_R1.Connection;
import net.minecraft.server.v1_4_R1.NetworkManager;
import net.minecraft.server.v1_4_R1.Packet;

public class NPCNetworkManager extends NetworkManager {

	public NPCNetworkManager() throws IOException {
		super(new NullSocket(), "NPC Manager", new Connection() {
			@Override
			public boolean a() {
				return true;
			}
		}, null);
		try {
			Field f = NetworkManager.class.getDeclaredField("m");
			f.setAccessible(true);
			f.set(this, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void a(Connection nethandler) {
	}

	@Override
	public void queue(Packet packet) {
	}

	@Override
	public void a(String s, Object... aobject) {
	}

	@Override
	public void a() {
	}

}