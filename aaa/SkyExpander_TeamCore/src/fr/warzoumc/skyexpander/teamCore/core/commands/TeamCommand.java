package fr.warzoumc.skyexpander.teamCore.core.commands;

import fr.warzoumc.skyexpander.teamCore.core.team.file.TeamCreator;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.level.Donnation;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.players.Leaving;
import fr.warzoumc.skyexpander.teamCore.core.team.teamObj.core.teamEvent.players.Joining;
import fr.warzoumc.skyexpander.teamCore.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {

    private Main main;
    public TeamCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length == 2){
                if (args[0].equalsIgnoreCase("create")){
                    new TeamCreator(main, args[1], player).createTeam();
                } else if (args[0].equalsIgnoreCase("join")){
                    new Joining(main, player, args[1]);
                }
            } else if (args.length == 1){
                 if (args[0].equalsIgnoreCase("leave")) {
                    new Leaving(main, player);
                 }
            } else {
                new Donnation(main, player);
            }
        }
        return false;
    }
}
