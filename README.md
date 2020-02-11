## Auto PDF Mark
### Description
A simple java-based tool that could generate the PDF bookmarks from the named link in the file. If a word is linked from other section in the file, it is probably a header. The headers are now used as bookmarks, which enable user to move quickly from any PDF reader.


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


### Screenshots
![Imgur](https://i.imgur.com/HzHhijh.png)

![Github](https://user-images.githubusercontent.com/9023821/74156373-25e44400-4c51-11ea-8a4c-5b0b0f6ece05.PNG)

