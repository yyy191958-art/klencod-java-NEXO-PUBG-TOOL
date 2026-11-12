
package com.nexo.pubgtool;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.*;
import android.webkit.*;
import android.widget.*;

public class MainActivity extends Activity {

    final int BG = Color.rgb(9, 13, 16);
    final int CARD = Color.rgb(23, 30, 35);
    final int GREEN = Color.rgb(0, 255, 125);
    final int WHITE = Color.WHITE;

    LinearLayout root;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        setContentView(root);
        showHome();
    }

    GradientDrawable shape(int color, int stroke) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(25);
        d.setStroke(2, stroke);
        return d;
    }

    TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        t.setPadding(12, 12, 12, 12);
        t.setIncludeFontPadding(true);
        return t;
    }

    void header(String title) {
        TextView h = text(title, 25, GREEN);
        h.setPadding(10, 24, 10, 24);
        root.addView(h);
    }

    void addButton(String arabic, String english, Runnable action) {

        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setGravity(Gravity.CENTER);
        item.setBackground(shape(CARD, GREEN));
        item.setPadding(10, 12, 10, 12);

        TextView ar = text(arabic, 19, WHITE);
        TextView en = text(english, 13, GREEN);

        item.addView(ar);
        item.addView(en);

        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(
                -1, -2
            );

        p.setMargins(0, 8, 0, 8);
        item.setLayoutParams(p);

        item.setOnClickListener(v -> action.run());

        root.addView(item);
    }

    void pageStart(String title) {

        root.removeAllViews();

        TextView back = text("‹  رجوع | BACK", 17, GREEN);
        back.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        back.setOnClickListener(v -> showHome());

        root.addView(back);
        header(title);
    }

    void showHome() {

        root.removeAllViews();

        ScrollView scroll = new ScrollView(this);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(20, 25, 20, 25);
        content.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        TextView logo = text("𓆩 𝑵𝑬𝑿𝑶 𓆪", 39, GREEN);
        content.addView(logo);

        content.addView(text(
            "PUBG BOOSTER PRO",
            20, WHITE
        ));

        content.addView(text(
            "مرحباً بك في عالم NEXO",
            19, WHITE
        ));

        content.addView(text(
            "YOUR PUBG TOOL CENTER",
            13, GREEN
        ));

        addTo(content, "قسم الألعاب", "GAMES",
            () -> showGames());

        addTo(content, "أدوات الجهاز", "DEVICE TOOLS",
            () -> showTools());

        addTo(content, "أخبار ببجي", "PUBG NEWS",
            () -> showNews());

        addTo(content, "روابطنا الرسمية", "OUR LINKS",
            () -> showLinks());

        TextView footer = text(
            "DEVELOPED BY NEXO",
            14, GREEN
        );
        content.addView(footer);

        scroll.addView(content);
        root.addView(scroll);
    }

    void addTo(LinearLayout parent,
               String ar, String en, Runnable action) {

        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setGravity(Gravity.CENTER);
        item.setBackground(shape(CARD, GREEN));
        item.setPadding(10, 12, 10, 12);

        item.addView(text(ar, 19, WHITE));
        item.addView(text(en, 13, GREEN));

        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(-1, -2);

        p.setMargins(0, 9, 0, 9);
        parent.addView(item, p);

        item.setOnClickListener(v -> action.run());
    }

    void showGames() {

        pageStart("الألعاب | GAMES");

        addButton("النسخة العالمية", "PUBG GLOBAL",
            () -> openGame("com.tencent.ig"));

        addButton("النسخة الكورية", "PUBG KOREA",
            () -> openGame("com.pubg.krmobile"));

        addButton("النسخة الفيتنامية", "PUBG VIETNAM",
            () -> openGame("com.vng.pubgmobile"));

        addButton("النسخة التايوانية", "PUBG TAIWAN",
            () -> openGame("com.rekoo.pubgm"));
    }

    void showTools() {

        pageStart("أدوات الجهاز | DEVICE TOOLS");

        addButton("فحص مساحة الجهاز", "STORAGE CHECK",
            () -> checkStorage());

        addButton("حرارة البطارية", "BATTERY TEMPERATURE",
            () -> checkTemperature());

        addButton("إعدادات التخزين", "STORAGE SETTINGS",
            () -> openSettings());
    }

    void showLinks() {

        pageStart("روابطنا | OUR LINKS");

        addButton("قناة التليجرام", "TELEGRAM CHANNEL",
            () -> openLink("https://t.me/az_4oi"));

        addButton("الموقع الرسمي", "PUBG MOBILE WEBSITE",
            () -> openLink("https://www.pubgmobile.com/"));
    }

    void showNews() {

        pageStart("أخبار ببجي | PUBG NEWS");

        TextView note = text(
            "آخر الأخبار والتحديثات الرسمية\nLATEST OFFICIAL UPDATES",
            16, WHITE
        );

        root.addView(note);

        web = new WebView(this);

        web.setBackgroundColor(BG);

        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        web.setWebViewClient(new WebViewClient());

        root.addView(web, new LinearLayout.LayoutParams(
            -1, 0, 1
        ));

        web.loadUrl(
            "https://www.pubgmobile.com/en-US/news.shtml"
        );
    }

    void openGame(String packageName) {

        try {
            Intent i = getPackageManager()
                .getLaunchIntentForPackage(packageName);

            if (i != null) {
                startActivity(i);
            } else {
                Toast.makeText(this,
                    "اللعبة غير مثبتة | GAME NOT INSTALLED",
                    Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this,
                "تعذر تشغيل اللعبة",
                Toast.LENGTH_SHORT).show();
        }
    }

    void checkStorage() {

        try {
            android.os.StatFs s = new android.os.StatFs(
                Environment.getDataDirectory().getPath()
            );

            long total = s.getTotalBytes() / 1048576;
            long free = s.getAvailableBytes() / 1048576;
            long used = total - free;

            new AlertDialog.Builder(this)
                .setTitle("مساحة الجهاز | STORAGE")
                .setMessage(
                    "Total: " + total + " MB\n\n" +
                    "Used: " + used + " MB\n\n" +
                    "Free: " + free + " MB"
                )
                .setPositiveButton("OK", null)
                .show();

        } catch (Exception e) {
            Toast.makeText(this,
                "حدث خطأ",
                Toast.LENGTH_SHORT).show();
        }
    }

    void checkTemperature() {

        try {
            Intent battery = registerReceiver(
                null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            );

            if (battery != null) {

                int temp = battery.getIntExtra(
                    android.os.BatteryManager.EXTRA_TEMPERATURE,
                    0
                );

                new AlertDialog.Builder(this)
                    .setTitle("Battery Temperature")
                    .setMessage(
                        "حرارة البطارية: " +
                        (temp / 10.0) + " °C"
                    )
                    .setPositiveButton("OK", null)
                    .show();
            }

        } catch (Exception e) {
            Toast.makeText(this,
                "تعذر قراءة الحرارة",
                Toast.LENGTH_SHORT).show();
        }
    }

    void openSettings() {

        try {
            startActivity(new Intent(
                android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS
            ));
        } catch (Exception e) {
            Toast.makeText(this,
                "افتح إعدادات التخزين يدوياً",
                Toast.LENGTH_SHORT).show();
        }
    }

    void openLink(String url) {
        try {
            startActivity(new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            ));
        } catch (Exception e) {
            Toast.makeText(this,
                "تعذر فتح الرابط",
                Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

        if (web != null && web.canGoBack()) {
            web.goBack();
        } else {
            showHome();
        }
    }
}
