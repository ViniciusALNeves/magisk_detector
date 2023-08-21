package br.com.makrosystems.magisk_detector;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class AppList {

    private Context context;

    public AppList(Context context){
        this.context = context;
    }

    public String responseAppList(){

        StringBuilder stringBuilder = new StringBuilder();
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> applications = packageManager.getInstalledApplications(0);
        for (ApplicationInfo appInfo : applications) {
            stringBuilder.append(appInfo.packageName).append("\n");
        }

        return stringBuilder.toString();
    }

}
