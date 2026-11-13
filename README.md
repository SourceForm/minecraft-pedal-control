# Minecraft Pedal Control

A Fabric client mod for using Logitech G29 pedals to move in Minecraft Java 26.2, including while Minecraft is in the background.

## Status

The prototype built with Gradle 9.8.2, Java 25, Fabric Loom 1.17.21 and Fabric Loader 0.19.3. The G29 mappings below worked in the user's test. The source now sets these defaults and removes the temporary diagnostic logging; this cleanup needs a new build. No compiled release has been published.

## Controls

| Pedal | Zero-based GLFW axis | Action |
| --- | --- | --- |
| Accelerator | 1 | Move right (D) |
| Clutch | 3 | Move left (A) |

The mod applies movement in Minecraft; it does not send keyboard strokes to Windows. A value below `-0.5` counts as pressed. The user's G29 reported approximately +1 released and -1 pressed; other setups may differ.

## Build and installation

With Java 25 and Gradle available, run from the project directory:

```powershell
& "C:\Tools\Gradle\gradle-9.8.2\bin\gradle.bat" build
```

Place `build/libs/minecraft-pedal-control-0.1.0.jar` in the Minecraft 26.2 Fabric instance's `mods` folder. Do not use the sources JAR.

On first launch the mod creates `config/pedal-control.properties` in the instance directory. Close Minecraft, set `enabled=true` and restart. Existing configuration values override defaults: use `leftAxis=3`, `rightAxis=1`, and `pressedBelow=-0.5` for the tested G29. An old `diagnostics` entry is ignored and can be removed.

## Limitations

Axis mappings and polarity may differ on other setups. Disconnect and menu behavior have not been independently verified. There is no in-game calibration screen or enable toggle. Test changes in a safe world.
