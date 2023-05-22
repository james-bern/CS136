(clear && javac *.java -Xlint:-unchecked && java $(basename "$1" .java) && rm *.class) || (cls && javac *.java -Xlint:-unchecked && java %~n1 && del *.class)
