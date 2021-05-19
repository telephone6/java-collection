package cn.lxw.agent;

import java.lang.instrument.Instrumentation;

public class SecondAgent {
    // javac SecondAgent.java
    // jar cvmf SecondAgent.MF SecondAgent.jar SecondAgent.class
    public static void agentmain(String args, Instrumentation inst) throws Exception
    {
        System.out.println("agentmain in version SecondAgent:" + args);
    }

    public static void premain(String args, Instrumentation inst) throws Exception
    {
        System.out.println("premain in version SecondAgent:" + args);
    }
}
