package agent;

import asm.ClassPrinterCaller;
import javaassist.ElapsedTimeCalculator;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent1 {

    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中，并被同一个System ClassLoader装载，被统一的安全策略(security policy)和上下文(context)管理
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("====Agent1 premain方法执行1====");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) {

                if ("stuff/Stuff".equals(s)) {
                    try {
                        // Javassist
                        ElapsedTimeCalculator.addElapsedTimeLogic("stuff.Stuff", "stuff1", "Javassist Agent1 first");
                        ElapsedTimeCalculator.addElapsedTimeLogic("stuff.Stuff", "stuff1", "Javassist Agent1 second");

                        // ASM Code
                        return ClassPrinterCaller.addPrintClassDefLogic(bytes);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                return null;
            }
        });
    }

    /**
     * 如果不存在 premain(String agentOps, Instrumentation inst)，则会执行 premain(String agentOps)
     */
    public static void premain(String agentOps) {
        System.out.println("====Agent1 premain方法执行2====");
        System.out.println(agentOps);
    }

}

