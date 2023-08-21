package br.com.makrosystems.magisk_detector;

import android.app.Application;

import br.com.makrosystems.haven.HavenSDK;

public class CustomApplication extends Application {

    //Classe responsavel pela conecção com o Haven
     public static HavenSDK havenSDK;
     @Override
     public void onCreate(){
         super.onCreate();
         try {
             havenSDK = new HavenSDK();
             havenSDK.Crypto.RSA_encrypt("", "");
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
}
