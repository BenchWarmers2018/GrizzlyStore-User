#!/usr/bin/env bash
servicename = grizzlystore-user

echo 'Deployment in progress'
pwd && cd target
sudo systemctl stop $servicename.service || true

if [ -f $servicename ] ; then
    sudo rm /etc/init.d/$servicename || true
fi

sudo ln -s *.jar /etc/init.d/$servicename
sudo systemctl start $servicename.service
