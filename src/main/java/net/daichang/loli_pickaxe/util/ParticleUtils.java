package net.daichang.loli_pickaxe.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

import static java.lang.Math.*;
import static net.minecraft.util.Mth.square;

public class ParticleUtils {
    public static void Particle(Player player, Level level){
        int Particle1 = 7;
        double Particle = 1.0;

        for(double Angle = 0.0; Angle < 12.0; ++Angle) {
            SimpleParticleType type = new SimpleParticleType(true);
            double Rad = 360.0 * Angle / (double)Particle1 * Math.PI / 180.0;
            double X = player.getX() + sin(Rad) * Particle;
            double Z = player.getZ() + Math.cos(Rad) * Particle;
            level.addParticle(type, X, player.getY() + 2, Z, ((new Random()).nextDouble() - 1.0) * 0.08, ((new Random()).nextDouble() - 1.0) * 0.08, ((new Random()).nextDouble() - 1.0) * 0.08);
        }
    }
    public static void apple(Level level, Entity player) {
        double CENTER_X = player.getX();
        double CENTER_Y = player.getY() + 2;
        double CENTER_Z = player.getZ();
        double RADIUS = 5;
        for (double angleX = 0; angleX < 2 * Math.PI; angleX += 0.1) {
            for (double angleY = 0; angleY < Math.PI; angleY += 0.1) {
                double x = CENTER_X + RADIUS * sin(angleY) * Math.cos(angleX);
                double y = CENTER_Y + RADIUS * Math.cos(angleY);
                double z = CENTER_Z + RADIUS * sin(angleY) * sin(angleX);
                ParticleOptions options = ParticleTypes.ELECTRIC_SPARK;
                Vec3 position = new Vec3(x, y, z);
                level.addParticle(options,position.x,position.y,position.z,0,1,0);
            }
        }
    }

    public static void Particle1(Player player, Level level){
        int X = (int) player.getX();
        int y = (int) player.getY();
        int Z = (int) player.getZ();

        SimpleParticleType type = ParticleTypes.ELECTRIC_SPARK.getType();
        for (int i = 0; i < 360; ++i) {
            double qwq = Math.toRadians(i);
            double QWQ = Math.cos(qwq);
            double qwq2 = sin(qwq) + Z;
            level.addParticle(type,  QWQ * 1.5 + X , y + 0.1,  qwq2 * 1.5 +Z, 0, 1, 0);
        }
    }

    static int counter = 0;
    static double rs = 0;
    public static void Test(Entity player,Level level) {
        double X = player.getX();
        double Z = player.getZ();
        double y = player.getY() + 0.1D;
        double r = sqrt(12);
        SimpleParticleType type = ParticleTypes.ELECTRIC_SPARK.getType();

        //六芒星
        //二维空间内距离原点长度为r且角度为a的点p坐标是：r*(cosa,sina)


        double[] x1 = new double[6];
        double[] y1 = new double[6];

        for(int i = 0 ; i < 6; i++){
            double rad = (2*Math.PI/6)*i+rs;
            x1[i] = (r*cos(rad) + player.getX());
            y1[i] = (r*sin(rad) + player.getZ());
            //现场计算六芒星顶点
        }
        for(int i = 0 ; i < 6; i++){
            ParticleUtils.Drawline(0.05,x1[i], y, y1[i],x1[(i+8)%6],y,y1[(i+8)%6],type,level);
        }

        rs = rs+0.01;

//             double r = sqrt(12);
//             double x = r * Math.cos(a);
//             double z = r * sin(a);
//            ParticleUtils.Drawline(0.05F,X+3,y,Z+sqrt(3),X-3,y,Z+sqrt(3),type,level);
//            ParticleUtils.Drawline(0.05F,X,y,Z-sqrt(12),X-3,y,Z+sqrt(3),type,level);
//            ParticleUtils.Drawline(0.05F,X,y,Z-sqrt(12),X+3,y,Z+sqrt(3),type,level);

//            ParticleUtils.Drawline(0.05F,X-3,y,Z-sqrt(3),X+3,y,Z-sqrt(3),type,level);
//            ParticleUtils.Drawline(0.05F,X,y,Z+sqrt(12),X-3,y,Z-sqrt(3),type,level);
//            ParticleUtils.Drawline(0.05F,X,y,Z+sqrt(12),X+3,y,Z-sqrt(3),type,level);

    }
    public static void Particle2(Player player,Level level){
        //圆形阵法
                double X = player.getX();
                double Z = player.getZ();
                double y = player.getY() + 0.1D;
                SimpleParticleType type = ParticleTypes.ELECTRIC_SPARK.getType();
                for(int i = 0 ; i <= 360; i++){
                    double rad = i * 0.017453292519943295D;
                    double r1 = 0.5D,r2 = 3.0D;
            double x1 = r1 * Math.cos(rad) , x2 = r2 * Math.cos(rad);
            double z1 = r1 * sin(rad) , z2 = r2 * sin(rad);

            double tmp = 6D/ sqrt(2D);

            level.addParticle(type,X+x1,y,Z+z1,0,0,0);
            level.addParticle(type,X+x2,y,Z+z2,0,0,0);

            level.addParticle(type,X+x1,y,Z+z1+6,0,0,0);
            level.addParticle(type,X+x2,y,Z+z2+6,0,0,0);

            level.addParticle(type,X+x1,y,Z+z1-6,0,0,0);
            level.addParticle(type,X+x2,y,Z+z2-6,0,0,0);

            level.addParticle(type,X+x1+6,y,Z+z1,0,0,0);
            level.addParticle(type,X+x2+6,y,Z+z2,0,0,0);

            level.addParticle(type,X+x1-6,y,Z+z1,0,0,0);
            level.addParticle(type,X+x2-6,y,Z+z2,0,0,0);

            level.addParticle(type,X+x1+tmp,y,Z+z1+tmp,0,0,0);
            level.addParticle(type,X+x2+tmp,y,Z+z2+tmp,0,0,0);

            level.addParticle(type,X+x1-tmp,y,Z+z1-tmp,0,0,0);
            level.addParticle(type,X+x2-tmp,y,Z+z2-tmp,0,0,0);

            level.addParticle(type,X+x1+tmp,y,Z+z1-tmp,0,0,0);
            level.addParticle(type,X+x2+tmp,y,Z+z2-tmp,0,0,0);

            level.addParticle(type,X+x1-tmp,y,Z+z1+tmp,0,0,0);
            level.addParticle(type,X+x2-tmp,y,Z+z2+tmp,0,0,0);
        }

        for(double i = 0.5; i <= 3; i+=0.05D){
            for(int d = 0; d <=360 ; d+=60){
                double rad = (d+counter) * 0.017453292519943295;
                double rad1 = (d+counter-30) * 0.017453292519943295;
                double rad2 = (d+counter+30) * 0.017453292519943295;
                double x = i * Math.cos(rad),x1 = i * Math.cos(rad1);
                double z = i * sin(rad),z1 = i * Math.cos(rad2);
                double tmp = 6/ sqrt(2);
                level.addParticle(type,X+x,y,Z+z,0,0,0);
                level.addParticle(type,X+x+6,y,Z+z,0,0,0);
                level.addParticle(type,X+x-6,y,Z+z,0,0,0);
                level.addParticle(type,X+x,y,Z+z+6,0,0,0);
                level.addParticle(type,X+x,y,Z+z-6,0,0,0);
                level.addParticle(type,X+x+tmp,y,Z+z+tmp,0,0,0);
                level.addParticle(type,X+x-tmp,y,Z+z-tmp,0,0,0);
                level.addParticle(type,X+x+tmp,y,Z+z-tmp,0,0,0);
                level.addParticle(type,X+x-tmp,y,Z+z+tmp,0,0,0);
            }
        }
        counter++;
        if(counter >= 360){
            counter = 0;
        }
    }

    public static void addParticle3(Player player,Level level){
        double X = player.getX();
        double Z = player.getZ();
        double y = player.getY();
        SimpleParticleType type = ParticleTypes.AMBIENT_ENTITY_EFFECT.getType();
        for(int i = 0 ; i <= 360; i++){
            double rad = i * 0.017453292519943295;
            double r = 3.0D;
            double x = r * Math.cos(rad);
            double z = r * sin(rad);
            level.addParticle(type,X+x,y,Z+z,0,0,0);
            level.addParticle(type,X+x+3,y,Z+z+3,0,0,0);
            level.addParticle(type,X+x-3,y,Z+z-3,0,0,0);
            level.addParticle(type,X+x+3,y,Z+z-3,0,0,0);
            level.addParticle(type,X+x-3,y,Z+z+3,0,0,0);
        }
        for(double d = y ; d <= y+3; d+=0.05D){
            level.addParticle(type,X-5,d,Z-5,0,1.0,0);
            level.addParticle(type,X+5,d,Z+5,0,1.0,0);
            level.addParticle(type,X-5,d,Z+5,0,1.0,0);
            level.addParticle(type,X+5,d,Z-5,0,1.0,0);
        }
    }

    public static void Drawline(double interval,double tox, double toy, double toz, double X, double Y, double Z,SimpleParticleType type,Level level){
        //自动连接任意两点
        double deltax = tox-X, deltay = toy-Y, deltaz = toz-Z;
        double length = sqrt(square(deltax) + square(deltay) + square(deltaz));
        int amount = (int)(length/interval);
        for (int i = 0 ; i <= amount; i++) level.addParticle(type,X+deltax*i/amount, Y+deltay*i/amount, Z+deltaz*i/amount,0,0.05,0);
    }
}
