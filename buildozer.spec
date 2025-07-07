[app]
title = ConnectGold
package.name = connectgold
package.domain = org.connectgold.app
source.dir = .
source.include_exts = py,png,jpg,kv,atlas
version = 1.0
requirements = python3,kivy,kivmob,android,jnius
orientation = portrait
fullscreen = 1

[buildozer]
log_level = 2
warn_on_root = 1

[android]
android.permissions = INTERNET,ACCESS_NETWORK_STATE
android.api = 33
android.minapi = 21
android.ndk = 25b
android.sdk = 24
android.meta_data = com.google.android.gms.ads.APPLICATION_ID=ca-app-pub-1171216593802007~4173705766
android.gradle_dependencies = com.google.android.gms:play-services-ads:22.6.0
android.enable_androidx = 1
android.useandroidx = 1
