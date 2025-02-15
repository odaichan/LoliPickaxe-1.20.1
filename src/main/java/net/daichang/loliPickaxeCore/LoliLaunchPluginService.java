package net.daichang.loliPickaxeCore;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.EnumSet;

import static org.objectweb.asm.Opcodes.*;

public class LoliLaunchPluginService implements ILaunchPluginService {
    public static void CoreLogger(String msg){
        System.out.println("[LoliPickaxe Core]：" + msg);
    }

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
            if (!classNode.name.contains("net/daichang")) {
                if (classNode.superName.equals("net/minecraft/world/entity/LivingEntity")) {
                    if (methodNode.name.equals("m_21223_")) {
                        classNode.methods.remove("m_21223_");
                        CoreLogger("find getHealth on" + classNode.name);
                    }
                }
            }
        }
        return writer;
    }

    private static void rMethod(MethodInsnNode call, String name, String desc) {
        call.setOpcode(Opcodes.INVOKESTATIC);
        call.owner = owner;
        call.name = name;
        call.desc = desc;
    }
    private static void rField(MethodNode method, FieldInsnNode field, String name, String desc) {
        method.instructions.set(field, new MethodInsnNode(Opcodes.INVOKESTATIC, owner, name, desc, false));
    }


    public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return EnumSet.of(Phase.BEFORE);
    }

    public static void FuckMethod(MethodNode mn) {
        Type type = Type.getReturnType(mn.desc);
        mn.tryCatchBlocks.clear();
        mn.instructions.clear();
        mn.localVariables.clear();
        switch (type.getSort()) {
            case Type.VOID: {
                mn.instructions.add(new InsnNode(RETURN));
                break;
            }
            case Type.SHORT:
            case Type.CHAR:
            case Type.BYTE:
            case Type.INT:
            case Type.BOOLEAN: {
                mn.instructions.add(new InsnNode(ICONST_0));
                mn.instructions.add(new InsnNode(IRETURN));
                break;
            }
            case Type.FLOAT: {
                mn.instructions.add(new InsnNode(FCONST_0));
                mn.instructions.add(new InsnNode(FRETURN));
                break;
            }
            case Type.LONG: {
                mn.instructions.add(new InsnNode(LCONST_0));
                mn.instructions.add(new InsnNode(LRETURN));
                break;
            }
            case Type.DOUBLE: {
                mn.instructions.add(new InsnNode(DCONST_0));
                mn.instructions.add(new InsnNode(DRETURN));
                break;
            }

            case Type.OBJECT: {
                mn.instructions.add(new InsnNode(ACONST_NULL));
                mn.instructions.add(new InsnNode(ARETURN));
                break;
            }
            default: {
                throw new IllegalStateException("The is  ??");
            }
        }
    }
}
