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