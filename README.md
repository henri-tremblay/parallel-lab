parallel-lab
============

Laboratory to demonstrate different way to do parallel programming.

## VM Configuration ##

Performance tests have to be done on two distinct machines. The script `launch.sh` in scripts/ is for the gatling machine. This machine control the Tomcat machine over ssh.

Executes on the gatling machine to connect directly without password : 
`ssh-keygen -t rsa`
`ssh-copy-id user@gatling_machine`