parallel-lab
============

Laboratory to demonstrate different way to do parallel programming.

### Launch ##

The project must be launched with the parameter `implementation` (see launch.sh). 
Available values are:
- naive
- akka
- mono
- executor
- pool


### VM Configuration ###

Performance tests have to be done on two distinct machines. The script `launch.sh` in scripts/ is for the gatling machine. This machine control the Tomcat machine over ssh.

Executes on the gatling machine to connect directly without password : 
`ssh-keygen -t rsa`
`ssh-copy-id user@gatling_machine`