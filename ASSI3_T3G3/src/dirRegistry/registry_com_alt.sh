CODEBASE="file://"$1"/dirRegistry/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     -cp "../genclass.jar:."\
     serverSide.main.ServerRegisterRemoteObject 22001 localhost 22000
