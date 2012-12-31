package net.countercraft.runner.senders;

import java.util.Set;

import net.countercraft.runner.Controller;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class RunnerSender extends ConsoleCommandSender {
	
	private String participant;
	private String nickname;
	private boolean use_nick;
	
	public RunnerSender(String participant, String nickname, boolean use_nick) {
		this.participant = participant;
		this.nickname = nickname;
		this.use_nick = use_nick;
	}
	@Override
	public void sendMessage(String arg0) {
		Controller.getXMPPManager().sendToOne(ChatColor.stripColor(arg0), participant);
	}

	@Override
	public void sendMessage(String[] arg0) {
		for (String msg : arg0) {
			Controller.getXMPPManager().sendToOne(ChatColor.stripColor(msg), participant);
		}
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1,
			boolean arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1,
			boolean arg2, int arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermission(String arg0) {
		if (this.use_nick && Controller.isVaultInstalled()) {
			return Controller.getPermission().playerHas((World)null, this.nickname.toString(), arg0);
		}
		return true;
	}

	@Override
	public boolean hasPermission(Permission arg0) {
		if (this.use_nick && Controller.isVaultInstalled()) {
			return Controller.getPermission().playerHas((World)null, this.nickname.toString(), arg0.getName());
		}
		return true;
	}

	@Override
	public boolean isPermissionSet(String arg0) {
		return true;
	}

	@Override
	public boolean isPermissionSet(Permission arg0) {
		return true;
	}

	@Override
	public void recalculatePermissions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAttachment(PermissionAttachment arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOp() {
		return true;
	}

	@Override
	public void setOp(boolean arg0) {
		
	}

	@Override
	public String getName() {
		return this.participant;
	}

	@Override
	public Server getServer() {
		return Bukkit.getServer();
	}

}
