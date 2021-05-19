package cn.lxw.agent;

import java.lang.instrument.Instrumentation;

public class FirstAgent {
    // javac FirstAgent.java
    // jar cvmf FirstAgent.MF FirstAgent.jar FirstAgent.class
    public static void agentmain(String args, Instrumentation inst) throws Exception
    {
        System.out.println("agentmain in version FirstAgent:" + args);
    }

    public static void premain(String args, Instrumentation inst) throws Exception
    {
        System.out.println("premain in version FirstAgent:" + args);
    }
}
