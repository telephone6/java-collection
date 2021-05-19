package cn.lxw;

import cn.lxw.util.MixUtil;
import com.sun.tools.attach.*;

import javax.management.ObjectInstance;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 功能描述: <br>
 * 〈show some usage with attach-technology〉
 * @Param:
 * @Return:
 * @Author: luoxw
 * @Date: 2021/5/19 13:59
 */
public class AttachMainApp {

    private static final String TEST_PROCESS_NAME = "TestProcess";

    /**
     * 功能描述: <br>
     * 〈if you want to run it and you don't see the README.md file under project,I think u should take a view better.
     * Or you will be confused about this project.〉
     * @Param: [args]
     * @Return: void
     * @Author: luoxw
     * @Date: 2021/5/19 15:01
     */
    public static void main(String[] args) throws Exception {
        // #1 get process info
        String pid = getPid(TEST_PROCESS_NAME);
        // #2 attach jvm agent
        String address = getAddress(pid);
        // #3 get jmx info
        printMemGCInfo(address);
        // #4 attach our agent
        attachOurAgent(pid);
    }

    private static String getPid(String processName){
        // #1 print all process info
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        MixUtil.printTip("print all process info");
        for (VirtualMachineDescriptor vmd : list) {
            System.out.println("pid:" + vmd.id() + ":" + vmd.displayName());
            if(vmd.displayName().contains(processName)){
                return vmd.id();
            }
        }
        return null;
    }

    private static String getAddress(String pid) throws IOException, AttachNotSupportedException,AgentLoadException,AgentInitializationException {
        // #2 atach
        VirtualMachine virtualmachine = VirtualMachine.attach(pid);
        // 2.1 find jvm agent
        String javaHome = virtualmachine.getSystemProperties().getProperty("java.home");
        String agentPath = javaHome + File.separator + "jre" + File.separator + "lib" + File.separator + "management-agent.jar";
        File file = new File(agentPath);
        if (!file.exists()) {
            agentPath = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
            file = new File(agentPath);
            if (!file.exists()) {
                throw new IOException("Management agent not found");
            }
        }
        agentPath = file.getCanonicalPath();
        // 2.2 load jvm agent
        try {
            virtualmachine.loadAgent(agentPath, "com.sun.management.jmxremote");
        } catch (AgentLoadException e) {
            throw new AgentLoadException();
        } catch (AgentInitializationException agentinitializationexception) {
            throw new AgentInitializationException();
        }
        // 2.3 get some info
        Properties properties = virtualmachine.getAgentProperties();
        Set<Object> keySet = properties.keySet();
        MixUtil.printTip("print all properties");
        for (Object key:keySet) {
            System.out.println("key:" + key + ",value:" + properties.get(key));
        }
        // 2.4 get jmx address
        String address = (String) properties.get("com.sun.management.jmxremote.localConnectorAddress");
        MixUtil.printTip("print jmx address");
        System.out.println(address);
        // 2.5 detach jvm agent
        virtualmachine.detach();
        return address;
    }

    private static void printMemGCInfo(String address) throws IOException {
        // #3 JMX info
        // 3.1 connect JMX
        JMXServiceURL url = new JMXServiceURL(address);
        JMXConnector connector = JMXConnectorFactory.connect(url);
        System.out.println(connector.getMBeanServerConnection().getMBeanCount());
        Set<ObjectInstance> objectInstances = connector.getMBeanServerConnection().queryMBeans(null, null);
        // 3.2 print info which get from JMX
        MixUtil.printTip("print objectInstances");
        for (ObjectInstance obj :objectInstances
        ) {
            System.out.println(obj.getObjectName().getCanonicalName());
        }
        MixUtil.printTip("print memory&GC info");
        MixUtil.printMemGCInfo();
    }

    private static void attachOurAgent(String pid) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        // #4 attach process
        // 4.1 find the agent jar position
        StringBuilder sb = new StringBuilder(System.getProperty("user.dir"));
        sb.append(File.separator).append("java")
                .append(File.separator).append("attach")
                .append(File.separator).append("src")
                .append(File.separator).append("main")
                .append(File.separator).append("java")
                .append(File.separator).append("cn")
                .append(File.separator).append("lxw")
                .append(File.separator).append("agent")
                .append(File.separator);

        // 4.2 attach FirstAgent
        String agentJarPath = sb.toString()+"FirstAgent.jar";
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(agentJarPath, "This is Args to the FistAgent.");
        // 4.3 detach FirstAgent
        vm.detach();

        // 4.4 attach SecondAgent
        agentJarPath = sb.toString()+"SecondAgent.jar";
        vm = VirtualMachine.attach(pid);
        vm.loadAgent(agentJarPath, "This is Args to the SecondAgent.");
        // 4.5 detach SecondAgent
        vm.detach();
    }
}
