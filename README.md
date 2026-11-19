# ST01 Offline Manual for Android

这是一个可离线运行本地 HTML 手册的 Android WebView 外壳，适合作为平板手册应用的起点。它不联网、不包含账号登录，也不上传浏览内容。

## 构建

需要 Android Studio、JDK 17 和 Android SDK 35。用 Android Studio 打开本仓库，等待 Gradle 同步后运行 app。也可以在 PowerShell 中执行：

    .\gradlew.bat assembleDebug

On macOS or Linux, run the wrapper through the shell:

    sh ./gradlew assembleDebug

首次构建需要网络下载 Gradle 和 Android Gradle Plugin。生成的 APK 位于 app/build/outputs/apk/debug/。不要提交本地签名文件、APK 或 local.properties。

## 放入自己的离线内容

本仓库只提供可复用的应用外壳和一张说明页，不包含原 ST01 网站的数据、规则文本、卡牌图片、游戏插图、模型照片、PDF、截图或原版图标。要打包内容，请将你拥有发布权的 HTML、脚本、样式和媒体放进 app/src/main/assets/，并将入口设为 index.html。

页面会通过本地 https://offline.manual.invalid/ 地址加载。导航只允许此本地资源；WebView 的文件与网络访问均关闭。新内容需要重新构建 APK 才能更新。

## 许可证

本仓库自行编写的代码采用 MIT License。Android SDK、Gradle 与 Android Gradle Plugin 由各自权利人按其许可证提供。任何自行添加的第三方内容都应遵守其原许可证。

## 项目关系与商标

此仓库整理自个人 ST01 平板离线测试项目中的 Android 外壳，抽离了未获授权公开的游戏资料与图像。ST01、游戏名称及相关标识属于各自权利人；本项目为非官方工具，与权利人无关联或背书。
