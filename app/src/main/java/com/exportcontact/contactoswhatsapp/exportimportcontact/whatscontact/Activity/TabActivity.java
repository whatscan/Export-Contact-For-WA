package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetailsParams;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Advertisement;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Constant;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Preference;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.Security;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Fragment.ExportContactFragment;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Fragment.ExportedFilesFragment;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Fragment.FragmentSetting;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Fragment.WhatsappToolFragment;
import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabActivity extends AppCompatActivity {
    private static final int REQ_CODE_PERMISSION = 111;
    public BottomNavigationView bottomNavigation;
    public ImageView imgHome, imgFiles, imgWp, imgSetting;
    public TextView tv_home, tv_file, tv_wp, tv_setting;
    public RelativeLayout rlMain;
    public TextView txtToolbar;
    PurchasesUpdatedListener purchasesUpdatedListener = (billingResult, list) -> {
        Toast.makeText(TabActivity.this, billingResult.toString(), Toast.LENGTH_SHORT).show();
    };
    private BillingClient billingClient;

    @Override
    public void onBackPressed() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE2", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun) {
            Constant.BottomSheetDialogRateApp(TabActivity.this);
            getSharedPreferences("PREFERENCE2", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
        } else {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(TabActivity.this);
            View inflate = LayoutInflater.from(TabActivity.this).inflate(R.layout.dialog_exit, null);
            bottomSheetDialog.setContentView(inflate);

            LinearLayout llExit = inflate.findViewById(R.id.llExit);
            TextView txtExit = inflate.findViewById(R.id.txtExit);
            FrameLayout fl_native = inflate.findViewById(R.id.fl_native);

            if (Preference.getBooleanTheme(false)) {
                llExit.setBackgroundColor(ContextCompat.getColor(this, R.color.darkBlack));
                txtExit.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            }

            Advertisement.shoNativeAds(TabActivity.this, fl_native);

            txtExit.setOnClickListener(v -> {
                bottomSheetDialog.dismiss();
                finishAffinity();
            });
            bottomSheetDialog.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        openFragment(new ExportContactFragment());
        FindView();

        permission();

        checkActiveSubs();

        if (Build.VERSION.SDK_INT >= 23) {
            checkMultiplePermissions();
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + File.separator + "WhatsApp Contact Export");
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Preference.getBooleanTheme(false)) {
            setStatusBarTheme();
            rlMain.setBackgroundColor(ContextCompat.getColor(TabActivity.this, R.color.darkBlack));
            bottomNavigation.setBackground(ContextCompat.getDrawable(TabActivity.this, R.drawable.shape_black));
            tv_home.setTextColor(ContextCompat.getColor(TabActivity.this, R.color.colorWhite));
            tv_setting.setTextColor(ContextCompat.getColor(TabActivity.this, R.color.colorWhite));
            tv_wp.setTextColor(ContextCompat.getColor(TabActivity.this, R.color.colorWhite));
            tv_file.setTextColor(ContextCompat.getColor(TabActivity.this, R.color.colorWhite));
            txtToolbar.setTextColor(ContextCompat.getColor(TabActivity.this, R.color.colorWhite));
        } else {
            setStatusBar();
            rlMain.setBackgroundColor(ContextCompat.getColor(TabActivity.this, R.color.card_color));
            bottomNavigation.setBackground(ContextCompat.getDrawable(TabActivity.this, R.drawable.shape_white_tab));
            tv_wp.setTextColor(ContextCompat.getColor(TabActivity.this, R.color.colorBlack));
            tv_file.setTextColor(ContextCompat.getColor(TabActivity.this, R.color.colorBlack));
            tv_setting.setTextColor(ContextCompat.getColor(TabActivity.this, R.color.colorBlack));
            tv_home.setTextColor(ContextCompat.getColor(TabActivity.this, R.color.colorBlack));
            txtToolbar.setTextColor(ContextCompat.getColor(TabActivity.this, R.color.colorBlack));
        }


    }

    private void FindView() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        imgHome = findViewById(R.id.imgHome);
        imgFiles = findViewById(R.id.imgFiles);
        imgWp = findViewById(R.id.imgWp);
        imgSetting = findViewById(R.id.imgSetting);
        tv_home = findViewById(R.id.tv_home);
        tv_file = findViewById(R.id.tv_file);
        tv_wp = findViewById(R.id.tv_wp);
        tv_setting = findViewById(R.id.tv_setting);
        rlMain = findViewById(R.id.rlMain);
        txtToolbar = findViewById(R.id.txtToolbar);

        billingClient = BillingClient.newBuilder(TabActivity.this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    getSkuDetails();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {

            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    tv_home.setVisibility(View.VISIBLE);
                    tv_file.setVisibility(View.GONE);
                    tv_wp.setVisibility(View.GONE);
                    tv_setting.setVisibility(View.GONE);
                    imgHome.setBackground(ContextCompat.getDrawable(TabActivity.this, R.drawable.round_menu));
                    imgHome.setImageResource(R.drawable.s_daily);
                    imgSetting.setImageResource(R.drawable.n_setting);
                    imgWp.setImageResource(R.drawable.n_wp);
                    imgFiles.setImageResource(R.drawable.n_files);
                    imgSetting.setBackground(null);
                    imgWp.setBackground(null);
                    imgFiles.setBackground(null);
                    openFragment(new ExportContactFragment());
                    return true;
                case R.id.nav_files:
                    tv_file.setVisibility(View.VISIBLE);
                    tv_setting.setVisibility(View.GONE);
                    tv_wp.setVisibility(View.GONE);
                    tv_home.setVisibility(View.GONE);
                    imgFiles.setBackground(ContextCompat.getDrawable(TabActivity.this, R.drawable.round_menu));
                    imgFiles.setImageResource(R.drawable.s_files);
                    imgHome.setImageResource(R.drawable.n_daily);
                    imgSetting.setImageResource(R.drawable.n_setting);
                    imgWp.setImageResource(R.drawable.n_wp);
                    imgSetting.setBackground(null);
                    imgWp.setBackground(null);
                    imgHome.setBackground(null);
                    openFragment(new ExportedFilesFragment());
                    return true;
                case R.id.nav_wp:
                    tv_wp.setVisibility(View.VISIBLE);
                    tv_setting.setVisibility(View.GONE);
                    tv_file.setVisibility(View.GONE);
                    tv_home.setVisibility(View.GONE);
                    imgWp.setBackground(ContextCompat.getDrawable(TabActivity.this, R.drawable.round_menu));
                    imgWp.setImageResource(R.drawable.s_wp);
                    imgFiles.setImageResource(R.drawable.n_files);
                    imgHome.setImageResource(R.drawable.n_daily);
                    imgSetting.setImageResource(R.drawable.n_setting);
                    imgSetting.setBackground(null);
                    imgFiles.setBackground(null);
                    imgHome.setBackground(null);
                    openFragment(new WhatsappToolFragment());
                    return true;
                case R.id.nav_setting:
                    tv_home.setVisibility(View.GONE);
                    tv_setting.setVisibility(View.VISIBLE);
                    tv_file.setVisibility(View.GONE);
                    tv_wp.setVisibility(View.GONE);
                    imgSetting.setBackground(ContextCompat.getDrawable(TabActivity.this, R.drawable.round_menu));
                    imgHome.setImageResource(R.drawable.n_daily);
                    imgSetting.setImageResource(R.drawable.s_setting);
                    imgWp.setImageResource(R.drawable.n_wp);
                    imgFiles.setImageResource(R.drawable.n_files);
                    imgHome.setBackground(null);
                    imgFiles.setBackground(null);
                    imgWp.setBackground(null);
                    openFragment(new FragmentSetting());
                    return true;
            }
            return false;
        });

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void permission() {
        if (!chekPermissionContact()) {
            permissionOnlyContact(105);
            return;
        }
    }

    public boolean chekPermissionContact() {
        return ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") == 0;
    }


    public void permissionOnlyContact(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("android.permission.READ_CONTACTS");
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), i);
        }
    }

    private void checkMultiplePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionsNeeded = new ArrayList();
            List<String> permissionsList = new ArrayList();
            if (!addPermission(permissionsList, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                permissionsNeeded.add("Write Storage");
            }
            if (!addPermission(permissionsList, "android.permission.READ_EXTERNAL_STORAGE")) {
                permissionsNeeded.add("Read Storage");
            }
            if (permissionsList.size() > 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQ_CODE_PERMISSION);
                return;
            }
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission)) {
                return false;
            }
        }
        return true;
    }

    public void setStatusBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.card_color));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.card_color));
        }
    }

    private void getSkuDetails() {
        List<String> skuList_sub = new ArrayList<>();
        skuList_sub.add("weekly_key");
        skuList_sub.add("monthly_key");
        skuList_sub.add("yearly_key");
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList_sub).setType(BillingClient.SkuType.SUBS);
        billingClient.querySkuDetailsAsync(params.build(), (billingResult, list) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getSku().equals("weekly_key")) {
                        Preference.setWeekly_price(list.get(i).getPrice());
                    } else if (list.get(i).getSku().equals("monthly_key")) {
                        Preference.setMonthly_price(list.get(i).getPrice());
                    } else if (list.get(i).getSku().equals("yearly_key")) {
                        Preference.setYearly_price(list.get(i).getPrice());
                    }
                }
            }
        });
    }

    public void setStatusBarTheme() {
        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(TabActivity.this, R.color.darkBlack));
        }
    }

    private void checkActiveSubs() {
        BillingClient billingClient = BillingClient.newBuilder(this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();


        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.SUBS, (billingResult1, list) -> {
                        Purchase.PurchasesResult purchaseResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
                        if (billingResult1.getResponseCode() == BillingClient.BillingResponseCode.OK && !Objects.requireNonNull(purchaseResult.getPurchasesList()).isEmpty()) {

                            ArrayList<String> skusW = new ArrayList<>();
                            skusW.add("weekly_key");

                            ArrayList<String> skusM = new ArrayList<>();
                            skusM.add("monthly_key");

                            ArrayList<String> skusY = new ArrayList<>();
                            skusY.add("yearly_key");

                            if (purchaseResult.getPurchasesList().size() == 0) {
                                Toast.makeText(TabActivity.this, purchaseResult.getPurchasesList().size(), Toast.LENGTH_SHORT).show();
                            } else {
                                for (int i = 0; i < purchaseResult.getPurchasesList().size(); i++) {
                                    if (purchaseResult.getPurchasesList().get(i).getSkus().equals(skusW)) {
                                        if (!verifyValidSignature(purchaseResult.getPurchasesList().get(i).getOriginalJson(), purchaseResult.getPurchasesList().get(i).getSignature())) {
                                            Preference.setActive_Weekly("");
                                        } else {
                                            Preference.setActive_Weekly("true");
                                        }
                                    } else if (purchaseResult.getPurchasesList().get(i).getSkus().equals(skusM)) {
                                        if (!verifyValidSignature(purchaseResult.getPurchasesList().get(i).getOriginalJson(), purchaseResult.getPurchasesList().get(i).getSignature())) {
                                            Preference.setActive_Monthly("");
                                        } else {
                                            Preference.setActive_Monthly("true");
                                        }
                                    } else if (purchaseResult.getPurchasesList().get(i).getSkus().equals(skusY)) {
                                        if (!verifyValidSignature(purchaseResult.getPurchasesList().get(i).getOriginalJson(), purchaseResult.getPurchasesList().get(i).getSignature())) {
                                            Preference.setActive_Yearly("");
                                        } else {
                                            Preference.setActive_Yearly("true");
                                        }
                                    } else {
                                        Preference.setActive_Weekly("");
                                        Preference.setActive_Monthly("");
                                        Preference.setActive_Yearly("");
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgsGjW5sY1EIAv/5rMu7wfOWiHLr/d6qSmD/h11zG9CJcw8bGGz1rUuKtfXXFxURbI9J/Q8k7CsinM785gwh1Ef8MzGzyTaT/Dc4Xpfpi3lX92BXvfGH02azfY3f6TeJ0kjTtM99nKnxc77TVrVw/rk3rHj4ddW/u+UTNa+g4c2VvSkjw8a6SZQNxYAT3kgkRAZaEVCOqxeC2JSTjeHJclH0yeQS03q7TQjZY7KJybTTqotXXCOpcxrmv79Pc41+wF5APxzvT56ODpIFk5hFJ/+zvmxLgwBhI2coyM6RgeWIPcf3bv10WTZwUhEUQosce7s6Yxp9/dUQzToSocfGLdwIDAQAB";
            return Security.verifyPurchase(base64Key, signedData, signature);
        } catch (IOException e) {
            return false;
        }
    }

}