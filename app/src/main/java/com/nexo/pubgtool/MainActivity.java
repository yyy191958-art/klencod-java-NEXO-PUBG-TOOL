package com.nexo.pubgtool;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import android.os.StatFs;

public class MainActivity extends Activity {

    LinearLayout main;
    int green = Color.rgb(0, 255, 125);
    int dark = Color.rgb(10, 14, 17);
    int card = Color.rgb(23, 29, 34);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(dark);
        getWindow().setNavigationBarColor(dark);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(dark);

        main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(20, 25, 20, 30);
        main.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        scroll.addView(main);
        setContentView(scroll);

        TextView title = new TextView(this);
        title.setText("𓆩 𝑵𝑬𝑿𝑶 𓆪");
        title.setTextColor(green);
        title.setTextSize(32);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        main.addView(title);

        TextView sub = new TextView(this);
        sub.setText("PUBG BOOSTER PRO");
        sub.setTextColor(Color.WHITE);
        sub.setTextSize(19);
        sub.setGravity(Gravity.CENTER);
        sub.setPadding(0, 5, 0, 20);
        main.addView(sub);

        addLine();
        addInfo("مرحباً بك في NEXO PUBG TOOL");
        addInfo("أدوات مساعدة للاعبين");

        addButton("🎮  تشغيل PUBG GLOBAL", () -> openGame("com.tencent.ig"));

        addButton("🌍  نسخ PUBG الأخرى", () -> showVersions());

        addButton("🧹  فحص مساحة الجهاز", () -> checkStorage());

        addButton("🌡️  فحص حرارة البطارية", () -> checkTemperature());

        addButton("⚙️  تنظيف الملفات المؤقتة", () -> {
            try {
                Intent i = new Intent(
                    android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS
                );
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(this,
                    "افتح إعدادات التخزين يدوياً",
                    Toast.LENGTH_LONG).show();
            }
        });

        addButton("📰  أخبار وتحديثات PUBG", () ->
            openLink("https://www.pubgmobile.com/"));

        addButton("✈️  قناة التليجرام", () ->
            openLink("https://t.me/az_4oi"));

        addButton("🎵  حساب TikTok", () ->
            openLink("https://www.tiktok.com/@wdd4k"));

        addLine();

        TextView footer = new TextView(this);
        footer.setText("DEVELOPED BY 𓆩 𝑵𝑬𝑿𝑶 𓆪");
        footer.setTextColor(green);
        footer.setTextSize(14);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(0, 20, 0, 10);
        main.addView(footer);
    }

    void addInfo(String text) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextColor(Color.WHITE);
        t.setTextSize(17);
        t.setGravity(Gravity.CENTER);
        t.setPadding(5, 12, 5, 12);
        main.addView(t);
    }

    void addLine() {
        View line = new View(this);
        line.setBackgroundColor(green);
        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(-1, 2);
        p.setMargins(5, 15, 5, 15);
        main.addView(line, p);
    }

    void addButton(String text, Runnable action) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextColor(Color.WHITE);
        b.setTextSize(16);
        b.setAllCaps(false);
        b.setTypeface(null, Typeface.BOLD);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(card);
        bg.setCornerRadius(24);
        bg.setStroke(2, green);
        b.setBackground(bg);

        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(-1, 65);
        p.setMargins(0, 9, 0, 9);

        main.addView(b, p);
        b.setOnClickListener(v -> action.run());
    }

    void openGame(String packageName) {
        try {
            Intent i = getPackageManager()
                .getLaunchIntentForPackage(packageName);

            if (i != null) {
                startActivity(i);
            } else {
                Toast.makeText(this,
                    "اللعبة غير مثبتة على جهازك",
                    Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this,
                "تعذر تشغيل اللعبة",
                Toast.LENGTH_SHORT).show();
        }
    }

    void showVersions() {
        String[] names = {
            "PUBG Global",
            "PUBG KR",
            "PUBG Vietnam",
            "PUBG Taiwan"
        };

        String[] packages = {
            "com.tencent.ig",
            "com.pubg.krmobile",
            "com.vng.pubgmobile",
            "com.rekoo.pubgm"
        };

        new AlertDialog.Builder(this)
            .setTitle("اختر نسخة PUBG")
            .setItems(names, (dialog, which) ->
                openGame(packages[which]))
            .setNegativeButton("إلغاء", null)
            .show();
    }

    void checkStorage() {
        try {
            StatFs stat = new StatFs(
                Environment.getDataDirectory().getPath()
            );

            long total = stat.getTotalBytes() / (1024 * 1024);
            long free = stat.getAvailableBytes() / (1024 * 1024);
            long used = total - free;

            new AlertDialog.Builder(this)
                .setTitle("مساحة الجهاز")
                .setMessage(
                    "المساحة الكلية: " + total + " MB\n\n" +
                    "المساحة المستخدمة: " + used + " MB\n\n" +
                    "المساحة المتوفرة: " + free + " MB"
                )
                .setPositiveButton("حسناً", null)
                .show();

        } catch (Exception e) {
            Toast.makeText(this,
                "تعذر قراءة المساحة",
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
                    android.os.BatteryManager.EXTRA_TEMPERATURE, 0
                );

                new AlertDialog.Builder(this)
                    .setTitle("حرارة البطارية")
                    .setMessage("درجة الحرارة: " + (temp / 10.0) + " °C")
                    .setPositiveButton("حسناً", null)
                    .show();
            }

        } catch (Exception e) {
            Toast.makeText(this,
                "تعذر قراءة درجة الحرارة",
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
}
