# For Developers
If you are looking to work on adding additional functionality to the mod, this
section details how to compile the mod. if you are looking to localize the mod,
go [here](./localization.md).

## Compilation

### Mod Requirements
SayTheSpire requires the following mods in order to compile. I recommend you to subscribe to the mods on Steam and copy their latest jar files from their Steam workshop directory on your system to the lib folder in the root of this repository. The mods for Slay The Spire can be found in C:\Program Files (x86)\Steam\steamapps\workshop\content\646570. They are located within folder which are only named by numbers, so you'll have to browse through them to dig them up.

* [ModTheSpire](https://steamcommunity.com/sharedfiles/filedetails/?id=1605060445)
* [BaseMod](https://steamcommunity.com/workshop/filedetails/?id=1605833019)
* [Downfall](https://steamcommunity.com/sharedfiles/filedetails/?id=1610056683)

In addition to said mods SayTheSpire also requires the current desktop-1.0.jar that ships with Slay The Spire itself. Copy that one from your Steam\steamapps\common\Slay The Spire directory into the lib folder as well.

If the lib folder does not exist it is safe to create one.

### Setting up Screen Reader Libs
In order to use screen reader output, you need Output libraries for all
supported operating systems. These are required for the mod to compile.

#### Windows
For screen reader output on windows, the Tolk library is required. You can find
Tolk [here](https://github.com/dkager/tolk). Clone this repository and compile
the .jar. You'll need some kind of make processor, I recommend some Visual Studio environment (e.g. community edition) and running nmake like this:

```bash
nmake tolkjar
```

You need to set up the Tolk.jar as a maven dependency. To do so, run this
command from the repository's root, substituting in the correct path for your
Tolk.jar and leaving the other parameters unchanged:

```bash
mvn install:install-file -Dfile=path/to/Tolk.jar -DgroupId=com.davykager.tolk -DartifactId=Tolk -Dversion=unknown -Dpackaging=jar
```

If you want screen reader dlls to automatically extract at runtime, download the
release from some 3rd party GitHub release (thanks ndarilek): <https://github.com/ndarilek/tolk/releases/tag/refs%2Fheads%2Fmaster>

Paste the x86 and x64 directories in src/main/resources/tolk/, so you should have a directory
structure resembling this.

* src/main/resources/
    * tolk/
        * x86/
        * x64/

Note that if you do not add these files to resources, you will have to manually
place the correct dlls in your root SlayTheSpire game directory.

#### Linux
For screen reader output on Linux, speech dispatcher via the speechd library is used. Clone the [speechd github repository](https://github.com/brailcom/speechd-java) and use ant to compile it.

You will then need to set up the package as a Maven dependency. To do this, use this command, substituting in the correct path to speechd.jar while leaving the other parameters unchanged:

```bash
mvn install:install-file -Dfile=path/to/speechd.jar -DgroupId=speechd -DartifactId=speechd -Dversion=unknown -Dpackaging=jar
```

### Packaging and Setup
To compile the mod, run

```bash
mvn package
```

This should compile the mod and bundle all needed screenreader library files and other dependencies into the main .jar file. After that, copy target/sayTheSpire.jar to your Slay the Spire mods directory. If you're running SayTheSpire through the Steam Workshop already, Maven will have done so for you already.