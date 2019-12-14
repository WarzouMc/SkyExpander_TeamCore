package fr.warzoumc.skyexpander.teamCore.core.commands;

import fr.warzoumc.skyexpander.teamCore.core.team.file.TeamCreator;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.Team;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.level.Donnation;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.level.PassLevel;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.chat.MessageType;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.chat.TeamChat;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.players.Joining;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.players.Leaving;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.team.Disband;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class TeamCommand implements CommandExecutor {

    private Main main;
    public TeamCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length == 1){
                 if (args[0].equalsIgnoreCase("leave")) {
                    new Leaving(main, player);
                 } else if (args[0].equalsIgnoreCase("donate")){
                     new Donnation(main, player);
                 } else if (args[0].equalsIgnoreCase("disband")){
                     if (Team.fromPlayer(main, player) != null){
                         TextComponent textComponent = new TextComponent();
                         player.sendMessage(main.getSuffix()[1] + "Etes vous sur de vouloir détruire cette team !\n" +
                                 "§6#§4Toute les donnés enregistrés seront effacé !");
                         textComponent.setText("§l§2[§aOUI§2]");
                         textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                                 "/team disband " + UUID.nameUUIDFromBytes(Team.fromPlayer(main, player)
                                         .getTeamName().getBytes())));
                         player.spigot().sendMessage(textComponent);
                     } else {
                         player.sendMessage(main.getSuffix()[1] + "Tu es dans aucune team !");
                     }
                 } else if (args[0].equalsIgnoreCase("progression")){
                     if (Team.fromPlayer(main, player) != null){
                         player.sendMessage(Team.fromPlayer(main, player).getProgression());
                     }
                 } else if (args[0].equalsIgnoreCase("list")){
                     if (Team.fromPlayer(main, player) != null){
                         player.sendMessage(Team.fromPlayer(main, player).getPlayersList());
                     }
                 } else if (args[0].equalsIgnoreCase("pass")){
                     if (Team.fromPlayer(main, player) != null){
                         new PassLevel(main, Team.fromPlayer(main, player));
                     }
                 }
            } else if (args.length > 1){
                if (args.length == 2){
                    if (args[0].equalsIgnoreCase("create")){
                        new TeamCreator(main, args[1], player).createTeam();
                    } else if (args[0].equalsIgnoreCase("join")){
                        new Joining(main, player, args[1]);
                    } else if (args[0].equalsIgnoreCase("disband")){
                        if (args[1].equalsIgnoreCase(UUID.nameUUIDFromBytes(Team.fromPlayer(main, player).getTeamName()
                                .getBytes()).toString())){
                            new Disband(main, Team.fromPlayer(main, player));
                        } else {
                        }
                    } else if (args[0].equalsIgnoreCase("chat")){
                        if (Team.fromPlayer(main, player) != null){
                            String message = MessageType.PLAYER_MESSAGE.setPlayer(player).setMessage(args[1])
                                    .setTeam(Team.fromPlayer(main, player)).build(main);
                            TeamChat.send(Team.fromPlayer(main, player), message);
                        }
                    }
                } else {
                    if (args[0].equalsIgnoreCase("chat")){
                        if (Team.fromPlayer(main, player) != null){
                            String[] playerMessageArgs = args;
                            playerMessageArgs[0] = "";
                            final String[] playerMessage = {""};
                            Arrays.asList(playerMessageArgs).forEach(s -> playerMessage[0] = playerMessage[0] + " " + s);
                            String message = MessageType.PLAYER_MESSAGE.setPlayer(player).setMessage(playerMessage[0].substring(2))
                                    .setTeam(Team.fromPlayer(main, player)).build(main);
                            TeamChat.send(Team.fromPlayer(main, player), message);
                        }
                    }
                }
            }
        }
        return false;
    }
}
