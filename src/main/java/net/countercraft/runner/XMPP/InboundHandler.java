package net.countercraft.runner.XMPP;

//Java Imports
import java.util.logging.Level;
import java.util.logging.Logger;

//Local Imports
import net.countercraft.runner.Controller;

//Bukkit Imports
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

//Jivesoftware Imports
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class InboundHandler implements MessageListener {

	public void processMessage(Chat chat, Message msg) {
		String messageBody = msg.getBody();

		// If it is a command
		if (messageBody.startsWith("!")) {
			// Check if the sender is an admin
			if (Controller.getSettings().isAdmin(chat.getParticipant())) {
				String commandAll = messageBody.substring(1);
				String[] commandSplit = commandAll.split(" ");
				String command = commandSplit[0];
				// Generate args
				String argsWorking = "";

				for (int i = 1; i < commandSplit.length; i++) {
					argsWorking = argsWorking + commandSplit[i];
				}
				String[] args = argsWorking.split(" ");
				// Check if it is a Runner remote command
				// if weather
				if (command.equalsIgnoreCase("weather")) {

					if (args.length < 1) {
						try {
							chat.sendMessage("Sorry but your synatx was incorrect. It should be "
									+ Controller.getSettings().WEATHER_SYNTAX_EXAMPLE);
						} catch (XMPPException ex) {
							Logger.getLogger(InboundHandler.class.getName())
									.log(Level.SEVERE, null, ex);
						}
					} else {
						if (args[0].equalsIgnoreCase("storm")) {

							if (args.length == 1) {
								// No world given
								Controller.getPluginServer().getWorlds().get(0)
										.setStorm(true);
								try {
									chat.sendMessage("Weather was set to storm");
								} catch (XMPPException ex) {
									Logger.getLogger(
											InboundHandler.class.getName())
											.log(Level.SEVERE, null, ex);
								}
							} else {
								World fetchedWorld = Controller
										.getPluginServer().getWorld(args[1]);

								if (fetchedWorld == null) {
									try {
										chat.sendMessage("The world " + args[1]
												+ "was not found");
									} catch (XMPPException ex) {
										Logger.getLogger(
												InboundHandler.class.getName())
												.log(Level.SEVERE, null, ex);
									}
								} else {

									fetchedWorld.setStorm(true);
									try {
										chat.sendMessage("Weather was set to storm in world "
												+ fetchedWorld.getName());
									} catch (XMPPException ex) {
										Logger.getLogger(
												InboundHandler.class.getName())
												.log(Level.SEVERE, null, ex);
									}
								}
							}

						} else if (args[0].equalsIgnoreCase("sun")) {

							if (args.length == 1) {
								// No world given
								Controller.getPluginServer().getWorlds().get(0)
										.setStorm(false);
								try {
									chat.sendMessage("Weather was set to sun");
								} catch (XMPPException ex) {
									Logger.getLogger(
											InboundHandler.class.getName())
											.log(Level.SEVERE, null, ex);
								}
							} else {
								World fetchedWorld = Controller
										.getPluginServer().getWorld(args[1]);

								if (fetchedWorld == null) {
									try {
										chat.sendMessage("The world " + args[1]
												+ "was not found");
									} catch (XMPPException ex) {
										Logger.getLogger(
												InboundHandler.class.getName())
												.log(Level.SEVERE, null, ex);
									}
								} else {

									fetchedWorld.setStorm(false);
									try {
										chat.sendMessage("Weather was set to sun in world "
												+ fetchedWorld.getName());
									} catch (XMPPException ex) {
										Logger.getLogger(
												InboundHandler.class.getName())
												.log(Level.SEVERE, null, ex);
									}
								}
							}

						} else {
							try {
								chat.sendMessage("Sorry but " + args[0]
										+ "is not a recognised weather state");
							} catch (XMPPException ex) {
								Logger.getLogger(InboundHandler.class.getName())
										.log(Level.SEVERE, null, ex);
							}
						}
					}

				} else if (command.equalsIgnoreCase("time")) {
					// if time
					if (args.length < 1) {
						try {
							chat.sendMessage("Sorry but your synatx was incorrect. It should be "
									+ Controller.getSettings().TIME_SYNTAX_EXAMPLE);
						} catch (XMPPException ex) {
							Logger.getLogger(InboundHandler.class.getName())
									.log(Level.SEVERE, null, ex);
						}

					} else {
						if (args.length == 1) {
							// No world given
							Controller.getPluginServer().getWorlds().get(0)
									.setTime(new Long(args[0]));
							try {
								chat.sendMessage("Set time to " + args[0]);
							} catch (XMPPException ex) {
								Logger.getLogger(InboundHandler.class.getName())
										.log(Level.SEVERE, null, ex);
							}
						} else {
							World fetchedWorld = Controller.getPluginServer()
									.getWorld(args[1]);

							if (fetchedWorld == null) {
								try {
									chat.sendMessage("The world " + args[1]
											+ "was not found");
								} catch (XMPPException ex) {
									Logger.getLogger(
											InboundHandler.class.getName())
											.log(Level.SEVERE, null, ex);
								}
							} else {
								fetchedWorld.setTime(new Long(args[0]));
								try {
									chat.sendMessage("Set time to " + args[0]
											+ " in " + fetchedWorld.getName());
								} catch (XMPPException ex) {
									Logger.getLogger(
											InboundHandler.class.getName())
											.log(Level.SEVERE, null, ex);
								}
							}
						}
					}

				} else if (command.equalsIgnoreCase("who")) {
					Player[] players = Bukkit.getOnlinePlayers();
					String who = "There are " + players.length + "/"
							+ Bukkit.getMaxPlayers() + " players online:\n";
					for (Player p : players) {
						who += p.getDisplayName() + " ";
					}
					try {
						chat.sendMessage(who);
					} catch (XMPPException ex) {
						Logger.getLogger(InboundHandler.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				} else {
					// else execute normally
					try {
						chat.sendMessage((Controller
								.sendCommandAsConsoleWithResult(commandAll)) ? "Command executed"
								: "Command not found");
					} catch (XMPPException ex) {
						Logger.getLogger(InboundHandler.class.getName()).log(
								Level.SEVERE, null, ex);
					}

				}
			} else {
				try {
					chat.sendMessage("[Error] You do not have permission to use that command.");
				} catch (XMPPException ex) {
					Logger.getLogger(InboundHandler.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}

		} else {
			// This is a message
			Controller.getPluginServer().broadcastMessage(
					"[Runner Internet Chat] "
							+ Controller.getXMPPManager().correctAdress(
									chat.getParticipant()) + " : "
							+ messageBody);
			Controller.getXMPPManager().sendAllExcept(
					"[Internet Chat] "
							+ Controller.getXMPPManager().correctAdress(
									chat.getParticipant()) + " : "
							+ messageBody, chat.getParticipant());

		}
	}

}
