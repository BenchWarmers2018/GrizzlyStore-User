#!/usr/bin/env bash

echo 'Deployment in process'
pwd && cd target
sudo systemctl stop grizzlystore-user.service || true
sudo rm /etc/init.d/grizzlystore-user || true
sudo ln -s *.jar /etc/init.d/grizzlystore-user
sudo systemctl start grizzlystore-user.service
