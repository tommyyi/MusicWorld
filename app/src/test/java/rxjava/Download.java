package rxjava;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/8/3.
 */
public class Download
{
    @Test
    public void testdl() throws Exception
    {
        //String song = "雪花轻飘";
        String song = "承诺";
        String suffix = ".mp4";
        String musicAddress = "http://musicd.tianyigames.com/" + URLEncoder.encode(song, "utf-8") + suffix;
        URL url=new URL(musicAddress);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        InputStream inputStream=httpURLConnection.getInputStream();

        FileOutputStream fileOutputStream=new FileOutputStream(new File(song+suffix));
        byte[] buffer=new byte[20480];
        while (true)
        {
            int count = inputStream.read(buffer);
            if(count==-1)
                break;
            fileOutputStream.write(buffer,0,count);
            System.out.println(count+"\r\n");
        }

        inputStream.close();
        fileOutputStream.close();
    }
}
