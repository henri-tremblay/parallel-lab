
 #!/bin/bash 

launch () {

        echo 'Launching implementation : ' $1
        ssh root@192.168.225.129 'export CATALINA_OPTS="-Dimplementation='$1'";/root/apache/bin/catalina.sh start'
        sleep 10
        echo 'Loading gatling for the first time'
        /root/sources/gatling.sh > /tmp/gatling.log

        echo 'Rapport: '$1''
        /root/sources/gatling.sh | grep 'Please open the following file' | cut -d ':' -f2 

        echo 'Stopping remote tomcat'
        ssh root@192.168.225.129 '/root/apache/bin/shutdown.sh'
        sleep 10
}

launch naive;
launch akka;
launch mono;
launch executor;
launch pool;