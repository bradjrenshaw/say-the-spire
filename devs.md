# For Developers

## Compilation

### Requirements

SayTheSpire requires both ModTheSpire and BaseMod in order to run. As a result it needs these jars to compile. Get their latest releases from their GitHub pages and place them in the lib foldre:

* [ModTheSpire](https://github.com/kiooeht/ModTheSpire)
* [BaseMod](https://github.com/daviscook477/BaseMod)

In order to use screen reader output, you need Output libraries for all supported operating systems.

#### Windows

For screen reader output on windows, the Tolk library is required. You can find Tolk [here](https://github.com/dkager/tolk). Clone this repository and compile the .jar. 

You need to set up the Tolk.jar as a maven dependency. To do so, run this command from the repository's root, substituting in the correct path for your Tolk.jar and leaving the other parameters unchanged:

mvn install:install-file -Dfile=path/to/Tolk.jar -DgroupId=com.davykager.tolk -DartifactId=Tolk -Dversion=unknown -Dpackaging=jar

If you want screen reader dlls to automatically extract at runtime, download the release from the Tolk github page (Latest build from AppVeyor). Paste the x86 and x64 directories in src/main/resources/tolk/, so you should have a directory structure resembling this.

* src/main/resources/
    * tolk/
        * x86
        * x64

Note that if you do not add these files to resources, you will have to manually place the correct dlls in your root SlayTheSpire game directory.

### Packaging and Setup

To compile the mod, run

mvn package

then copy mods/sayTheSpire.jar to your Slay the Spire mods directory.