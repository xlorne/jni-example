# JNI Example Project

这是一个Java Native Interface (JNI) 的示例项目，演示了如何在Java中调用C++编写的本地方法。

## 项目简介

本项目展示了JNI的基本使用方法：
- Java端定义native方法
- C++端实现对应的本地方法
- 通过JNI桥接Java和C++代码
- 使用CMake构建C++动态库

## 项目结构

```
jni-example/
├── lib/                          # 编译后的动态库文件
│   └── libhello.dylib           # macOS动态库
├── lib-cpp/                      # C++源代码目录
│   ├── build.sh                 # C++项目构建脚本
│   ├── CMakeLists.txt           # CMake配置文件
│   ├── include/                 # 头文件目录
│   │   ├── com_codingapi_jni_HelloWord.h  # JNI生成的头文件
│   │   └── library.h            # 库函数头文件
│   └── src/                     # C++源代码
│       ├── HelloJNI.cpp         # JNI实现文件
│       ├── library.cpp          # 核心库函数实现
│       └── main.cpp             # C++测试程序
├── scripts/                     # 构建脚本
│   └── build.sh                # Java头文件生成脚本
├── src/                         # Java源代码
│   └── main/java/com/codingapi/jni/
│       ├── HelloWord.java       # 包含native方法的Java类
│       └── Main.java            # Java主程序
├── pom.xml                      # Maven配置文件
└── README.md                    # 项目说明文档
```

## 环境要求

- **Java**: JDK 17 或更高版本
- **C++编译器**: 支持C++17的编译器 (如GCC, Clang)
- **CMake**: 3.15 或更高版本
- **操作系统**: macOS (当前配置为macOS，其他系统需要相应调整)

## 构建步骤

### 1. 生成JNI头文件

首先需要从Java类生成JNI头文件：

```bash
cd scripts
./build.sh
```

这个脚本会执行：
```bash
javac -h ../lib-cpp/include ../src/main/java/com/codingapi/jni/HelloWord.java
```

### 2. 编译C++动态库

```bash
cd lib-cpp
./build.sh
```

这个脚本会：
- 创建build目录
- 运行CMake配置
- 编译生成动态库文件

### 3. 编译Java程序

```bash
# 编译Java源代码
javac -d target/classes src/main/java/com/codingapi/jni/*.java
```

## 运行程序

### 运行Java程序

```bash
# 设置库路径并运行
java -Djava.library.path=./lib -cp target/classes com.codingapi.jni.Main
```

### 运行C++测试程序

```bash
cd lib-cpp/build
./lib-cpp
```

## 代码说明

### Java端 (HelloWord.java)

```java
public class HelloWord {
    public native void hello();  // 声明native方法
    
    static {
        // 加载动态库
        String path = System.getProperty("user.dir");
        String libPath = path + "/lib/libhello.dylib";
        System.load(libPath);
    }
}
```

### C++端 (HelloJNI.cpp)

```cpp
#include "com_codingapi_jni_HelloWord.h"
#include "jni.h"
#include <iostream>

extern void hello();

// JNI函数实现
extern "C" JNIEXPORT void JNICALL Java_com_codingapi_jni_HelloWord_hello(JNIEnv *env, jobject obj) {
    hello();
}
```

### 核心库函数 (library.cpp)

```cpp
#include "library.h"
#include <iostream>

void hello() {
    std::cout << "Hello, World!" << std::endl;
}
```

## 关键概念

1. **JNI函数命名规则**: `Java_包名_类名_方法名`
2. **动态库加载**: 使用`System.load()`加载本地库
3. **头文件生成**: 使用`javac -h`自动生成JNI头文件
4. **CMake构建**: 使用CMake管理C++项目的构建过程

## 注意事项

- 确保动态库路径正确，当前配置为`./lib/libhello.dylib`
- 在不同操作系统上，动态库扩展名不同：
  - macOS: `.dylib`
  - Linux: `.so`
  - Windows: `.dll`
- 运行时需要正确设置`java.library.path`

## 扩展建议

- 添加参数传递示例
- 实现更复杂的数据类型交互
- 添加异常处理机制
- 支持多平台构建脚本

## 许可证

本项目仅供学习和参考使用。
