package service.dj45x;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiscordUniSystem {
    public static void main(String[] args) {
        SpringApplication.run(DiscordUniSystem.class, args);
    }
    @SneakyThrows
    public DiscordUniSystem(
            @Value("${discord.system.token}") String token,
            ObjectProvider<ListenerAdapter> listenerAdapters
    ){
        DefaultShardManagerBuilder shardManagerBuilder = DefaultShardManagerBuilder
                .createDefault(token)
                .setShardsTotal(2)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setEnabledIntents(
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.SCHEDULED_EVENTS,
                        GatewayIntent.GUILD_MODERATION
                );
        // Add all ListenAdapters
        for(var adapter : listenerAdapters) {
            shardManagerBuilder.addEventListeners(adapter);
        }

        // Login all shards
        ShardManager shardManager = shardManagerBuilder.build();

        // Set initial Activity Status
        shardManager.setActivity(Activity.watching("/help for more info"));
    }
}