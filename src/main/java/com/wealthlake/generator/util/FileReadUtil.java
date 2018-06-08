package com.wealthlake.generator.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * Created By Rsh
 *
 * @author rsh
 * @Description
 * @Date: 2018/6/7
 * @Time: 16:23
 */
public class FileReadUtil {

    // 读取文件指定行后的内容
    public static FileLineInfo readAppointedLineNumberAfter(File sourceFile, int lineNumber) throws IOException {
        if (sourceFile.exists()) {
            StringBuffer sb = new StringBuffer();
            FileReader in = new FileReader(sourceFile);
            LineNumberReader reader = new LineNumberReader(in);
            int totalLineNum = getTotalLines(sourceFile);
            if (lineNumber <= 0 || lineNumber > totalLineNum) {
                return null;
            }
            int lines = 1;
            String s = reader.readLine();
            while (s != null) {
                if (lines >= lineNumber) {
                    if (s != null) {
                        sb.append(s);
                        sb.append("\n");
                    }
                }
                s = reader.readLine();
                lines++;
            }
            reader.close();
            in.close();
            return new FileLineInfo(lines, sb.toString());
        }
        return null;
    }

    // 文件内容的总行数。
    static int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
    }

    /**
     * 读取文件指定行。
     */
    public static void main(String[] args) throws IOException {
        // 指定读取的行号
        int lineNumber = 2;
        // 读取文件
        File sourceFile = new File("D:/java/test.txt");
        // 读取指定的行
        readAppointedLineNumberAfter(sourceFile, lineNumber);
        // 获取文件的内容的总行数
        System.out.println(getTotalLines(sourceFile));
    }

}

