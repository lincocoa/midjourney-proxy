## 1 修改记录
### 1.1 添加 agentHost 常量与属性
com.github.novicezk.midjourney.support.DiscordHelper.AGENT_HOST

com.github.novicezk.midjourney.ProxyProperties.NgDiscordConfig.agentHost
com.github.novicezk.midjourney.wss.user.UserWebSocketStarter.agentHost

### 1.2 为 WebSocket 添加转发代理处理逻辑
com.github.novicezk.midjourney.wss.user.UserWebSocketStarter.start

### 1.3 为 RestTemplate 添加转发代理处理逻辑
com.github.novicezk.midjourney.service.NotifyServiceImpl.postJson
com.github.novicezk.midjourney.service.DiscordServiceImpl.postJson(java.lang.String, java.lang.String)
com.github.novicezk.midjourney.service.translate.BaiduTranslateServiceImpl.translateToEnglish

### 1.4 添加 config.md 中的参数说明
mj.ng-discord.agent-host

## 重要代码
### 网址定义
com.github.novicezk.midjourney.support.DiscordHelper.java

