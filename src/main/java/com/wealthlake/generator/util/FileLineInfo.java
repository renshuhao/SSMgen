package com.wealthlake.generator.util;

public class FileLineInfo {
    private int lineNumber;
    private String content;

    public FileLineInfo(int lineNumber, String content) {
        this.lineNumber = lineNumber;
        this.content = content;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
