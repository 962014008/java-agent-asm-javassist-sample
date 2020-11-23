package javaassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * 计算方法调用前后的耗时
 */
public class ElapsedTimeCalculator {
    public static byte[] addElapsedTimeLogic(String className, String methodName, String agentName) throws Exception {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get(className);
        // 如果class is frozen（冻结class）
        if (cc.isFrozen()) {
            cc.defrost();
        }
        CtMethod m = cc.getDeclaredMethod(methodName);
        m.addLocalVariable("elapsedTime", CtClass.longType);
        m.insertBefore("elapsedTime = System.currentTimeMillis();");
        m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;System.out.println(\"" + agentName + " Method Executed in ms: \" + elapsedTime);}");
        // 不能使用detach，否则多个javaagent只有最后一个生效
//                        cc.detach();
        return cc.toBytecode();
    }
}
