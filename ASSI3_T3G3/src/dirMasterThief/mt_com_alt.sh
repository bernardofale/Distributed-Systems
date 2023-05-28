CODEBASE="file://"$1"/dirMasterThief/"
java  -cp "../genclass.jar:."\
      -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.ClientMasterThief localhost 22000
