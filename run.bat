@echo off

IF %1.==. GOTO no_definiation_file_name
java -classpath bin;. LaserExitPointInMirrorMaze %1
GOTO End1

:no_definiation_file_name
	echo Please provide the maze definition filename
	echo run maze_definition.txt
GOTO End1

:End1