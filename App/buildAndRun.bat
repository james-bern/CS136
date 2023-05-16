(clear && javac *.java && java $(basename "$1" .java) && rm *.class) || (cls && javac *.java && java %~n1 && del *.class)
