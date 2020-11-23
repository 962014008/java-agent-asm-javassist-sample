package asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * 调用asm增强代码，打印类的定义
 */
public class ClassPrinterCaller {
    public static byte[] addPrintClassDefLogic(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(reader, 0);
        ClassPrinter visitor = new ClassPrinter(writer);
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }
}
