package sayTheSpire.speech;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

public class TolkResourceHandler extends TolkHandler {

  public void disposeResources() {
    String resources[] = getResources();
    try {
      for (String resource : resources) {
        File file = new File(System.getProperty("user.dir") + "/" + resource);
        file.delete();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    System.out.println("speech.TolkResourceHandler: All files cleared.");
  }

  public Boolean loadResources() {
    try {
      String path = getPath();
      String resources[] = getResources();
      for (String resource : resources) {
        InputStream stream = getClass().getResourceAsStream(path + resource);
        if (stream == null) System.err.println("input stream for " + path + resource + " is null.");
        FileOutputStream outStream =
            new FileOutputStream(System.getProperty("user.dir") + "/" + resource);
        byte[] buffer = new byte[8192];
        int len;
        while ((len = stream.read(buffer)) != -1) {
          outStream.write(buffer, 0, len);
        }
        stream.close();
        outStream.close();
      }
    } catch (Exception e) {
      System.out.println("exception thrown");
      System.out.println(e.getMessage());
      e.printStackTrace();
      return false;
    }
    System.out.println("speech.TolkResourceHandler: all files written");
    return true;
  }

  public void unload() {
    super.unload();
    try {
      unloadNativeLibs();
    } catch (Throwable t) {
      System.out.println(t.getMessage());
      t.printStackTrace();
    }
  }

  public static String getPath() {
    String path = "/tolk/";
    if (!is64Bit()) {
      path += "x86";
    } else {
      path += "x64";
    }
    path += "/";
    return path;
  }

  public static String[] getResources() {
    if (!is64Bit()) {
      return new String[] {
        "Tolk.dll", "nvdaControllerClient32.dll", "SAAPI32.dll", "Tolk.exp", "Tolk.lib"
      };
    } else {
      return new String[] {
        "Tolk.dll", "nvdaControllerClient64.dll", "SAAPI64.dll", "Tolk.exp", "Tolk.lib"
      };
    }
  }

  public static Boolean is64Bit() {
    if (System.getProperty("os.name").contains("Windows")) {
      return System.getenv("ProgramFiles(x86)") != null;
    } else {
      return System.getProperty("os.arch").indexOf("64") != -1;
    }
  }

  public void unloadNativeLibs() throws Throwable {
    // Unfortunately this horrifying abomination is required in order to remove dll files after
    // unloading
    ClassLoader classLoader = this.getClass().getClassLoader();
    Field field = ClassLoader.class.getDeclaredField("nativeLibraries");
    field.setAccessible(true);
    Vector libs = (Vector) field.get(classLoader);
    for (Object o : libs) {
      Method finalize = o.getClass().getDeclaredMethod("finalize", new Class[0]);
      finalize.setAccessible(true);
      finalize.invoke(o, new Object[0]);
    }
  }
}
