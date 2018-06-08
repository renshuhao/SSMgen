package com.wealthlake.generator.util;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * Created By Rsh
 *
 * @author rsh
 * @Description
 * @Date: 2018/6/7
 * @Time: 14:58
 */
public class FileMonitor {

    private FileAlterationMonitor monitor = null;

    private File file;
    private FileAlterationListener alterationListener;
    private long intervalTime = 1000L;

    public FileMonitor(long interval) throws Exception {
        monitor = new FileAlterationMonitor(interval);
    }

    public FileMonitor() throws Exception {
        monitor = new FileAlterationMonitor(intervalTime);
    }

    public void monitor(String path, FileAlterationListener listener) {
        FileAlterationObserver observer = new FileAlterationObserver(new File(path));
        monitor.addObserver(observer);
        observer.addListener(listener);
    }

    public void monitor() {
        FileAlterationObserver observer = new FileAlterationObserver(file);
        monitor.addObserver(observer);
        observer.addListener(alterationListener);
    }

    public void stop() throws Exception {
        try {
            monitor.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFilePath(String filePath) {
        if (filePath != null) {
            this.file = new File(filePath);
        }
    }

    public FileAlterationListener getAlterationListener() {
        return alterationListener;
    }

    public void setAlterationListener(FileAlterationListener alterationListener) {
        this.alterationListener = alterationListener;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

}
