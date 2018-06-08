package com.wealthlake.generator.util;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * Created By Rsh
 *
 * @author rsh
 * @Description
 * @Date: 2018/6/7
 * @Time: 15:02
 */

public class FileListenerAdaptor extends FileAlterationListenerAdaptor {

    private FileListenerCallback fileListenerCallback;

    public FileListenerAdaptor() {
        super();
    }

    public FileListenerAdaptor(FileListenerCallback fileListenerCallback) {
        this.fileListenerCallback = fileListenerCallback;
    }

    @Override
    public void onStart(FileAlterationObserver arg0) {
        //System.out.println("begin listening!");
        super.onStart(arg0);
        if (fileListenerCallback != null) {
            fileListenerCallback.callback(null, 1);
        }
    }

    @Override
    public void onStop(FileAlterationObserver arg0) {
        //System.out.println("end listening!");
        super.onStop(arg0);
        if (fileListenerCallback != null) {
            fileListenerCallback.callback(null, 2);
        }
    }

    @Override
    public void onDirectoryCreate(File fold) {
        //System.out.println("fold: " + fold.getAbsolutePath() + " is created.");
        super.onDirectoryCreate(fold);
        if (fileListenerCallback != null) {
            fileListenerCallback.callback(null, 3);
        }
    }

    @Override
    public void onDirectoryChange(File fold) {
        //System.out.println("fold: " + fold.getAbsolutePath() + " is changed.");
        super.onDirectoryChange(fold);
        if (fileListenerCallback != null) {
            fileListenerCallback.callback(null, 4);
        }
    }

    @Override
    public void onDirectoryDelete(File fold) {
        //System.out.println("fold: " + fold.getAbsolutePath() + " is deleted.");
        super.onDirectoryDelete(fold);
        if (fileListenerCallback != null) {
            fileListenerCallback.callback(null, 5);
        }
    }

    @Override
    public void onFileCreate(File file) {
        //System.out.println("file: " + file.getAbsolutePath() + " is created.");
        super.onFileCreate(file);
        if (fileListenerCallback != null) {
            fileListenerCallback.callback(null, 6);
        }
    }

    @Override
    public void onFileChange(File file) {
        //System.out.println("file: " + file.getAbsolutePath() + " is changed.");
        super.onFileChange(file);
        if (fileListenerCallback != null) {
            fileListenerCallback.callback(null, 7);
        }
    }

    @Override
    public void onFileDelete(File file) {
        //System.out.println("file: " + file.getAbsolutePath() + " is deleted");
        super.onFileDelete(file);
        if (fileListenerCallback != null) {
            fileListenerCallback.callback(null, 8);
        }
    }
}
