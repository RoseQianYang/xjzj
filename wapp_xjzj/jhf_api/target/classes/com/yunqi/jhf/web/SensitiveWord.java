package com.yunqi.jhf.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 敏感关键字 过滤
 * @author wangsong
 *
 */

/**
 * 使用方法： SensitiveWord sw = new SensitiveWord();  
               sw.InitializationWork();  
 * 
 *    strStar = sw.filterInfo(str); // str 将要被过滤信息     strStar 过滤后的信息 敏感字用 * 代替
 *    strStar.contains("*") // 分享内容含有敏感字      strStar 就包含  * contains返回的是布尔类型true 和false 包含的话就返回true，不包含的话就返回false 
 */

public class SensitiveWord {
	
	private static StringBuilder replaceAll;//初始化  
    private static String encoding = "UTF-8";  
    private static String replceStr = "*";  
    private static int replceSize = 500;  
    private static String fileName = "CensorWords.txt";   //敏感字库
    private static List<String> arrayList; 
    
    
    /** 
     * 文件要求路径在src或resource下，默认文件名为CensorWords.txt 
     * @param fileName 词库文件名(含后缀) 
     */  
    public SensitiveWord(String fileName)  
    {  
        SensitiveWord.fileName = fileName;  
    }  
    
    /** 
     * @param replceStr 敏感词被转换的字符 
     * @param replceSize 初始转义容量 
     */  
    public SensitiveWord(String replceStr,int replceSize)  
    {  
        SensitiveWord.replceStr = fileName;  
        SensitiveWord.replceSize = replceSize;  
    }  
    
    /** 
     * @param str 将要被过滤信息 
     * @return 过滤后的信息 
     */  
    public static String filterInfo(String str)  
    {  
        StringBuilder buffer = new StringBuilder(str);  
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>(arrayList.size());  
        String temp;  
        for(int x = 0; x < arrayList.size();x++)  
        {  
            temp = arrayList.get(x);  
            int findIndexSize = 0;  
            for(int start = -1;(start=buffer.indexOf(temp,findIndexSize)) > -1;)  
            {  
                findIndexSize = start+temp.length();//从已找到的后面开始找  
                Integer mapStart = hash.get(start);//起始位置  
                if(mapStart == null || (mapStart != null && findIndexSize > mapStart))//满足1个，即可更新map  
                {  
                    hash.put(start, findIndexSize);  
                }  
            }  
        }  
        Collection<Integer> values = hash.keySet();  
        for(Integer startIndex : values)  
        {  
            Integer endIndex = hash.get(startIndex);  
            buffer.replace(startIndex, endIndex, replaceAll.substring(0,endIndex-startIndex));  
        }  
        hash.clear();  
        return buffer.toString();  
    }  
    
    
    /** 
     *   初始化敏感词库 
     */  
    public static void InitializationWork()  
    {  
        replaceAll = new StringBuilder(replceSize);  
        for(int x=0;x < replceSize;x++)  
        {  
            replaceAll.append(replceStr);  
        }  
        //加载词库  
        arrayList = new ArrayList<String>();  
        InputStreamReader read = null;  
        BufferedReader bufferedReader = null;  
        try {  
            read = new InputStreamReader(SensitiveWord.class.getClassLoader().getResourceAsStream(fileName),encoding);  
            bufferedReader = new BufferedReader(read);  
            for(String txt = null;(txt = bufferedReader.readLine()) != null;){  
                if(!arrayList.contains(txt))  
                    arrayList.add(txt);  
            }  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                if(null != bufferedReader)  
                bufferedReader.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            try {  
                if(null != read)  
                read.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    public StringBuilder getReplaceAll() {  
        return replaceAll;  
    }  
    
    
    public void setReplaceAll(StringBuilder replaceAll) {  
        SensitiveWord.replaceAll = replaceAll;  
    }  
    public String getReplceStr() {  
        return replceStr;  
    }  
    public void setReplceStr(String replceStr) {  
        SensitiveWord.replceStr = replceStr;  
    }  
    public int getReplceSize() {  
        return replceSize;  
    }  
    public void setReplceSize(int replceSize) {  
        SensitiveWord.replceSize = replceSize;  
    }  
    public String getFileName() {  
        return fileName;  
    }  
    public void setFileName(String fileName) {  
        SensitiveWord.fileName = fileName;  
    }  
    public List<String> getArrayList() {  
        return arrayList;  
    }  
    public void setArrayList(List<String> arrayList) {  
        SensitiveWord.arrayList = arrayList;  
    }  
    public String getEncoding() {  
        return encoding;  
    }  
    public void setEncoding(String encoding) {  
        SensitiveWord.encoding = encoding;  
    }  

}
