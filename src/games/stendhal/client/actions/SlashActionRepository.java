/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.actions;

import static games.stendhal.common.constants.Actions.ALTERKILL;
import static games.stendhal.common.constants.Actions.INSPECTKILL;
import static games.stendhal.common.constants.Actions.REMOVEDETAIL;
import static games.stendhal.common.constants.General.COMBAT_KARMA;

import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Manages Slash Action Objects.
 */
public class SlashActionRepository {

	/** Set of client supported Actions. */
	private static HashMap<String, SlashAction> actions = new HashMap<String, SlashAction>();

	/**
	 * Registers the available Action.
	 */
	public static void register() {
		final SlashAction msg = new MessageAction();
		final SlashAction supporta = new SupportAnswerAction();
		final SlashAction who = new WhoAction();
		final SlashAction help = new HelpAction();
		final GroupMessageAction groupMessage = new GroupMessageAction();

		actions.put("/", new RemessageAction());
		actions.put("add", new AddBuddyAction());
		actions.put("adminlevel", new AdminLevelAction());
		actions.put("adminnote", new AdminNoteAction());
		actions.put("alter", new AlterAction());
		actions.put(ALTERKILL, new AlterKillAction());
		actions.put("answer", new AnswerAction());
		actions.put("atlas", new AtlasBrowserLaunchCommand());

//		actions.put("ban", new BanAction());   

		actions.put("clear", new ClearChatLogAction());
		actions.put("clickmode", new ClickModeAction());
		actions.put("clientinfo", new ClientInfoAction());
		actions.put("commands", help);
		actions.put("config", new ConfigAction());

		actions.put("drop", new DropAction());

		actions.put("cast", new CastSpellAction());

		actions.put("gmhelp", new GMHelpAction());
		actions.put("group", new GroupManagementAction(groupMessage));
		actions.put("groupmessage", groupMessage);

		actions.put("help", help);

		actions.put("ignore", new IgnoreAction());

		actions.put(INSPECTKILL, new InspectKillAction());

		actions.put("msg", msg);
		actions.put("mute", new MuteAction());

		actions.put("names", who);

		actions.put("p", groupMessage);
		actions.put("profile", new ProfileAction());
		actions.put("travellog", new TravelLogAction());

		actions.put("quit", new QuitAction());

		actions.put("remove", new RemoveBuddyAction());

		actions.put("sentence", new SentenceAction());
		actions.put("status", new SentenceAction()); // Alias for /sentence
		actions.put("settings", new SettingsAction());

		actions.put("sound", new SoundAction());
		actions.put("volume", new VolumeAction());
		actions.put("vol", new VolumeAction());

		actions.put("storemessage", new StoreMessageAction());
		actions.put("postmessage", new StoreMessageAction());

		actions.put("summonat", new SummonAtAction());
		actions.put("summon", new SummonAction());
		actions.put("supportanswer", supporta);
		actions.put("supporta", supporta);
		actions.put("support", new SupportAction());

		actions.put("takescreenshot", new ScreenshotAction());
		actions.put("teleportto", new TeleportToAction());
		actions.put("tell", msg);

		actions.put("where", new WhereAction());
		actions.put("who", who);
		actions.putAll(BareBonesBrowserLaunchCommandsFactory.createBrowserCommands());
//		actions.put("wrap", new WrapAction());

		/* Movement */
		actions.put("walk", new AutoWalkAction());
		actions.put("stopwalk", new AutoWalkStopAction());
		actions.put("movecont", new MoveContinuousAction());

		// PvP challenge actions
		actions.put("accept", new AcceptChallengeAction());

		actions.put(COMBAT_KARMA, new SetCombatKarmaAction());

		// allows players to remove the detail layer manually
		actions.put(REMOVEDETAIL, new RemoveDetailAction());
		
		
		try {
			final ActionsXMLLoader loader = new ActionsXMLLoader();
			
			final Map<String, BaseAction> baseActions = loader.load(new URI("/data/conf/slashActions.xml"));
			
			for (Map.Entry<String, BaseAction> entry : baseActions.entrySet()) {
				 String name = entry.getKey();
				 BaseAction action = entry.getValue();
				 actions.put(name, action);
				 for (String alias : action.getAliases()) {
					 actions.put(alias, action);
				 }
			}
		} catch (final Exception e) {
			// LOGGER.error("SlashActions.xml could not be loaded", e);
		}
	}

	/**
	 * Gets the Action object for the specified Action name.
	 *
	 * @param name
	 *            name of Action
	 * @return Action object
	 */
	public static SlashAction get(String name) {
		String temp = name.toLowerCase(Locale.ENGLISH);
		return actions.get(temp);
	}

	/**
	 * Get all known command names.
	 *
	 * @return set of commands
	 */
	public static Set<String> getCommandNames() {
		return actions.keySet();
	}
}
