package agent;

import timer.ElapsedTimeCalculator;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent2 {

    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中，并被同一个System ClassLoader装载，被统一的安全策略(security policy)和上下文(context)管理
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("====Agent2 premain方法执行1====");

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) {

                if ("stuff/Stuff".equals(s)) {
                    try {
                        // ASM Code
//                        ClassReader reader = new ClassReader(bytes);
//                        ClassWriter writer = new ClassWriter(reader, 0);
//                        ClassPrinter visitor = new ClassPrinter(writer);
//                        reader.accept(visitor, 0);
//                        return writer.toByteArray();

                        // Javassist
                        ElapsedTimeCalculator.addElapsedTimeLogic("stuff.Stuff", "stuff2", "Javassist Agent2 first");
                        return ElapsedTimeCalculator.addElapsedTimeLogic("stuff.Stuff", "stuff2", "Javassist Agent2 second");
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
        System.out.println("====Agent2 premain方法执行2====");
        System.out.println(agentOps);
    }

}

