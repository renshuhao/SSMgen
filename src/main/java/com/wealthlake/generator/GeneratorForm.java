package com.wealthlake.generator;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.util.GLogger;
import com.wealthlake.generator.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成器窗体类，程序入口
 *
 * @author rsh
 * @version v.1.2
 * @date 2018/06/06
 */

public class GeneratorForm extends JFrame {
    private JPanel settingsPanel;
    private JLabel setBasepackageLabel;
    private JTextField setBasepackageLabelText;
    private JLabel setNamespaceLabel;
    private JTextField setNamespaceLabelText;
    private JLabel setOutRootLabel;
    private JTextField setOutRootLabelText;
    private JLabel setTableRemovePrefixesLabel;
    private JTextField setTableRemovePrefixesLabelText;
    private JLabel setDbTypeLabel;
    private JComboBox setDbTypeComboBox;
    private JLabel setJdbcUrlLabel;
    private JTextField setJdbcUrlLabelText;
    private JLabel setJdbcDriverLabel;
    private JTextField setJdbcDriverLabelText;
    private JLabel setJdbcSchemaLabel;
    private JTextField setJdbcSchemaLabelText;
    private JLabel setJdbcCatalogLabel;
    private JTextField setJdbcCatalogLabelText;
    private JLabel setJdbcUsernameLabel;
    private JTextField setJdbcUsernameLabelText;
    private JLabel setJdbcPasswordLabel;
    private JTextField setJdbcPasswordLabelText;
    private JLabel setTableLabel;
    private JTextField setTableLabelText;
    private JLabel setTemplateLabel;
    private JTextField setTemplateLabelText;
    private JTextArea logTextArea;
    private JButton generatorBut;

    Settings setting = Settings.getInstance();
    PrintStream out;
    Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
    Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
    int screenWidth = screenSize.width; // 获取屏幕的宽
    int screenHeight = screenSize.height; // 获取屏幕的高
    FileMonitor fileMonitor;
    int lineNumber = 1;
    boolean isRead = false;
    File logFile;
    String templateFolder = "templates";
    String logFolder = "log";
    boolean logFileCreate = false;
    int isNeedBottom = 0;

    // 程序入口
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GeneratorForm().setVisible(true);
            }
        });
    }

    public GeneratorForm() {
        ConfigProperties.reload();
        initComponents();
        this.setResizable(false);
        int windowWidth = this.getWidth(); // 获得窗口宽
        int windowHeight = this.getHeight(); // 获得窗口高
        this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
        try {
            try {
                String ln = ConfigProperties.getProperty("lineNumber");
                if (ln != null && !"".equals(ln)) {
                    lineNumber = Integer.valueOf(ln);
                }
            } catch (NumberFormatException e) {
                outputException(e);
            }
            //String path = GeneratorForm.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            //path = URLDecoder.decode(path, "utf-8");
            String fileName = "log_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".log";
            File logFolderFile = new File(logFolder);
            if (!logFolderFile.isDirectory()) {
                logFolderFile.mkdir();
            }
            logFile = new File(logFolder + File.separator + fileName);
            boolean b = true;
            if (!logFile.exists()) {
                b = logFile.createNewFile();
            } else {
                logFile.delete();
            }
            if (b) {
                out = new PrintStream(new FileOutputStream(logFile, true), true);
                System.setOut(out);
                System.setErr(out);
                GLogger.out = out;
                GLogger.err = out;
                logFileCreate = true;
            } else {
                logTextArea.append("\n 日志文件创建失败");
            }
            try {
                FileListenerAdaptor fileListenerAdaptor = new FileListenerAdaptor(new FileListenerCallback() {
                    @Override
                    public void callback(File file, int event) {
                        try {
                            refreshLog();
                        } catch (Exception e) {
                            outputException(e);
                        }
                    }
                });
                fileMonitor = new FileMonitor();
                fileMonitor.setFile(logFile);
                fileMonitor.setAlterationListener(fileListenerAdaptor);
                fileMonitor.monitor();
                fileMonitor.start();
            } catch (Exception e1) {
                outputException(e1);
            }
        } catch (Exception e1) {
            outputException(e1);
        }
    }

    /**
     * 刷新显示日志信息
     */
    private void refreshLog() {
        try {
            if (!isRead) {
                isRead = true;
                FileLineInfo fileLineInfo = FileReadUtil.readAppointedLineNumberAfter(logFile, lineNumber);
                if (fileLineInfo != null) {
                    lineNumber = fileLineInfo.getLineNumber();
                    logTextArea.append(fileLineInfo.getContent());
                    logTextArea.paintImmediately(logTextArea.getX(), logTextArea.getY(), logTextArea.getWidth(), logTextArea.getHeight());
                    isNeedBottom = 0;
                    ConfigProperties.setProperty("lineNumber", String.valueOf(lineNumber));
                }
                isRead = false;
            }
        } catch (Exception e) {
            outputException(e);
        }
    }

    /**
     * 输入异常信息
     *
     * @param e
     */
    private void outputException(Exception e) {
        if (logFileCreate) {
            System.out.println(e);
        } else {
            logTextArea.append(e.getClass() + e.getMessage());
            logTextArea.paintImmediately(logTextArea.getX(), logTextArea.getY(), logTextArea.getWidth(), logTextArea.getHeight());
            isNeedBottom = 0;
        }
    }

    /**
     * 初始化组件
     */
    private void initComponents() {
        settingsPanel = new JPanel();
        settingsPanel.setBorder(new LineBorder(Color.GRAY, 1, true));
        settingsPanel.setToolTipText("\u8BBE\u7F6E\u533A");

        setBasepackageLabel = new JLabel("基础包名");
        setBasepackageLabel.setSize(new Dimension(50, 30));
        setBasepackageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setBasepackageLabel.setVerticalAlignment(SwingConstants.CENTER);
        setBasepackageLabelText = new JTextField("");
        setBasepackageLabelText.setText(ConfigProperties.getProperty("basepackage"));
        setBasepackageLabelText.addFocusListener(new TextFocusListener("", setBasepackageLabelText, 1, setting));//添加焦点事件反映
        setBasepackageLabelText.setPreferredSize(new Dimension(400, 30));
        settingsChange(setBasepackageLabelText, 1);

        setNamespaceLabel = new JLabel("命名空间");
        setNamespaceLabel.setSize(new Dimension(50, 30));
        setNamespaceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setNamespaceLabel.setVerticalAlignment(SwingConstants.CENTER);
        setNamespaceLabelText = new JTextField("");
        setNamespaceLabelText.setText(ConfigProperties.getProperty("namespace"));
        setNamespaceLabelText.addFocusListener(new TextFocusListener("", setNamespaceLabelText, 2, setting));//添加焦点事件反映
        setNamespaceLabelText.setPreferredSize(new Dimension(400, 30));
        settingsChange(setNamespaceLabelText, 2);

        setOutRootLabel = new JLabel("输出目录");
        setOutRootLabel.setSize(new Dimension(50, 30));
        setOutRootLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setOutRootLabel.setVerticalAlignment(SwingConstants.CENTER);
        setOutRootLabelText = new JTextField("");
        setOutRootLabelText.setText(ConfigProperties.getProperty("outRoot"));
        setOutRootLabelText.addFocusListener(new TextFocusListener("", setOutRootLabelText, 3, setting));//添加焦点事件反映
        setOutRootLabelText.setPreferredSize(new Dimension(400, 30));
        settingsChange(setOutRootLabelText, 3);

        setTableRemovePrefixesLabel = new JLabel("去除表名前缀");
        setTableRemovePrefixesLabel.setSize(new Dimension(50, 30));
        setTableRemovePrefixesLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setTableRemovePrefixesLabel.setVerticalAlignment(SwingConstants.CENTER);
        setTableRemovePrefixesLabelText = new JTextField("");
        setTableRemovePrefixesLabelText.setText(ConfigProperties.getProperty("tableRemovePrefixes"));
        setTableRemovePrefixesLabelText.addFocusListener(new TextFocusListener("", setTableRemovePrefixesLabelText, 4, setting));//添加焦点事件反映
        setTableRemovePrefixesLabelText.setPreferredSize(new Dimension(400, 30));
        settingsChange(setTableRemovePrefixesLabelText, 4);

        setDbTypeLabel = new JLabel("数据库类型");
        setDbTypeComboBox = new JComboBox();
        setDbTypeComboBox.setModel(new DefaultComboBoxModel(new String[]{"Mysql", "Oracle", "SQLServer2000",
                "SQLServer2005", "JTDs for SQLServer", "PostgreSql", "Sybase", "DB2", "HsqlDB", "Derby", "H2"}));
        setDbTypeComboBox.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dbTypeComboBox1ItemStateChanged(evt);
            }
        });

        setJdbcUrlLabel = new JLabel("数据源:URL");
        setJdbcUrlLabel.setSize(new Dimension(50, 30));
        setJdbcUrlLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setJdbcUrlLabel.setVerticalAlignment(SwingConstants.CENTER);
        setJdbcUrlLabelText = new JTextField("");
        setJdbcUrlLabelText.setText(ConfigProperties.getProperty("jdbc_url"));
        setJdbcUrlLabelText.addFocusListener(new TextFocusListener("", setJdbcUrlLabelText, 6, setting));//添加焦点事件反映
        setJdbcUrlLabelText.setPreferredSize(new Dimension(400, 30));

        setJdbcDriverLabel = new JLabel("数据源:驱动");
        setJdbcDriverLabel.setSize(new Dimension(50, 30));
        setJdbcDriverLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setJdbcDriverLabel.setVerticalAlignment(SwingConstants.CENTER);
        setJdbcDriverLabelText = new JTextField("");
        setJdbcDriverLabelText.setText(ConfigProperties.getProperty("jdbc_driver"));
        setJdbcDriverLabelText.addFocusListener(new TextFocusListener("", setJdbcDriverLabelText, 7, setting));//添加焦点事件反映
        setJdbcDriverLabelText.setPreferredSize(new Dimension(400, 30));
        setJdbcDriverLabelText.setEditable(false);

        setJdbcSchemaLabel = new JLabel("数据源:schema");
        setJdbcSchemaLabel.setSize(new Dimension(50, 30));
        setJdbcSchemaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setJdbcSchemaLabel.setVerticalAlignment(SwingConstants.CENTER);
        setJdbcSchemaLabelText = new JTextField("");
        setJdbcSchemaLabelText.setText(ConfigProperties.getProperty("jdbc_schema"));
        setJdbcSchemaLabelText.addFocusListener(new TextFocusListener("", setJdbcSchemaLabelText, 8, setting));//添加焦点事件反映
        setJdbcSchemaLabelText.setPreferredSize(new Dimension(400, 30));
        setJdbcSchemaLabel.setVisible(false);
        setJdbcSchemaLabelText.setVisible(false);

        setJdbcCatalogLabel = new JLabel("数据源:catalog");
        setJdbcCatalogLabel.setSize(new Dimension(50, 30));
        setJdbcCatalogLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setJdbcCatalogLabel.setVerticalAlignment(SwingConstants.CENTER);
        setJdbcCatalogLabelText = new JTextField("");
        setJdbcCatalogLabelText.setText(ConfigProperties.getProperty("jdbc_catalog"));
        setJdbcCatalogLabelText.addFocusListener(new TextFocusListener("", setJdbcCatalogLabelText, 9, setting));//添加焦点事件反映
        setJdbcCatalogLabelText.setPreferredSize(new Dimension(400, 30));
        setJdbcCatalogLabel.setVisible(false);
        setJdbcCatalogLabelText.setVisible(false);

        dbTypeComboBox1ItemStateChanged(null);
        settingsChange(setJdbcUrlLabelText, 6);
        settingsChange(setJdbcDriverLabelText, 7);
        settingsChange(setJdbcSchemaLabelText, 8);
        settingsChange(setJdbcCatalogLabelText, 9);

        setJdbcUsernameLabel = new JLabel("数据源:用户名");
        setJdbcUsernameLabel.setSize(new Dimension(50, 30));
        setJdbcUsernameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setJdbcUsernameLabel.setVerticalAlignment(SwingConstants.CENTER);
        setJdbcUsernameLabelText = new JTextField("");
        setJdbcUsernameLabelText.setText(ConfigProperties.getProperty("jdbc_username"));
        setJdbcUsernameLabelText.addFocusListener(new TextFocusListener("", setJdbcUsernameLabelText, 10, setting));//添加焦点事件反映
        setJdbcUsernameLabelText.setPreferredSize(new Dimension(400, 30));
        settingsChange(setJdbcUsernameLabelText, 10);

        setJdbcPasswordLabel = new JLabel("数据源:密码");
        setJdbcPasswordLabel.setSize(new Dimension(50, 30));
        setJdbcPasswordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setJdbcPasswordLabel.setVerticalAlignment(SwingConstants.CENTER);
        setJdbcPasswordLabelText = new JTextField("");
        setJdbcPasswordLabelText.setText(ConfigProperties.getProperty("jdbc_password"));
        setJdbcPasswordLabelText.addFocusListener(new TextFocusListener("", setJdbcPasswordLabelText, 11, setting));//添加焦点事件反映
        setJdbcPasswordLabelText.setPreferredSize(new Dimension(400, 30));
        settingsChange(setJdbcPasswordLabelText, 11);

        setTableLabel = new JLabel("数据库表");
        setTableLabel.setSize(new Dimension(50, 30));
        setTableLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setTableLabel.setVerticalAlignment(SwingConstants.CENTER);
        setTableLabelText = new JTextField("");
        setTableLabelText.setText(ConfigProperties.getProperty("table"));
        setTableLabelText.addFocusListener(new TextFocusListener("", setTableLabelText, 12, setting));//添加焦点事件反映
        setTableLabelText.setPreferredSize(new Dimension(400, 30));
        settingsChange(setTableLabelText, 12);

        setTemplateLabel = new JLabel("模板名称");
        setTemplateLabel.setSize(new Dimension(50, 30));
        setTemplateLabel.setHorizontalAlignment(SwingConstants.LEFT);
        setTemplateLabel.setVerticalAlignment(SwingConstants.CENTER);
        setTemplateLabelText = new JTextField("");
        setTemplateLabelText.setText(ConfigProperties.getProperty("template"));
        setTemplateLabelText.addFocusListener(new TextFocusListener("", setTemplateLabelText, 13, setting));//添加焦点事件反映
        setTemplateLabelText.setPreferredSize(new Dimension(40, 30));
        settingsChange(setTemplateLabelText, 13);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logTextArea.setColumns(50);
        logTextArea.setRows(10);
        JScrollPane jsp = new JScrollPane(logTextArea);
        jsp.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent evt) {
                if (evt.getAdjustmentType() == AdjustmentEvent.TRACK && isNeedBottom <= 3) {
                    jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getModel().getMaximum() - jsp.getVerticalScrollBar().getModel().getExtent());
                    isNeedBottom++;
                }
            }
        });

        generatorBut = new JButton("开始生成");
        generatorBut.setMargin(new Insets(6, 15, 6, 15));
        generatorBut.setPreferredSize(new Dimension(100, 40));
        generatorBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    generator(e);
                } catch (Exception e1) {
                    outputException(e1);
                }
            }
        });

        // 创建内容面板容器
        JPanel panel = new JPanel();
        //panel.setPreferredSize(new Dimension(500, 600));//关键代码,设置JPanel的大小
        // 创建分组布局，并关联容器
        GroupLayout layout = new GroupLayout(panel);
        // 设置容器的布局
        panel.setLayout(layout);
        // 自动创建组件之间的间隙
        layout.setAutoCreateGaps(true);
        // 自动创建容器与触到容器边框的组件之间的间隙
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup hParalGroup01 = layout.createParallelGroup()
                .addComponent(setBasepackageLabel)
                .addComponent(setNamespaceLabel)
                .addComponent(setOutRootLabel)
                .addComponent(setTableRemovePrefixesLabel)
                .addComponent(setDbTypeLabel)
                .addComponent(setJdbcUrlLabel)
                .addComponent(setJdbcDriverLabel)
                .addComponent(setJdbcSchemaLabel)
                .addComponent(setJdbcCatalogLabel)
                .addComponent(setJdbcUsernameLabel)
                .addComponent(setJdbcPasswordLabel)
                .addComponent(setTableLabel)
                .addComponent(setTemplateLabel);
        GroupLayout.ParallelGroup hParalGroup02 = layout.createParallelGroup()
                .addComponent(setBasepackageLabelText)
                .addComponent(setNamespaceLabelText)
                .addComponent(setOutRootLabelText)
                .addComponent(setTableRemovePrefixesLabelText)
                .addComponent(setDbTypeComboBox)
                .addComponent(setJdbcUrlLabelText)
                .addComponent(setJdbcDriverLabelText)
                .addComponent(setJdbcSchemaLabelText)
                .addComponent(setJdbcCatalogLabelText)
                .addComponent(setJdbcUsernameLabelText)
                .addComponent(setJdbcPasswordLabelText)
                .addComponent(setTableLabelText)
                .addComponent(setTemplateLabelText);

        // 水平串行（左右）hParalGroup01 和 hParalGroup02
        GroupLayout.SequentialGroup hSeqGroup = layout.createSequentialGroup().addGroup(hParalGroup01).addGroup(hParalGroup02);
        // 水平并行（上下）hSeqGroup 和 btn05
        GroupLayout.ParallelGroup hParalGroup = layout.createParallelGroup()
                .addGroup(hSeqGroup)
                .addComponent(jsp, GroupLayout.Alignment.CENTER)
                .addComponent(generatorBut, GroupLayout.Alignment.CENTER);
        ;
        layout.setHorizontalGroup(hParalGroup);

        //=================================

        GroupLayout.ParallelGroup vParalGroup01 = layout.createParallelGroup().addComponent(setBasepackageLabel).addComponent(setBasepackageLabelText);
        GroupLayout.ParallelGroup vParalGroup02 = layout.createParallelGroup().addComponent(setNamespaceLabel).addComponent(setNamespaceLabelText);
        GroupLayout.ParallelGroup vParalGroup03 = layout.createParallelGroup().addComponent(setOutRootLabel).addComponent(setOutRootLabelText);
        GroupLayout.ParallelGroup vParalGroup04 = layout.createParallelGroup().addComponent(setTableRemovePrefixesLabel).addComponent(setTableRemovePrefixesLabelText);
        GroupLayout.ParallelGroup vParalGroup05 = layout.createParallelGroup().addComponent(setDbTypeLabel).addComponent(setDbTypeComboBox);
        GroupLayout.ParallelGroup vParalGroup06 = layout.createParallelGroup().addComponent(setJdbcUrlLabel).addComponent(setJdbcUrlLabelText);
        GroupLayout.ParallelGroup vParalGroup07 = layout.createParallelGroup().addComponent(setJdbcDriverLabel).addComponent(setJdbcDriverLabelText);
        GroupLayout.ParallelGroup vParalGroup08 = layout.createParallelGroup().addComponent(setJdbcSchemaLabel).addComponent(setJdbcSchemaLabelText);
        GroupLayout.ParallelGroup vParalGroup09 = layout.createParallelGroup().addComponent(setJdbcCatalogLabel).addComponent(setJdbcCatalogLabelText);
        GroupLayout.ParallelGroup vParalGroup10 = layout.createParallelGroup().addComponent(setJdbcUsernameLabel).addComponent(setJdbcUsernameLabelText);
        GroupLayout.ParallelGroup vParalGroup11 = layout.createParallelGroup().addComponent(setJdbcPasswordLabel).addComponent(setJdbcPasswordLabelText);
        GroupLayout.ParallelGroup vParalGroup12 = layout.createParallelGroup().addComponent(setTableLabel).addComponent(setTableLabelText);
        GroupLayout.ParallelGroup vParalGroup13 = layout.createParallelGroup().addComponent(setTemplateLabel).addComponent(setTemplateLabelText);

        // 垂直串行（上下）vParalGroup01, vParalGroup02 和 btn05
        GroupLayout.SequentialGroup vSeqGroup = layout.createSequentialGroup()
                .addGroup(vParalGroup01)
                .addGroup(vParalGroup02)
                .addGroup(vParalGroup03)
                .addGroup(vParalGroup04)
                .addGroup(vParalGroup05)
                .addGroup(vParalGroup06)
                .addGroup(vParalGroup07)
                .addGroup(vParalGroup08)
                .addGroup(vParalGroup09)
                .addGroup(vParalGroup10)
                .addGroup(vParalGroup11)
                .addGroup(vParalGroup12)
                .addGroup(vParalGroup13)
                .addComponent(jsp)
                .addComponent(generatorBut);
        ;
        layout.setVerticalGroup(vSeqGroup);

        setContentPane(panel);
        setTitle("SSM代码生成器");

        // 监听窗口关闭事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (out != null) {
                    out.close();
                }
                System.exit(0);
            }
        });
        pack();
    }

    /**
     * 数据类型修改事件
     *
     * @param evt
     */
    private void dbTypeComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {
        setting.setDbType(setDbTypeComboBox.getSelectedIndex() + 1);
        setDefaultJdbc(setting.getDbType());
        // Oracle 数据库
        if (setting.getDbType() == 2) {
            setJdbcSchemaLabel.setVisible(true);
            setJdbcSchemaLabelText.setVisible(true);
            setJdbcCatalogLabel.setVisible(true);
            setJdbcCatalogLabelText.setVisible(true);
        } else {
            setJdbcSchemaLabel.setVisible(false);
            setJdbcSchemaLabelText.setVisible(false);
            setJdbcCatalogLabel.setVisible(false);
            setJdbcCatalogLabelText.setVisible(false);
        }
    }

    /**
     * 设置数据源配置
     *
     * @param dbType
     */
    private void setDefaultJdbc(int dbType) {
        String[] jdbcConfig = getdefaultJdbcConfig(dbType);
        String defaultJdbcUrl = jdbcConfig[0];
        String defaultJdbcDriver = jdbcConfig[1];
        if (setJdbcUrlLabelText.getText() == null || "".equals(setJdbcUrlLabelText.getText())) {
            setJdbcUrlLabelText.setText(defaultJdbcUrl);
        }
        if (setJdbcDriverLabelText.getText() == null || "".equals(setJdbcDriverLabelText.getText())) {
            setJdbcDriverLabelText.setText(defaultJdbcDriver);
        }
    }

    /**
     * 获取数据类型对应的数据源链接地址
     *
     * @param dbType
     * @return
     */
    private String[] getdefaultJdbcConfig(int dbType) {
        String jdbcUrl = null;
        String jdbcDriver = null;
        switch (dbType) {
            case 1:
                jdbcUrl = "jdbc:mysql://localhost:3306/[database]?useUnicode=true&amp;characterEncoding=UTF-8";
                jdbcDriver = "com.mysql.jdbc.Driver";
                break;
            case 2:
                jdbcUrl = "jdbc:oracle:thin:@localhost:1521:[sid]";
                jdbcDriver = "oracle.jdbc.driver.OracleDriver";
                break;
            case 3:
                jdbcUrl = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=[database]";
                jdbcDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
                break;
            case 4:
                jdbcUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=[database]";
                jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                break;
            case 5:
                jdbcUrl = "jdbc:jtds:sqlserver://localhost:1433/[database];tds=8.0;lastupdatecount=true";
                jdbcDriver = "net.sourceforge.jtds.jdbc.Driver";
                break;
            case 6:
                jdbcUrl = "jdbc:postgresql://localhost/[database]";
                jdbcDriver = "org.postgresql.Driver";
                break;
            case 7:
                jdbcUrl = "jdbc:sybase:Tds:localhost:5007/[database]";
                jdbcDriver = "com.sybase.jdbc.SybDriver";
                break;
            case 8:
                jdbcUrl = "jdbc:db2://localhost:5000/[database]";
                jdbcDriver = "com.ibm.db2.jdbc.app.DB2Driver";
                break;
            case 9:
                jdbcUrl = "jdbc:hsqldb:mem:generatorDB";
                jdbcDriver = "org.hsqldb.jdbcDriver";
                break;
            case 10:
                jdbcUrl = "jdbc:derby://localhost/[database]";
                jdbcDriver = "org.apache.derby.jdbc.ClientDriver";
                break;
            case 11:
                jdbcUrl = "jdbc:h2:tcp://localhost/~/[database]";
                jdbcDriver = "org.h2.Driver";
                break;
        }
        return new String[]{jdbcUrl, jdbcDriver};
    }

    /**
     * 开始生成代码
     *
     * @param e
     * @throws Exception
     */
    private void generator(MouseEvent e) throws Exception {
        generatorBut.setEnabled(false);
        String basepackage = setting.getBasepackage();
        String namespace = setting.getNamespace();
        String outRoot = setting.getOutRoot();
        String tableRemovePrefixes = setting.getTableRemovePrefixes();
        int dbType = setting.getDbType();
        String jdbcUrl = setting.getJdbcUrl();
        String jdbcDriver = setting.getJdbcDriver();
        String jdbcSchema = setting.getJdbcSchema();
        String jdbcCatalog = setting.getJdbcCatalog();
        String jdbcUsername = setting.getJdbcUsername();
        String jdbcPassword = setting.getJdbcPassword();
        String table = setting.getTable();
        String template = setting.getTemplate();

        if (basepackage == null || "".equals(basepackage)) {
            new Dialog("请输入基本包名").jd.setVisible(true);
            generatorBut.setEnabled(true);
            return;
        }
        if (outRoot == null || "".equals(outRoot)) {
            new Dialog("请输入输出目录").jd.setVisible(true);
            generatorBut.setEnabled(true);
            return;
        }
        if (jdbcUrl == null || "".equals(jdbcUrl)) {
            new Dialog("请输入数据源:URL").jd.setVisible(true);
            generatorBut.setEnabled(true);
            return;
        }
        if (jdbcDriver == null || "".equals(jdbcDriver)) {
            new Dialog("请输入数据源:驱动").jd.setVisible(true);
            generatorBut.setEnabled(true);
            return;
        }
        if (dbType == 2) {
            if (jdbcSchema == null || "".equals(jdbcSchema)) {
                new Dialog("请输入数据源:schema").jd.setVisible(true);
                generatorBut.setEnabled(true);
                return;
            }
            if (jdbcCatalog == null || "".equals(jdbcCatalog)) {
                new Dialog("请输入数据源:catalog").jd.setVisible(true);
                generatorBut.setEnabled(true);
                return;
            }
        }
        if (jdbcUsername == null || "".equals(jdbcUsername)) {
            new Dialog("请输入数据源:用户名").jd.setVisible(true);
            generatorBut.setEnabled(true);
            return;
        }
        if (jdbcPassword == null || "".equals(jdbcPassword)) {
            new Dialog("请输入数据源:密码").jd.setVisible(true);
            generatorBut.setEnabled(true);
            return;
        }
        if (template == null || "".equals(template)) {
            new Dialog("请输入模板名称").jd.setVisible(true);
            generatorBut.setEnabled(true);
            return;
        }

        if (basepackage != null && !"".equals(basepackage)) {
            GeneratorProperties.setProperty("basepackage", basepackage);
            ConfigProperties.setProperty("basepackage", basepackage);
        }
        if (namespace != null && !"".equals(namespace)) {
            GeneratorProperties.setProperty("namespace", namespace);
            ConfigProperties.setProperty("namespace", namespace);
        }
        if (outRoot != null && !"".equals(outRoot)) {
            GeneratorProperties.setProperty("outRoot", outRoot);
            ConfigProperties.setProperty("outRoot", outRoot);
        }
        if (tableRemovePrefixes != null && !"".equals(tableRemovePrefixes)) {
            GeneratorProperties.setProperty("tableRemovePrefixes", tableRemovePrefixes);
            ConfigProperties.setProperty("tableRemovePrefixes", tableRemovePrefixes);
        }
        if (jdbcUrl != null && !"".equals(jdbcUrl)) {
            GeneratorProperties.setProperty("jdbc_url", jdbcUrl);
            ConfigProperties.setProperty("jdbc_url", jdbcUrl);
        }
        if (jdbcDriver != null && !"".equals(jdbcDriver)) {
            GeneratorProperties.setProperty("jdbc_driver", jdbcDriver);
            ConfigProperties.setProperty("jdbc_driver", jdbcDriver);
        }
        if (jdbcSchema != null && !"".equals(jdbcSchema)) {
            GeneratorProperties.setProperty("jdbc_schema", jdbcSchema);
            ConfigProperties.setProperty("jdbc_schema", jdbcSchema);
        }
        if (jdbcCatalog != null && !"".equals(jdbcCatalog)) {
            GeneratorProperties.setProperty("jdbc_catalog", jdbcCatalog);
            ConfigProperties.setProperty("jdbc_catalog", jdbcCatalog);
        }
        if (jdbcDriver != null && !"".equals(jdbcDriver)) {
            GeneratorProperties.setProperty("jdbc_driver", jdbcDriver);
            ConfigProperties.setProperty("jdbc_driver", jdbcDriver);
        }
        if (jdbcUsername != null && !"".equals(jdbcUsername)) {
            GeneratorProperties.setProperty("jdbc_username", jdbcUsername);
            ConfigProperties.setProperty("jdbc_username", jdbcUsername);
        }
        if (jdbcPassword != null && !"".equals(jdbcPassword)) {
            GeneratorProperties.setProperty("jdbc_password", jdbcPassword);
            ConfigProperties.setProperty("jdbc_password", jdbcPassword);
        }
        if (table != null && !"".equals(table)) {
            GeneratorProperties.setProperty("table", table);
            ConfigProperties.setProperty("table", table);
        }
        if (template != null && !"".equals(template)) {
            GeneratorProperties.setProperty("template", template);
            ConfigProperties.setProperty("template", template);
        }

        try {
            StringBuffer sb = new StringBuffer();
            sb.append("\n");
            sb.append("***************************************************************\n");
            sb.append("* * 开始生成代码 * *\n");
            sb.append("* * " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " * *\n");
            sb.append("***************************************************************\n");
            if (logFileCreate) {
                System.out.println(sb.toString());
                refreshLog();
            } else {
                logTextArea.append(sb.toString());
                logTextArea.paintImmediately(logTextArea.getX(), logTextArea.getY(), logTextArea.getWidth(), logTextArea.getHeight());
            }

            GeneratorFacade g = new GeneratorFacade();
            g.getGenerator().setTemplateRootDir(templateFolder + File.separator + template);
            g.deleteOutRootDir(); // 删除生成器的输出目录
            if (table != null && !"".equals(table)) {
                g.generateByTable(table.split(",")); // 根据输入的数据表生成
            } else {
                g.generateByAllTable();    // 生成所有数据表代码
            }

            refreshLog();

            sb = new StringBuffer();
            sb.append("\n");
            sb.append("***************************************************************\n");
            sb.append("* * 生成代码完成 * *\n");
            sb.append("* * " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " * *\n");
            sb.append("***************************************************************\n");
            if (logFileCreate) {
                System.out.println(sb.toString());
                refreshLog();
            } else {
                logTextArea.append(sb.toString());
                logTextArea.paintImmediately(logTextArea.getX(), logTextArea.getY(), logTextArea.getWidth(), logTextArea.getHeight());
            }

            //new Dialog("代码生成已完成").jd.setVisible(true);
            //打开文件夹
            Runtime.getRuntime().exec("cmd.exe /c start " + GeneratorProperties.getRequiredProperty("outRoot"));
        } catch (Exception e1) {
            outputException(e1);
        } finally {
            generatorBut.setEnabled(true);
            // 更新用户配置文件
            ConfigProperties.updateConfigFile();
        }
    }

    class Dialog {
        JDialog jd = new JDialog();

        Dialog(String msg) {
            try {
                jd.setSize(300, 160);
                jd.setTitle("提示");
                jd.setModal(true);
                Container c2 = jd.getContentPane();
                c2.setLayout(null);
                jd.setLocation(screenWidth / 2 - jd.getWidth() / 2, screenHeight / 2 - jd.getHeight() / 2);
                JLabel jl = new JLabel(msg);
                jl.setBounds(20, -20, 250, 100);
                JButton jbb = new JButton("确定");
                jbb.setBounds(120, 70, 60, 30);
                c2.add(jl);
                c2.add(jbb);
                jbb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jd.dispose();
                    }
                });
                jd.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            } catch (Exception e) {
                outputException(e);
            }
        }
    }

    private void settingsChange(JTextField jtf, int index) {
        String temp = jtf.getText();
        switch (index) {
            case 1:
                setting.setBasepackage(temp);
                break;
            case 2:
                setting.setNamespace(temp);
                break;
            case 3:
                setting.setOutRoot(temp);
                break;
            case 4:
                setting.setTableRemovePrefixes(temp);
                break;
            case 6:
                setting.setJdbcUrl(temp);
                break;
            case 7:
                setting.setJdbcDriver(temp);
                break;
            case 8:
                setting.setJdbcSchema(temp);
                break;
            case 9:
                setting.setJdbcCatalog(temp);
                break;
            case 10:
                setting.setJdbcUsername(temp);
                break;
            case 11:
                setting.setJdbcPassword(temp);
                break;
            case 12:
                setting.setTable(temp);
                break;
            case 13:
                setting.setTemplate(temp);
                break;
        }
    }

}

class TextFocusListener implements FocusListener {
    String info;
    JTextField jtf;
    int index;
    Settings settings;

    public TextFocusListener(String info, JTextField jtf, int index, Settings settings) {
        this.info = info;
        this.jtf = jtf;
        this.index = index;
        this.settings = settings;
    }

    @Override
    public void focusGained(FocusEvent e) { // 获得焦点的时候,清空提示文字
        String temp = jtf.getText();
        if (temp.equals(info)) {
            //jtf.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) { // 失去焦点的时候,判断如果为空,就显示提示文字
        String temp = jtf.getText();
        if (temp.equals("")) {
            //jtf.setText(info);
        }
        switch (index) {
            case 1:
                settings.setBasepackage(temp);
                break;
            case 2:
                settings.setNamespace(temp);
                break;
            case 3:
                settings.setOutRoot(temp);
                break;
            case 4:
                settings.setTableRemovePrefixes(temp);
                break;
            case 6:
                settings.setJdbcUrl(temp);
                break;
            case 7:
                settings.setJdbcDriver(temp);
                break;
            case 8:
                settings.setJdbcSchema(temp);
                break;
            case 9:
                settings.setJdbcCatalog(temp);
                break;
            case 10:
                settings.setJdbcUsername(temp);
                break;
            case 11:
                settings.setJdbcPassword(temp);
                break;
            case 12:
                settings.setTable(temp);
                break;
            case 13:
                settings.setTemplate(temp);
                break;
        }
    }

}