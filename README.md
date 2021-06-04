# java-collection
requirement: 
    jdk: 1.8+
    apache-maven: 3.0+
1.attach
**where**：java/attach
**what**：
    show how to attach the process which is running,and we can enhance the byte code for some special check or function.
**how**：
    1.Open Terminal
    2.Change into the directory: 
        java-collection/java/attach/src/main/java/cn/lxw/agent
    3.Input the following command:
        javac FirstAgent.java
        jar cvmf FirstAgent.MF FirstAgent.jar FirstAgent.class
        javac SecondAgent.java
        jar cvmf SecondAgent.MF SecondAgent.jar SecondAgent.class
    4.Run the Class "TestProcess" which in [java-collection/java/attach/src/main/java];
    5.Run the Class "AttachMainApp" which in [java-collection/java/attach/src/main/java];
    6.Then you can know it better by viewing code and console.
    
2.mysql-compare
**where**：database/mysql-compare
**what**：
    show the differences between two databases which is kind of MySQL.
**how**：
    1.Edit the config of cn.lxw.mysql.MainApp.java
        private final static String A_HOST = "192.168.0.64:3306";
        private final static String A_DBNAME = "rcs_info";
        private final static String A_USERNAME = "rcs_user";
        private final static String A_USERPWD = "rcs@202103291104.";
    
        private final static String B_HOST = "192.168.0.64:3306";
        private final static String B_DBNAME = "rcs_info_test";
        private final static String B_USERNAME = "rcs_user_test";
        private final static String B_USERPWD = "rcs@202103291104.";
    2.Then run the method which is named "main(String[] args)" in cn.lxw.mysql.MainApp.java.