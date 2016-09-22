package com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yueyinyue.Model.MusicItem;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class MusicCheckTest
{
    private static final String MUSIC_SERVER = "http://musicd.tianyigames.com/";
    private static final String SERVER_MUSIC_LIST_FILE = "D:\\CODE\\MusicAPP\\MusicJuLeTang\\app\\src\\list.txt";
    private static final String CP_JSON_FILE = "D:\\CODE\\MusicAPP\\MusicJuLeTang\\app\\src\\cp.json";

    @Test
    public void test_append_suffix_mp3() throws Exception
    {
        File file=new File(CP_JSON_FILE);
        FileInputStream fileInputStream=new FileInputStream(file);
        byte[] buffer=new byte[fileInputStream.available()];
        fileInputStream.read(buffer);
        fileInputStream.close();

        List<MusicItem> musicItemList = JSON.parseArray(new String(buffer), MusicItem.class);
        int size=musicItemList.size();
        for(int index=0;index<size;index++)
            musicItemList.get(index).setSong(musicItemList.get(index).getSong()+".mp3");

        String jsonString = JSON.toJSONString(musicItemList);

        if(file.exists())
            file.delete();

        FileOutputStream fileOutputStream=new FileOutputStream(file);
        fileOutputStream.write(jsonString.getBytes());
        fileOutputStream.close();
    }

    @Test
    public void test_cp_json_music_list_is_included_in_server_and_set_song_name_with_server_specified() throws Exception
    {
        ArrayList<String> LiYongArrayList = new ArrayList<>();
        File file = new File(SERVER_MUSIC_LIST_FILE);
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = "";

        try
        {
            while (line != null)
            {
                try
                {
                    line = bufferedReader.readLine();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                if (line == null)
                {
                    break;
                }
                else
                {
                    LiYongArrayList.add(line);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String musicItemListStr = "";

        try
        {
            file = new File(CP_JSON_FILE);
            InputStream in = new FileInputStream(file);
            int size = in.available();
            byte buf[] = new byte[size];
            in.read(buf);
            musicItemListStr = new String(buf, "utf-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        List<MusicItem> appMusicList = JSONArray.parseArray(musicItemListStr, MusicItem.class);

        int size = appMusicList.size();
        for (int index = 0; index < size; index++)
        {
            boolean isIncluded = false;
            int length = LiYongArrayList.size();
            for (int i = 0; i < length; i++)
            {
                if (LiYongArrayList.get(i).contains(appMusicList.get(index).getSong()))
                {
                    //System.out.println(totalCpMusicList.get(i).getSong() + ":包含在服务器列表中");
                    isIncluded = true;
                    appMusicList.get(index).setSong(LiYongArrayList.get(i));
                }
            }
            if (!isIncluded)
            {
                System.out.println(appMusicList.get(index) + ":没有包含在服务器列表中");
            }
        }

        file = new File(CP_JSON_FILE);
        if (file.exists())
        {
            file.delete();
        }

        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(JSON.toJSONString(appMusicList).getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("over");
    }

    @Test
    public void test_server_music_list_is_available_to_downlaod()
    {
        File file = new File(SERVER_MUSIC_LIST_FILE);
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = "";

        URL url;
        String musicAddress;
        URLConnection urlConnection;
        InputStream httpInputStream = null;
        try
        {
            while (line != null)
            {
                try
                {
                    line = bufferedReader.readLine();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                if (line == null)
                {
                    break;
                }
                else if (line.contains(" "))
                {
                    System.out.println("包含有空格");
                }
                else
                {
                    musicAddress = MUSIC_SERVER + URLEncoder.encode(line, "utf-8");
                    url = new URL(musicAddress);
                    urlConnection = url.openConnection();
                    httpInputStream = urlConnection.getInputStream();
                    byte[] buffer = new byte[10];
                    int number = httpInputStream.read(buffer);
                    if (number == -1)
                    {
                        System.out.println(line + ":无法访问");
                    }
                    else
                    {
                        System.out.println("可以访问");
                    }

                    httpInputStream.close();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void test_confirm_every_song_in_cp_json_list_is_available_to_download()
    {
        String musicItemListStr = "";

        try
        {
            File file = new File(CP_JSON_FILE);
            InputStream in = new FileInputStream(file);
            int size = in.available();
            byte buf[] = new byte[size];
            in.read(buf);
            musicItemListStr = new String(buf, "utf-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        List<MusicItem> cpJsonMusicList = JSONArray.parseArray(musicItemListStr, MusicItem.class);

        URL url;
        String musicAddress;
        URLConnection urlConnection;
        InputStream inputStream = null;
        for (int index = 0; index < cpJsonMusicList.size(); index++)
        {
            try
            {
                musicAddress = MUSIC_SERVER + URLEncoder.encode(cpJsonMusicList.get(index).getSong(), "utf-8")/* + ".mp4"*/;
                url = new URL(musicAddress);
                urlConnection = url.openConnection();
                inputStream = urlConnection.getInputStream();
                byte[] buffer = new byte[1024];
                int number = inputStream.read(buffer);
                if (number == -1)
                {
                    System.out.println(cpJsonMusicList.get(index).getSong() + ":不能下载");
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        /*File file = new File("cp.json");
        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(JSON.toJSONString(currentCpCategoryMusicList).getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
    }
}
