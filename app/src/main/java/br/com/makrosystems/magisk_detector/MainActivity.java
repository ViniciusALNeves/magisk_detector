package br.com.makrosystems.magisk_detector;

import static android.content.ContentValues.TAG;
import static br.com.makrosystems.magisk_detector.CustomApplication.havenSDK;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewScrow;
    private TextView textViewProcess;
    private TextView textViewEXMagisk;
    private TextView textViewHaven;
    private TextView textViewFrida;
    private TextView textViewFileMagisk;
    private TextView textViewBuild;
    private TextView textViewSProccess;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewScrow = findViewById(R.id.textViewScrow);
        textViewProcess = findViewById(R.id.textViewProcess);
        textViewEXMagisk = findViewById(R.id.textViewEXMagisk);
        textViewHaven = findViewById(R.id.textViewHaven);
        textViewFrida = findViewById(R.id.textViewFrida);
        textViewFileMagisk = findViewById(R.id.textViewFileMagisk);
        textViewBuild = findViewById(R.id.textViewBuild);
        textViewSProccess =findViewById(R.id.textViewSProccess);
        button = findViewById(R.id.button);

        AppList appList = new AppList(this);
        String listaDApp = appList.responseAppList();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //App List
                textViewScrow.setText(listaDApp);

                //Magisk Proccess
                if (FridaTeste.isProcess("magisk")) {
                    textViewProcess.setText("PROCESSO DO MAGISK HIDE DETECTADO");
                } else {
                    textViewProcess.setText("NÃO EXISTE PROCESSO DO MAGISK HIDE");
                }

                //Package Magisk
                if (listaDApp.contains("magisk")) {
                    if (listaDApp.contains("magisk")) {
                        textViewEXMagisk.setText("MAGISK INSTALADO!");
                    } else {
                        textViewEXMagisk.setText("MAGISK NÃO ESTÁ INSTALADO!");
                    }

                    //Haven check Magisk
                    textViewHaven.setText("IS POSSIBLE MAGISK: " + havenSDK.DeviceInfo.possibleHookCheck());
                    textViewFrida.setText("FRIDA: " + havenSDK.DeviceInfo.isFridaCheck());

                    //Verifica por arquivos especificos do magisk
                    File magiskHideFile = new File("/sbin/.magisk");
                    if (magiskHideFile.exists()) {
                        textViewFileMagisk.setText("ARQUIVO ESPECIFICO ENCONTRADO");
                    }else {
                        textViewFileMagisk.setText("ARQUIVO ESPECIFICO NAO ENCONTRADO");
                    }

                    //Verificar as Propriedades do Build
                    if ("1".equals(System.getProperty("ro.debuggable")) ||
                            "1".equals(System.getProperty("ro.secure")) ||
                            "0".equals(System.getProperty("ro.build.type"))) {
                        // Possível ocultação de root
                        textViewBuild.setText("OCULTAÇÃO DE BUILD DETECTADA");
                    }else {
                        textViewBuild.setText("OCULTAÇÃO DE BUILD NÃO DETECTADA");
                    }

                    //Verificar Processos Suspeitos
                    ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningAppProcessInfo> runningProcesses = activityManager.getRunningAppProcesses();

                    for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                        if (processInfo.processName.contains("su") || processInfo.processName.contains("magisk")) {
                            // Processo suspeito encontrado
                            textViewSProccess.setText("PROCESSO SUSPEITO ENCONTRATO");
                        }else {
                            textViewSProccess.setText("PROCESSO SUSPEITO NÃO ENCONTRADO");
                        }
                    }
                }
            }
        });

//        Process process = null;
//        try {
//            process = Runtime.getRuntime().exec("ls proc/self/mounts");
//            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line = null;
//            line = in.readLine();
//
//            Log.d(TAG,"LINE: "+line);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}