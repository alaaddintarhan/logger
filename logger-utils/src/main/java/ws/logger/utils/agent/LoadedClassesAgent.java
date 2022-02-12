package ws.logger.utils.agent;


import java.lang.instrument.Instrumentation;
import java.util.Arrays;

public class LoadedClassesAgent {

    private static Instrumentation instrumentation;

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        LoadedClassesAgent.instrumentation = instrumentation;

        print("SYSTEM");
        print("BOOTSTRAP");
        print("BOOTSTRAP");
    }


    public static Class<?>[] listLoadedClasses(String classLoaderType) {
        return instrumentation.getInitiatedClasses(
                getClassLoader(classLoaderType));
    }

    private static ClassLoader getClassLoader(String classLoaderType) {
        ClassLoader classLoader = null;
        switch (classLoaderType) {
            case "SYSTEM":
                classLoader = ClassLoader.getSystemClassLoader();
                break;
            case "EXTENSION":
                classLoader = ClassLoader.getSystemClassLoader().getParent();
                break;
            case "BOOTSTRAP":
                break;
            default:
                break;
        }
        return classLoader;
    }

        public static void print(String classLoaderType) {
            System.out.println(classLoaderType + " ClassLoader : ");
            Class<?>[] classes = listLoadedClasses(classLoaderType);
            Arrays.asList(classes)
                    .forEach(clazz -> System.out.println(clazz.getCanonicalName()));
    }
}
