package fr.warzoumc.skyexpander.teamcore.core.commands;

import fr.warzoumc.skyexpander.teamcore.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TeamChatCommand implements CommandExecutor {
    private Main main;
    public TeamChatCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            final String[] commandDo = {"team chat"};
            Arrays.asList(args).forEach(s -> commandDo[0] = commandDo[0] + " " + s);
            player.performCommand(commandDo[0]);
        }
        return false;
    }
}
