import os
os.environ["KIVY_HOME"] = "/data/data/ru.iiec.pydroid3/files/kivy_home"

from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.clock import Clock
from kivmob import KivMob, TestIds

from jnius import autoclass

class MyWebApp(App):
    def build(self):
        # Initialize AdMob
        self.ads = KivMob(TestIds.APP)  # Replace with your real AdMob App ID in final build
        self.ads.new_banner(TestIds.BANNER, top_pos=True)
        self.ads.request_banner()
        self.ads.show_banner()

        self.ads.new_interstitial(TestIds.INTERSTITIAL)
        self.ads.request_interstitial()

        self.ads.new_rewarded_ad(TestIds.REWARDED)
        self.ads.request_rewarded_ad()

        layout = BoxLayout(orientation='vertical')

        # Show interstitial ad after 10 seconds (demo)
        Clock.schedule_once(lambda dt: self.show_interstitial(), 10)

        # Set up WebView (for Android build only)
        if platform := __import__("kivy.utils").platform == "android":
            WebView = autoclass('org.renpy.android.WebView')
            activity = autoclass('org.kivy.android.PythonActivity').mActivity
            webview = WebView(activity)
            webview.loadUrl("https://connectgold.sbs")
            layout.add_widget(webview.getView())

        return layout

    def show_interstitial(self):
        if self.ads.is_interstitial_loaded():
            self.ads.show_interstitial()

    def show_rewarded(self):
        if self.ads.is_rewarded_ad_loaded():
            self.ads.show_rewarded_ad()

MyWebApp().run()