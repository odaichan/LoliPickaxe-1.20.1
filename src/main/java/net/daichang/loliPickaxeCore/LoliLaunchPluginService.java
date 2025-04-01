package net.daichang.loliPickaxeCore;

import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import sun.misc.Unsafe;

import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static org.objectweb.asm.Opcodes.*;

public class LoliLaunchPluginService implements ILaunchPluginService {
    public static void CoreLogger(String msg){
        System.out.println("[LoliPickaxe Core]：" + msg);
    }
    private static final String METHOD_OWNER="net/daichang/loli_pickaxe/util/CoreMethod";
    private static final VarHandle packageLookup;
    private static final VarHandle parentLoaders;
    private static final MethodHandle getClassBytes;
    private static final MethodHandle classNameToModuleName;
    private static final MethodHandle loadFromModule;
    private static final ModuleClassLoader targetClassLoader;
    private static final Map<String, byte[]> byteCache = new HashMap<>();

    public  static LoliLaunchPluginService loliLaunchPluginService = new LoliLaunchPluginService();
    private static final String owner = "net/daichang/loli_pickaxe/util/core/CoreMethod";
    @Override
    public String name() {
        return "Loli Pickaxe LaunchPluginService";
    }

    public boolean processClass(Phase phase, ClassNode classNode, Type classType) {
        return this.transform(classNode);
    }

    private  boolean transform(ClassNode classNode) {
        boolean writer = false;
        for (MethodNode methodNode : classNode.methods) {
            for (AbstractInsnNode abstractInsnNode : methodNode.instructions) {
                if (!classNode.name.startsWith("net/daichang/loli_pickaxe/") && !classNode.name.startsWith("net/minecraft/")) {
                    if (abstractInsnNode instanceof MethodInsnNode call) {
                        if (abstractInsnNode.getOpcode() == INVOKEVIRTUAL || abstractInsnNode.getOpcode() == INVOKEINTERFACE) {
                            if (("m_21223_".equals(call.name) || "getHealth".equals(call.name)) && "()F".equals(call.desc)) {
                                methodNode.instructions.set(call, new MethodInsnNode(INVOKESTATIC, METHOD_OWNER, "getHealth", "(Lnet/minecraft/world/entity/LivingEntity;)F"));
                                CoreLogger("Changed GetHealth Method " + classNode.name);
                                writer = true;
                            }
                            if (("m_6084_".equals(call.name) || "isAlive".equals(call.name)) && "()Z".equals(call.desc)) {
                                methodNode.instructions.set(call, new MethodInsnNode(INVOKESTATIC, METHOD_OWNER, "isAlive", "(Lnet/minecraft/world/entity/Entity;)Z"));
                                CoreLogger("Changed IsAlive Method " + classNode.name);
                                writer = true;
                            }
                            if (("m_21224_".equals(call.name) || "isDeadOrDying".equals(call.name)) && "()Z".equals(call.desc)) {
                                methodNode.instructions.set(call, new MethodInsnNode(INVOKESTATIC, METHOD_OWNER, "isDeadOrDying", "(Lnet/minecraft/world/entity/LivingEntity;)Z"));
                                CoreLogger("Changed IsDeadOrDying Method " + classNode.name);
                                writer = true;
                            }
                        }
                    }
                }
            }
        }
        return writer;
    }

    private static boolean isAssignableFrom(String current, String father) {
        try {
            while (true) {
                if (current.equals(father)) {
                    return true;
                } else if (current.equals("java/lang/Object")) {
                    return false;
                } else {
                    current = new ClassReader(getClassBytes(current)).getSuperName();
                }
            }
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ClassNotFoundException) {
                return false;
            } else {
                throw e;
            }
        }
    }

    private static byte[] getClassBytes(String aname) {
        byte[] bytes = byteCache.get(aname);

        if (bytes != null) {
            return bytes;
        }

        Throwable suppressed = null;
        String name = aname.replace('/', '.');

        try {
            String pname = name.substring(0, name.lastIndexOf('.'));
            if (((Map<String, ResolvedModule>) packageLookup.get(targetClassLoader)).containsKey(pname)) {
                bytes = (byte[]) loadFromModule.invoke(targetClassLoader, classNameToModuleName.invoke(targetClassLoader, name), (BiFunction<ModuleReader, ModuleReference, Object>) (reader, ref) -> {
                    try {
                        return getClassBytes.invoke(targetClassLoader, reader, ref, name);
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                });
            } else {
                Map<String, ClassLoader> parentLoadersMap = (Map<String, ClassLoader>) parentLoaders.get(targetClassLoader);
                if (parentLoadersMap.containsKey(pname)) {
                    try (InputStream is = parentLoadersMap.get(pname).getResourceAsStream(aname + ".class")) {
                        if (is != null) {
                            bytes = is.readAllBytes();
                        }
                    }
                }
            }
        } catch (Throwable e) {
            suppressed = e;
        }

        if (bytes == null || bytes.length == 0) {
            ClassNotFoundException e = new ClassNotFoundException(name);
            if (suppressed != null) e.addSuppressed(suppressed);
            throw new RuntimeException(e);
        }

        byteCache.put(name, bytes);

        return bytes;
    }

    public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return EnumSet.of(Phase.BEFORE);
    }

    static {
        Field lookupF;
        Unsafe unsafe;
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            lookupF = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");

            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        MethodHandles.Lookup lookup = (MethodHandles.Lookup) unsafe.getObject(unsafe.staticFieldBase(lookupF), unsafe.staticFieldOffset(lookupF));
        try {
            packageLookup = lookup.findVarHandle(ModuleClassLoader.class, "packageLookup", Map.class);
            parentLoaders = lookup.findVarHandle(ModuleClassLoader.class, "parentLoaders", Map.class);
            getClassBytes = lookup.findVirtual(ModuleClassLoader.class, "getClassBytes", MethodType.methodType(byte[].class, ModuleReader.class, ModuleReference.class, String.class));
            classNameToModuleName = lookup.findVirtual(ModuleClassLoader.class, "classNameToModuleName", MethodType.methodType(String.class, String.class));
            loadFromModule = lookup.findVirtual(ModuleClassLoader.class, "loadFromModule", MethodType.methodType(Object.class, String.class, BiFunction.class));
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        targetClassLoader = Thread.currentThread().getContextClassLoader() instanceof ModuleClassLoader moduleClassLoader
                ? moduleClassLoader
                : (ModuleClassLoader) Thread.getAllStackTraces().keySet().stream()
                .map(Thread::getContextClassLoader)
                .filter(cl -> cl instanceof ModuleClassLoader)
                .findAny()
                .orElseThrow();
    }
}
