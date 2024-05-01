package uz.sardorbroo.secretarybot.config;

import com.github.alexdlaird.ngrok.NgrokClient;
import com.github.alexdlaird.ngrok.protocol.CreateTunnel;
import com.github.alexdlaird.ngrok.protocol.Tunnel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class NgrokConfig {

    @Value("${ngrok.static-domain}")
    private String domain;

    @Value("${server.port}")
    private Integer port;

    @Bean
    public Tunnel initializeNgrokTunnel() {
        log.info("Configuring Ngrok Tunnel...");

        NgrokClient client = new NgrokClient.Builder()
                .build();

        CreateTunnel tunnel = new CreateTunnel.Builder()
                .withHostname(domain)
                .withAddr(port)
                .build();

        Tunnel createdTunnel = client.connect(tunnel);

        log.info("Ngrok tunnel is configured successfully. Public URL: {}", createdTunnel.getPublicUrl());
        return createdTunnel;
    }

}
