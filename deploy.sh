#!/usr/bin/env bash
echo 'Deployment in progress'
pwd && cd target
sudo systemctl stop grizzlystore-user.service || true

if [ -f grizzlystore-user ] ; then
    sudo rm /etc/init.d/grizzlystore-user || true
fi

sudo ln -s *.jar /etc/init.d/grizzlystore-user
sudo systemctl start grizzlystore-user.service
