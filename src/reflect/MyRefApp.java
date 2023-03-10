package reflect;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyRefApp {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();

        // 1. 컴퍼넌트 스캔
        String packageName = "reflect";
        String packagePath = "./src/" + packageName.replace(".", "/");

        List<String> classNames = new ArrayList<>();

        File packageDirectory = new File(packagePath);

        if (packageDirectory.exists() && packageDirectory.isDirectory()) {
            getClassNamesFromDirectory(packageDirectory, packageName, classNames);
        }

        for (String className : classNames) {
            System.out.println("클래스 목록 : " + className);
        }

        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);

                Object instance = clazz.newInstance();

                if (clazz.isAnnotationPresent(Controller.class)) {
                    Method[] methods = clazz.getDeclaredMethods();
                    for (int i = 0; i < methods.length; i++) {
                        Method mt = methods[i];

                        Annotation annos = mt.getAnnotation(RequestMapping.class);
                        if (annos instanceof RequestMapping) {
                            RequestMapping rm = (RequestMapping) annos;

                            if (rm.uri().equals(path)) {
                                mt.invoke(instance);
                            }
                        }
                    }
                }

            } catch (Exception e) {

            }
        }

    }

    private static void getClassNamesFromDirectory(File directory, String packageName, List<String> classNames) {
        File[] files = directory.listFiles();
        if (files != null) {

            for (File file : files) {
                if (file.isDirectory()) {
                    String subPackageName = packageName + "." + file.getName();
                    getClassNamesFromDirectory(file, subPackageName, classNames);

                } else if (file.isFile() && file.getName().endsWith(".java")) {
                    String fileName = file.getName();
                    String className = packageName + "." + fileName.substring(0, fileName.lastIndexOf('.'));
                    classNames.add(className);
                }
            }
        }
    }
}