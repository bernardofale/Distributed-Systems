CODEBASE="file://"$1"/dirOrdinaryThieves/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -cp "../genclass.jar:."\
     clientSide.main.ClientOrdinaryThief localhost 22000 logger
