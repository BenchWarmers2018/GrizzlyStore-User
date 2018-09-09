#!/usr/bin/env bash

echo 'Deployment in process'
pwd && cd target
sudo systemctl stop grizzly-user.service || true
sudo rm /etc/init.d/grizzly-user || true
sudo ln -s grizzlystore-user-0.0.1-SNAPSHOT.jar /etc/init.d/grizzly-user
sudo systemctl start grizzly-user.service
