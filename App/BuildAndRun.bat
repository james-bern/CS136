((clear && javac *.java && java $1 && rm *.class) || (cls && javac *.java && java %1 && del *.class)) 
