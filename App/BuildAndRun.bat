((clear && javac *.java && java ${$1%.*} && rm *.class) || (cls && javac *.java && java %~n1 && del *.class)) 
