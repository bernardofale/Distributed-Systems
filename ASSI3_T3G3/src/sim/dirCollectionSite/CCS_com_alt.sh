CODEBASE="file://"$1"/dirCollectionSite/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     -cp "../genclass.jar:."\
     serverSide.main.ServerMasterThiefCCS 22003 localhost 22000
