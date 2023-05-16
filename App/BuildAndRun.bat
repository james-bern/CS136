((clear && javac *.java && java $(basename -s .java $1) && rm *.class) || (cls && javac *.java && java %~n1 && del *.class)) 
