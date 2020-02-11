## Auto PDF Mark
### Description
A simple java-based tool that could generate the PDF bookmarks from the named link in the file. If a word is linked from other section in the file, it is probably a header. The headers are now used as bookmarks, which enable user to move quickly in any PDF reader.


### Prerequisite
|Dependency|Scope|Version|
|:-:|:-:|:-:|
|JRE|Run|7+|
|JDK|Build|7+|
|jcdp|Included in Fat Jar|v3.0.4|
|jansi|Included in Fat Jar|v1.18|
|pdfbox|Included in Fat Jar|v2.0.1|


### Execution
#### Run
`$ java -jar auto-pdf-mark.jar [input.pdf] [output.pdf]`


#### Build - Linux
1. `$ ./gradlew shadowJar`
2. `$ cp cp build/libs/auto-pdf-mark-1.0-all.jar auto-pdf-mark.jar`


#### Build - Windows
1. `$ gradlew.bat shadowJar`
2. `$ cp cp build\libs\auto-pdf-mark-1.0-all.jar auto-pdf-mark.jar`


### Demonstrate
![Imgur](https://i.imgur.com/ZqK0rxB.gif)

