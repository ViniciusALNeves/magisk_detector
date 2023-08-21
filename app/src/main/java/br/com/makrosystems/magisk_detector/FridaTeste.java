package br.com.makrosystems.magisk_detector;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FridaTeste {

    public static boolean isProcess(String processName) {
        Process process;
        String line;
        try {
            StringBuilder output = new StringBuilder();
            ProcessBuilder processBuilder = new ProcessBuilder();

            processBuilder.command("su", "-c", "ps", "-e");
            processBuilder.redirectErrorStream(true);
//            process = Runtime.getRuntime().exec("su -c ps");
            process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
                if (line.contains(processName)) {
//                    System.out.println("LINE: "+line);
                    return true;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
        return false;
    }

}
