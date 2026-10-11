# Bazel
* https://bazel.build/
* https://github.com/bazelbuild/bazel

> a fast, scalable, multi-language and extensible build system
>
> {Fast, Correct} - Choose two
>
> Build and test software of any size, quickly and reliably.
> - **Speed up your builds and tests**: Bazel rebuilds only what is necessary. With advanced local and distributed caching, optimized dependency analysis and parallel execution, you get fast and incremental builds.
> - **One tool, multiple languages**: Build and test Java, C++, Android, iOS, Go, and a wide variety of other language platforms. Bazel runs on Windows, macOS, and Linux.
> - **Scalable**: Bazel helps you scale your organization, codebase, and continuous integration solution. It handles codebases of any size, in multiple repositories or a huge monorepo.
> - **Extensible to your needs**: Easily add support for new languages and platforms with Bazel's familiar extension language. Share and re-use language rules written by the growing Bazel community.


# Build system basics
* https://bazel.build/basics

- **Task-Based Build Systems/基于任务的构建系统**: This page discusses task-based build systems (such as Make, Maven, and Gradle) and some of their challenges.
- **Artifact-Based Build Systems/基于制品的构建系统**: This page discusses artifact-based build systems in response to the pain points of task-based build systems.
- **Distributed Builds/分布式构建**: This page covers distributed builds, or builds that are executed outside of your local machine. This requires more robust infrastructure to share resources and build results (and is where the true wizardry happens!)
- **Dependency Management/依赖管理**: This page covers some complications of dependencies at a large scale and strategies to counteract those complications.

# Concepts
* https://bazel.build/concepts/build-ref

- **Repositories/仓库**: Bazel builds software from source code organized in directory trees called repositories.
  - A repo is a directory tree with a boundary marker file at its root; such a boundary marker file could be `MODULE.bazel`, `REPO.bazel`, or in legacy contexts, `WORKSPACE` or `WORKSPACE.bazel`.
  - The repo in which the current Bazel command is being run is called the **main repo/主仓库**. Other, (external) repos are defined by **repo rules/仓库规则**.
- **Workspace/工作空间**: A defined set of repositories comprises the workspace. A workspace is the environment shared by all Bazel commands run from the same main repo. It encompasses the *main repo* and the *set of all defined external repos*.
- **Packages/包**: Source files in repositories are organized in a nested hierarchy of packages, where each package is a directory that contains a set of related source files and one `BUILD` file. 
  - A package is a collection of related files and a specification of how they can be used to **produce output artifacts**.
  - A package is defined as a directory containing a `BUILD` file named either `BUILD` or `BUILD.bazel`. A package includes all files in its directory, plus all subdirectories beneath it, except those which themselves contain a `BUILD` file.
- **Targets/目标**: A package is a container of targets, which are defined in the package's `BUILD` file. Most targets are one of two principal kinds, **files/文件** and **rules/规则**.
  - Files are further divided into two kinds. **Source files/源文件** are usually written by the efforts of people, and checked in to the repository. **Generated files/生成的文件**, sometimes called derived files or output files, are not checked in, but are generated from source files.
  - Each rule instance specifies **the relationship between a set of input and a set of output files/输入文件和输出文件之间的关系**. The inputs to a rule may be source files, but they also may be the outputs of other rules.
- **Labels/标签**: A label is an identifier for a target.
  - target name: `package-name:target-name`
  - package name: `//package-name:target-name`
```python, starlark
# [repo name]//[package name]:[target name]

# canonical repo name: @@myrepo
@@myrepo//my/app/main:app_binary
# apparent repo name: @myrepo
@myrepo//my/app/main:app_binary
# refer to the same repo: ommit repo name
//my/app/main:app_binary

# un-qualified package name
my/app/main
# fully-qualified package name
@@myrepo//my/app/main

# un-qualified target name
app_binary
```
- `BUILD` files: The `BUILD` file specifies what software outputs can be built from the source.
  - `BUILD` files declare targets by invoking rules.
  - `BUILD` files are evaluated using an imperative language, **Starlark**.
  - Bazel extensions are files ending in `.bzl`.
  - Types of build rules: `*_binary`, `*_test`, `*_library`.
- **Dependencies/依赖**
  - A target A depends upon a target B if B is needed by A at build or execution time. The **depends upon relation** induces a **Directed Acyclic Graph (DAG)** over targets, and it is called a dependency graph.
  - A target's **direct dependencies** are those other targets reachable by a path of length 1 in the dependency graph. A target's **transitive dependencies** are those targets upon which it depends via a path of any length through the graph.
- **Visibility/可见性**
  - Both types of visibility help other developers distinguish between your library's public API and its implementation details, and help enforce structure as your workspace grows. You can also use visibility when deprecating a public API to allow current users while denying new ones.
  - **Target visibility/目标可见性**: Target visibility controls who may depend on your target — that is, who may use your target's label inside an attribute such as `deps`. A target will fail to build during the analysis phase if it violates the visibility of one of its dependencies.
  - **Load visibility/加载可见性**: Load visibility controls whether a `.bzl` file may be loaded from other `BUILD` or `.bzl` files outside the current package.
- **Platforms/平台**
  - Bazel can build and test code on a variety of hardware, operating systems, and system configurations, using many different versions of build tools such as linkers and compilers. To help manage this complexity, Bazel has a concept of constraints and platforms. 
  - A **constraint/约束** is a dimension in which build or production environments may differ, such as CPU architecture, the presence or absence of a GPU, or the version of a system-installed compiler. 
  - A **platform** is a named collection of choices for these constraints, representing the particular resources that are available in some environment.
  - Modeling the environment as a platform helps Bazel to **automatically select the appropriate toolchains for build actions**. 
  - Platforms can also be used in combination with the `config_setting` rule to write *configurable attributes*.
- **Hermeticity/气密性**: When given the same input source code and product configuration, a **hermetic build system** always returns the same output by isolating the build from changes to the host system.

# Rules
* https://bazel.build/rules

# User guide

* `.bazelrc`: https://bazel.build/run/bazelrc
* Output Directory Layout: https://bazel.build/remote/output-directories
  * `--output_base`, `--output_user_root`: `alias bazel='bazelisk.exe --output_user_root=/d/software/Bazelisk/output/bazel --output_base=/d/software/Bazelisk/output'`

# Reference

* BUILD Encyclopedia of Functions: https://bazel.build/reference/be/overview
* Test encyclopedia: https://bazel.build/reference/test-encyclopedia
  * `bazel test`
* Command-Line Reference: https://bazel.build/reference/command-line-reference
```shell
bazel build
bazel run
bazel query
bazel clean

bazel info
bazel shutdown
```
* Query Reference: https://bazel.build/query/language
  * `bazel query`

# Extending
## Platforms/平台
* https://bazel.build/extending/platforms

Bazel recognizes three roles that a platform may serve:
- **Host/主机平台** - the platform on which Bazel itself runs.
- **Execution/执行平台** - a platform on which build tools execute build actions to produce intermediate and final outputs.
- **Target/目标平台** - a platform on which a final output resides and executes.

Bazel supports the following build scenarios regarding platforms:
- **Single-platform builds (default)/单平台构建** - host, execution, and target platforms are the same. For example, building a Linux executable on Ubuntu running on an Intel x64 CPU.
- **Cross-compilation builds/交叉编译构建** - host and execution platforms are the same, but the target platform is different. For example, building an iOS app on macOS running on a MacBook Pro.
- **Multi-platform builds/多平台构建** - host, execution, and target platforms are all different.

## Toolchains/工具链
* https://bazel.build/extending/toolchains

the **toolchain framework/工具链框架**, which is a way for rule authors to decouple their rule logic from platform-based selection of tools.

Essentially, you declare that your rule has an abstract dependency on some member of a family of targets (a **toolchain type/工具链类型**), and Bazel automatically resolves this to a particular target (a **toolchain/工具链**) based on the applicable platform constraints. Neither the rule author nor the target author need know the complete set of available platforms and toolchains.

# See Also
* [Awesome Bazel](https://awesomebazel.com/): A curated list of Bazel rules, tooling and resources.

| Language/Platform                 |
| --------------------------------- |
| Amazon web services (AWS)         |
| Android                           |
| AppImage                          |
| Apple (iOS, macOS, tvOS, watchOS) |
| ANTLR                             |
| ARM Mbed OS                       |
| Bison                             |
| Blender                           |
| BOSH                              |
| Brotli                            |
| C++                               |
| C#                                |
| Clojure                           |
| Closure                           |
| CSS                               |
| CMake                             |
| CocoaPods                         |
| CODEOWNERS                        |
| D                                 |
| Dart                              |
| Databricks                        |
| Docker                            |
| ebook (pdf, epub, mobi)           |
| ECS                               |
| Elm                               |
| Emacs Lisp (elisp)                |
| Emscripten                        |
| Erlang                            |
| Flex                              |
| Go                                |
| Graal                             |
| Grafana                           |
| Groovy                            |
| GWT                               |
| Haskell                           |
| Helm (Kubernetes)                 |
| Heroku                            |
| Homebrew                          |
| Hugo                              |
| Idris                             |
| ISPC                              |
| Java                              |
| JFlex                             |
| Kotlin                            |
| Kubernetes                        |
| LaTeX                             |
| LLVM toolchain                    |
| Lua                               |
| M4                                |
| Manifest of build artifacts       |
| Maven                             |
| Microsoft Azure                   |
| NativeScript                      |
| Nixpkgs                           |
| Node.js / JavaScript              |
| OCaml                             |
| OCI Containers                    |
| OpenAPI/Swagger                   |
| Packaging (RPM/DEB)               |
| Pandoc                            |
| PHP                               |
| Prometheus                        |
| Protobuf                          |
| Purescript                        |
| Python                            |
| Ragel                             |
| R                                 |
| ReasonML, BuckleScript            |
| Ruby                              |
| Rust                              |
| Sass                              |
| Scala                             |
| Shell                             |
| SonarQube                         |
| Spring                            |
| Swift                             |
| Terraform                         |
| Twirl                             |
| TypeScript                        |
| Verilog                           |
| Web (HTML, CSS, JS, assets)       |
| YAML                              |
| Zig                               |


* [Bazelisk](https://github.com/bazelbuild/bazelisk/blob/master/README.md): A user-friendly launcher for Bazel.
  * `~/.bazeliskrc`
