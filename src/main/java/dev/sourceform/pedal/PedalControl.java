package dev.sourceform.pedal;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.lwjgl.glfw.GLFW;
import java.io.*;
import java.nio.FloatBuffer;
import java.nio.file.*;
import java.util.Properties;

public final class PedalControl implements ClientModInitializer {
    private static Properties config = new Properties();
    private static boolean left, right;
    public void onInitializeClient() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("pedal-control.properties");
        config.setProperty("enabled", "false");
        config.setProperty("deviceNameContains", "G29");
        config.setProperty("leftAxis", "4");
        config.setProperty("rightAxis", "2");
        config.setProperty("pressedBelow", "-0.5");
        try {
            Files.createDirectories(path.getParent());
            if (Files.exists(path)) { try (InputStream in = Files.newInputStream(path)) { config.load(in); } }
            else { try (OutputStream out = Files.newOutputStream(path)) { config.store(out, "Pedal Control: calibrate axes before enabling"); } }
        } catch (IOException e) { System.err.println("Pedal Control config: " + e); }
    }
    public static boolean[] read() {
        left = right = false;
        if (!Boolean.parseBoolean(config.getProperty("enabled"))) return new boolean[]{false,false};
        try {
            for (int id = GLFW.GLFW_JOYSTICK_1; id <= GLFW.GLFW_JOYSTICK_LAST; id++) {
                if (!GLFW.glfwJoystickPresent(id)) continue;
                String name = GLFW.glfwGetJoystickName(id);
                if (name == null || !name.toLowerCase(java.util.Locale.ROOT).contains(config.getProperty("deviceNameContains").toLowerCase(java.util.Locale.ROOT))) continue;
                FloatBuffer axes = GLFW.glfwGetJoystickAxes(id);
                int l = Integer.parseInt(config.getProperty("leftAxis")), r = Integer.parseInt(config.getProperty("rightAxis"));
                float threshold = Float.parseFloat(config.getProperty("pressedBelow"));
                if (axes != null && l >= 0 && r >= 0 && l < axes.limit() && r < axes.limit() && Float.isFinite(threshold)) {
                    left = axes.get(l) < threshold; right = axes.get(r) < threshold;
                }
                break;
            }
        } catch (RuntimeException e) { return new boolean[]{false,false}; }
        return new boolean[]{left,right};
    }
}
