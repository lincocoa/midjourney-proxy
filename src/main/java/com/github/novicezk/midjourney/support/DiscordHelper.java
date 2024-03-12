package com.github.novicezk.midjourney.support;

import cn.hutool.core.text.CharSequenceUtil;
import com.github.novicezk.midjourney.ProxyProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class DiscordHelper {
    private final ProxyProperties properties;
    /**
     * 转发的代理服务器, 支持任何基于HTTP(s)的扩展协议, 比如WSS
     */
    public static final String AGENT_HOST = "agent.linus.asia";
    /**
     * DISCORD_SERVER_URL.
     */
    public static final String DISCORD_SERVER_URL = "https://discord.com";
    /**
     * DISCORD_CDN_URL.
     */
    public static final String DISCORD_CDN_URL = "https://cdn.discordapp.com";
    /**
     * DISCORD_WSS_URL.
     */
    public static final String DISCORD_WSS_URL = "wss://gateway.discord.gg";
    /**
     * DISCORD_UPLOAD_URL.
     */
    public static final String DISCORD_UPLOAD_URL = "https://discord-attachments-uploads-prd.storage.googleapis.com";

    public String getServer() {
        if (CharSequenceUtil.isBlank(this.properties.getNgDiscord().getServer())) {
            return DISCORD_SERVER_URL;
        }
        String serverUrl = this.properties.getNgDiscord().getServer();
        if (serverUrl.endsWith("/")) {
            serverUrl = serverUrl.substring(0, serverUrl.length() - 1);
        }
        return serverUrl;
    }

    public String getCdn() {
        if (CharSequenceUtil.isBlank(this.properties.getNgDiscord().getCdn())) {
            return DISCORD_CDN_URL;
        }
        String cdnUrl = this.properties.getNgDiscord().getCdn();
        if (cdnUrl.endsWith("/")) {
            cdnUrl = cdnUrl.substring(0, cdnUrl.length() - 1);
        }
        return cdnUrl;
    }

    /**
     * 返回转发代理的服务器HOST域名
     *
     * @return
     */
    public String getAgentHost() {
        if (CharSequenceUtil.isBlank(this.properties.getNgDiscord().getAgentHost())) {
            return AGENT_HOST;
        }

        return this.properties.getNgDiscord().getAgentHost();
    }

    /**
     * 返回URL中的HOST部分
     *
     * @return
     */
    public static String getHost(String url) {
        try {
            URL wssUrl = new URL(url);
            return wssUrl.getHost();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getWss() {
        if (CharSequenceUtil.isBlank(this.properties.getNgDiscord().getWss())) {
            return DISCORD_WSS_URL;
        }
        String wssUrl = this.properties.getNgDiscord().getWss();
        if (wssUrl.endsWith("/")) {
            wssUrl = wssUrl.substring(0, wssUrl.length() - 1);
        }
        return wssUrl;
    }

    public String getDiscordUploadUrl(String uploadUrl) {
        if (CharSequenceUtil.isBlank(this.properties.getNgDiscord().getUploadServer()) || CharSequenceUtil.isBlank(uploadUrl)) {
            return uploadUrl;
        }
        String uploadServer = this.properties.getNgDiscord().getUploadServer();
        if (uploadServer.endsWith("/")) {
            uploadServer = uploadServer.substring(0, uploadServer.length() - 1);
        }
        return uploadUrl.replaceFirst(DISCORD_UPLOAD_URL, uploadServer);
    }

    public String findTaskIdWithCdnUrl(String url) {
        if (!CharSequenceUtil.startWith(url, DISCORD_CDN_URL)) {
            return null;
        }
        int hashStartIndex = url.lastIndexOf("/");
        String taskId = CharSequenceUtil.subBefore(url.substring(hashStartIndex + 1), ".", true);
        if (CharSequenceUtil.length(taskId) == 16) {
            return taskId;
        }
        return null;
    }

    public String getMessageHash(String imageUrl) {
        if (CharSequenceUtil.isBlank(imageUrl)) {
            return null;
        }
        if (CharSequenceUtil.endWith(imageUrl, "_grid_0.webp")) {
            int hashStartIndex = imageUrl.lastIndexOf("/");
            if (hashStartIndex < 0) {
                return null;
            }
            return CharSequenceUtil.sub(imageUrl, hashStartIndex + 1, imageUrl.length() - "_grid_0.webp".length());
        }
        int hashStartIndex = imageUrl.lastIndexOf("_");
        if (hashStartIndex < 0) {
            return null;
        }
        return CharSequenceUtil.subBefore(imageUrl.substring(hashStartIndex + 1), ".", true);
    }

}
