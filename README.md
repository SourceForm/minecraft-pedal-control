# Minecraft Pedal Control

A prototype Fabric client mod for using Logitech G29 pedals to move in Minecraft Java 26.2, including the intended use of playing while Minecraft is in the background.

## Current status

The first prototype compiled successfully with Gradle 9.8.2, Java 25, Fabric Loom 1.17.21, and Fabric Loader 0.19.3. The axis defaults were changed afterward; that change still needs a fresh build. Controller input, axis numbering and polarity, focus behavior, in-game loading, and disconnect handling have not yet been verified with the G29.

The prototype source and build configuration are in this repository. A compiled JAR has not been published as a release.

## Intended controls

| Controller input | Minecraft action |
| --- | --- |
| Axis 2 | Move right, equivalent to D |
| Axis 4 | Move left, equivalent to A |

The prototype reads zero-based GLFW axis indices and applies movement in Minecraft; it does not send keyboard strokes to Windows. The numbers and pedal direction need calibration against the actual device. The prototype considers an axis pressed when its value is below `-0.5`.

## Build and installation

From the local prototype directory in PowerShell, with Java 25 and Gradle available:

```powershell
& "C:\Tools\Gradle\gradle-9.8.2\bin\gradle.bat" build
```

The resulting mod is `build/libs/minecraft-pedal-control-0.1.0.jar`; the similarly named sources JAR is not the mod to install. Put the mod JAR in the Minecraft 26.2 Fabric instance's `mods` directory.

On first launch, the mod creates `config/pedal-control.properties` in the instance directory. It is disabled by default. Close Minecraft, set `enabled=true`, and restart to test. Existing configuration files override changed defaults; set `leftAxis=4` and `rightAxis=2` there if necessary.

Test in a safe single-player world first. Incorrect axis indices or polarity can cause unintended or continuous movement. Release the pedals or close the world if movement behaves unexpectedly.

## Known limitations and next steps

- No calibration display or automatic axis detection yet; verify device name, axis indices, resting values and press direction.
- Verify that GLFW reads the G29 while Minecraft is unfocused and that Minecraft continues processing input without a pause screen.
- Verify actual movement, menu behavior, device disconnects and simultaneous input.
- Add configurable press/release thresholds, a convenient enable toggle and clearer status.
- Document a repeatable build and hardware test results.
