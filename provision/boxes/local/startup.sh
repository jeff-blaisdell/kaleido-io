#!/bin/bash

echo 'Running chef...'
pushd /opt/chef-solo/provisioning/chef
sudo ./chef-setup.sh
sudo ./chef-execute.sh /opt/chef-solo/provisioning/chef/solo-nodes/local/kaleido-stack.json kaleido
echo 'Finished chef...'
popd