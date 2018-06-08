package com.wealthlake.generator.util;

import java.io.File;

/**
 * Created By Rsh
 *
 * @author rsh
 * @Description
 * @Date: 2018/6/7
 * @Time: 15:18
 */
public interface FileListenerCallback {

    /**
     *
     * @param file
     * @param event 事件，1：onStart，2：onStop，3：onDirectoryCreate，4：onDirectoryChange，5：onDirectoryDelete，6：onFileCreate，7：onFileChange，8：onFileDelete
     */
    void callback(File file, int event);
}
