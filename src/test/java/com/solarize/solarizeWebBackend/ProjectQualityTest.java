package com.solarize.solarizeWebBackend;

import jakarta.persistence.Entity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectQualityTest {
    private final Set<Class<?>> classes = new HashSet<>();
    private final Set<String> packageNames = new HashSet<>();

    @BeforeAll
    public void getClasses() throws Exception {
        String BASE_PACKAGE = "com.solarize.solarizeWebBackend";
        String path = BASE_PACKAGE.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);

        Set<Class<?>> allClasses = new HashSet<>();
        Set<String> allPackageNames = new HashSet<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();

            if (resource.getProtocol().equals("file")) {
                File directory = new File(resource.getFile());
                if (directory.exists()) {
                    allClasses.addAll(findClasses(directory, BASE_PACKAGE));
                    findAllPackages(directory, BASE_PACKAGE, allPackageNames);
                }
            }
        }

        assertFalse(allClasses.isEmpty(), "Nenhuma classe foi encontrada no pacote " + BASE_PACKAGE);

        this.classes.addAll(allClasses);
        this.packageNames.addAll(allPackageNames);
    }

    private Set<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        assertNotNull(files);
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = Class.forName(className);
                classes.add(clazz);
            }
        }
        return classes;
    }

    private void findAllPackages(File directory, String currentPackage, Set<String> packageNames) {
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                String subPackage = currentPackage + "." + file.getName();
                if (!subPackage.equals("com.solarize.solarize_web_backend")) {
                    packageNames.add(subPackage);
                }
                findAllPackages(file, subPackage, packageNames);
            }
        }
    }

    private boolean sourceFileContains(Class<?> clazz, String textToFind) {
        String className = clazz.getName();
        if (className.contains("$")) {
            className = className.substring(0, className.indexOf('$'));
        }

        String relativePath = className.replace('.', '/') + ".java";

        Path sourcePath = Paths.get("src", "main", "java").resolve(relativePath);

        if (!Files.exists(sourcePath)) {
            System.err.println("Aviso: Não foi possível encontrar o arquivo-fonte para a classe: " + sourcePath);
            return false;
        }

        try {
            String fileContent = Files.readString(sourcePath);

            return fileContent.contains(textToFind)
                    && !fileContent.contains("// " + textToFind)
                    && !fileContent.contains("//" + textToFind);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Test
    @DisplayName("O nome de classes devem ser PascalCase")
    void verifyClassName() {
        classes.forEach(c -> {
            String className = c.getSimpleName();
            assertTrue(Character.isUpperCase(className.charAt(0)), "A primeira letra da classe deve ser maiúscula: " + c.getName());
            assertFalse(className.contains("_"), "O nome da classe não pode conter '_': " + c.getName());
        });
    }


    @Test
    @DisplayName("O nome de atributos devem ser camelCase")
    void verifyAttNames() {
        classes.forEach(c -> {
            Arrays.stream(c.getDeclaredFields()).toList().forEach(f -> {
                String fieldName = f.getName();

                if(!Modifier.isFinal(f.getModifiers())) {
                    assertTrue(Character.isLowerCase(fieldName.charAt(0)), "O nome do atributo deve começar com letra minuscula: " +
                            c.getSimpleName() + "." + f.getName());

                    assertFalse(fieldName.contains("_"), "O nome de um atributo não pode conter '_': " +
                            c.getSimpleName() + "." + f.getName());
                }
            });
        });
    }


    @Test
    @DisplayName("Nome de pacotes devem ser camelCase")
    void isValidPackageName() {
        packageNames.forEach(packageName -> {
            assertNotNull(packageName);

            String[] parts = packageName.split("\\.");
            for (String part : parts) {
                assertFalse(part.isEmpty(), "Parte do pacote não pode estar vazia no pacote: " + packageName);
                assertTrue(Character.isJavaIdentifierStart(part.charAt(0)), "O primeiro caractere da parte do pacote deve ser um início válido de identificador Java. Parte: '" + part + "' no pacote: " + packageName);
                assertFalse(part.contains("_"), "O nome do pacote não deve ter '_', escreva em camelCase." + packageName);
                assertTrue(Character.isLowerCase(part.charAt(0)), "A primeira letra do pacote não deve ser maiuscula" + packageName);

                for (int i = 1; i < part.length(); i++) {
                    assertTrue(Character.isJavaIdentifierPart(part.charAt(i)), "O caractere na posição " + i + " da parte do pacote deve ser uma parte válida de identificador Java. Parte: '" + part + "' no pacote: " + packageName);
                }
            }
        });
    }

    @Test
    @DisplayName("Classes controllers devem finalizar com Controller")
    void hasControllerOnName() {
        classes.forEach(c -> {
            String simpleName = c.getSimpleName();

            if(c.isAnnotationPresent(RestController.class)) {
                assertTrue(simpleName.endsWith("Controller"), "Uma classe controller, deve finalizar com 'Controller', ex: TesteController: " + c.getName());
            }

            assertFalse(simpleName.endsWith("Controller") && !c.isAnnotationPresent(RestController.class), "Uma classe com o nome finalizado com 'Controller', precisa estar anotada com @RestController: " + c.getName());

        });
    }

    @Test
    @DisplayName("Classes services precisam finalizar com Service")
    void hasServiceOnName() {

        classes.forEach(c -> {
            String simpleName = c.getSimpleName();

            if(c.isAnnotationPresent(Service.class)) {
                assertTrue(simpleName.endsWith("Service"), "Uma classe service, deve finalizar com 'Service', ex: TesteService: " + c.getName());
            }

            assertFalse(simpleName.endsWith("Service") && !c.isAnnotationPresent(Service.class), "Uma classe com o nome finalizado com 'Service', precisa estar anotada com @Service: " + c.getName());
        });
    }

    @Test
    @DisplayName("Injeção de dependencias")
    void dependendyInjection() {
        classes.forEach(c -> {
            if(c.isAnnotationPresent(Service.class) || c.isAnnotationPresent(RestController.class)) {
                assertFalse(sourceFileContains(c, "public " + c.getSimpleName()), "Gere o construtor com @RequiredArgsConstructor: " + c.getName());

                boolean hasFinalField = false;
                for(Field field : c.getDeclaredFields()) {
                    if(Modifier.isFinal(field.getModifiers())) {
                        assertFalse(field.isAnnotationPresent(Autowired.class), "@Autowired não é recomendado, gere um construtor com @RequiredArgsConstructor: " + c.getName());
                        hasFinalField = true;
                    }
                }

                if(hasFinalField) {
                    assertTrue(sourceFileContains(c, "@RequiredArgsConstructor"), "Injeção de dependencias devem utilizar a anotação @RequiredArgsConstructor para criar o construtor: " + c.getName());
                }
            }
        });
    }

    @Test
    @DisplayName("Entity não pode conter @Data")
    void entityNotContainsDataAnnotation() {
        classes.forEach(c -> {
            if(c.isAnnotationPresent(Entity.class)) {
                assertFalse(sourceFileContains(c, "@Data"), c.getName());
            }
        });
    }

    @Test
    @DisplayName("Entity deve conter @Getter e @Setter")
    void entityContainsGetterAndSetter() {
        classes.forEach(c -> {
            if(c.isAnnotationPresent(Entity.class)) {
                assertTrue(sourceFileContains(c, "@Getter"), "Use lombok: " + c.getName());
                assertTrue(sourceFileContains(c, "@Setter"), "Use lombok: " + c.getName());
            }
        });
    }

    @Test
    @DisplayName("DTOs devem ter @Getter, @Setter e @Builder")
    void DTOContainsGetterSetterAndBuild() {
        classes.forEach(c -> {
            if(c.getName().contains("dtos")) {
                assertTrue(c.getSimpleName().endsWith("Dto") || c.getSimpleName().endsWith("Builder"), "O nome de todos os arquivos dentro de uma pasta /dtos precisa finalizar com 'Dto': " + c.getName());

                assertTrue(sourceFileContains(c, "@Getter"), "A classe não possui @Getter: " + c.getName());
                assertTrue(sourceFileContains(c, "@Setter"), "A classe não possúi @Setter: " + c.getName());
                assertTrue(sourceFileContains(c, "@Builder"), "A clase não possui @Builder " + c.getName());
            }

            assertFalse(c.getSimpleName().endsWith("Dto") && !c.getName().contains("dtos"), "Um DTO precisa estar dentro de uma pasta apropriada. Mova seu DTO para uma pasta /dtos: " + c.getName());
        });
    }


    @Test
    @DisplayName("Nomes de interfaces de repository precisam finalizar com Repository")
    void repositoryEndsWithRepository() {
        classes.forEach(c -> {
            if(c.isInterface() && JpaRepository.class.isAssignableFrom(c)) {
                assertTrue(c.getSimpleName().endsWith("Repository"), c.getName());
            }
        });
    }

    @Test
    @DisplayName("Uma controller não pode retornar uma entity")
    void controllerCannotReturnEntity() {
        classes.forEach(c -> {
            if (c.isAnnotationPresent(RestController.class)) {
                Method[] methods = c.getDeclaredMethods();

                for (Method method : methods) {
                    if (
                            method.isAnnotationPresent(GetMapping.class) ||
                                    method.isAnnotationPresent(PostMapping.class) ||
                                    method.isAnnotationPresent(DeleteMapping.class) ||
                                    method.isAnnotationPresent(PutMapping.class) ||
                                    method.isAnnotationPresent(PatchMapping.class)
                    ) {
                        Class<?> returnType = method.getReturnType();

                        assertFalse(returnType.isAnnotationPresent(Entity.class),
                                "Controller não pode retornar uma Entity diretamente: " + c.getName() + "." + method.getName());

                        Type genericReturnType = method.getGenericReturnType();
                        if (genericReturnType instanceof ParameterizedType paramType) {
                            Type rawType = paramType.getRawType();
                            if (rawType == ResponseEntity.class) {
                                Type[] typeArgs = paramType.getActualTypeArguments();
                                if (typeArgs.length == 1) {
                                    Type innerType = typeArgs[0];
                                    if (innerType instanceof Class<?> innerClass) {
                                        assertFalse(innerClass.isAnnotationPresent(Entity.class),
                                                "Controller não pode retornar uma Entity dentro de ResponseEntity: "
                                                        + c.getName() + "." + method.getName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Test
    @DisplayName("Todo endpoint deve retornar um ResponseEntity")
    void controllerMustReturnResponseEntity() {
        classes.forEach(c -> {
            if (c.isAnnotationPresent(RestController.class)) {
                Method[] methods = c.getDeclaredMethods();

                for (Method method : methods) {
                    if (
                            method.isAnnotationPresent(GetMapping.class) ||
                                    method.isAnnotationPresent(PostMapping.class) ||
                                    method.isAnnotationPresent(DeleteMapping.class) ||
                                    method.isAnnotationPresent(PutMapping.class) ||
                                    method.isAnnotationPresent(PatchMapping.class)
                    ) {
                        Class<?> returnType = method.getReturnType();

                        assertTrue(ResponseEntity.class.isAssignableFrom(returnType),
                                "Controller precisa retornar um ResponseEntity: " + c.getName() + "." + method.getName());
                    }
                }
            }
        });
    }


    @Test
    @DisplayName("Modulos devem estar dentro de modules ou shared.")
    void packageInModulesOrShared() {
        classes.forEach(c -> {
            String className = c.getName();

            if(!className.contains("modules") && !className.contains("shared") && !className.endsWith("Test") && !className.endsWith("Tests")) {
                if(!c.getSimpleName().equals("SolarizeWebBackendApplication")) {
                    fail("Uma classe precisa estar inserida em um modulo do sistema ou um modulo compartilhado." +
                            "Mova para /shared ou /modules: " + className);
                }
            }
        });
    }

}
