@echo off
javac -sourcepath ./src/main/java -d build src/main/java/ru/iandreyshev/spreadsheetEngine/SpreadsheetEngine.java
java -cp build/ ru.iandreyshev.spreadsheetEngine.SpreadsheetEngine