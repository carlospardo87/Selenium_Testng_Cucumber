package helpers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public void forceQuitDriver(String command)  {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert process != null;
        InputStream inputstream = process.getInputStream();
        BufferedInputStream bufferedinputstream = new BufferedInputStream(inputstream);
        System.out.println(bufferedinputstream.toString());
    }
}
