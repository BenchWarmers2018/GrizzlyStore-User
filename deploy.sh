#!/usr/bin/env bash
echo 'Deployment in progress'
pwd && cd target

if (( $(ps -ef | grep -v grep | grep grizzlystore-user | wc -l) > 0)) 
	then
		echo "User service is running...attempting to stop service!"
		sudo systemctl stop grizzlystore-user.service || true
fi

if [ -f grizzlystore-user ] ; then
    sudo rm /etc/init.d/grizzlystore-user || true
fi

cp *.jar /opt/GrizzlyStoreMicroServices/GrizzlyStore-User.jar
sudo systemctl start grizzlystore-user.service