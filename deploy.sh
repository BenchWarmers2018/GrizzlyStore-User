#!/usr/bin/env bash
servicename = grizzlystore-user

echo 'Deployment in process'
pwd && cd target
sudo systemctl stop $servicename || true
if [ -f $servicename] ; then
	sudo rm /etc/init.d/$servicename || true
sudo ln -s *.jar /etc/init.d/$servicename
sudo systemctl start $servicename
