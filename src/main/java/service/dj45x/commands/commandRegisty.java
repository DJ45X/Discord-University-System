package service.dj45x.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class commandRegisty extends ListenerAdapter {
    @Value("${discord.system.guildid}")
    private String guildId;

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild guild = event.getGuild();
        if (!guild.getId().equals(guildId)) return;

        guild.updateCommands().queue(commands ->
                guild.updateCommands().addCommands(
                        Commands.slash("admin", "Admin Commands")
                                .addSubcommands(
                                        new SubcommandData("setup", "Setup all sticky system embeds")
                                ).setGuildOnly(true),
                        Commands.slash("student", "Commands used by students for interacting with UniBot")
                                .addSubcommands(
                                        // Add student commands here
                                ).setGuildOnly(true),
                        Commands.slash("faculty", "Commands used by faculty for managing students, courses, grades, etc..")
                                .addSubcommands(
                                        // add faculty commands here
                                ).setGuildOnly(true)
                ).queue());
    }
}
