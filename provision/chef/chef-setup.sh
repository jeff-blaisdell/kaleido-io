#!/bin/bash

source chef-vars.sh

## Install Tools

apt-get update

#Install make
if [ ! `command -v make` ]; then
	apt-get install -y make
fi

#Install g++
if [ ! `command -v g++` ]; then
	apt-get install -y g++
fi

#Install curl
if [ ! `command -v curl` ]; then
	apt-get install -y curl
fi

#Install unzip
if [ ! `command -v unzip` ]; then
	apt-get install -y unzip
fi

#Install Ruby
apt-get install -y ruby$RUBY_VERSION ruby$RUBY_VERSION-dev
update-alternatives --set ruby /usr/bin/ruby$RUBY_VERSION
update-alternatives --set gem /usr/bin/gem$RUBY_VERSION

# Need to add an updated repo to get the latest version of chef
# (default RightScale repo doesn't have it)
#
gem source  --add https://rubygems.org/

#Install Chef
if [ ! `command -v chef` ]; then

	# Resolve Chef Development Kit package.
	if [ ! -f "$CHEF_DK" ]; then
		# Look for pre-downloaded Chef Development Kit in Bootstrap directory.
		if [ -f "/opt/vmbootstrap/chef/$CHEF_DK" ]; then
			cp -f "/opt/vmbootstrap/chef/$CHEF_DK" "$CHEF_DK"
		else
			curl -O $CHEF_DK_URL
		fi
	fi

	# Install Chef DK package.
	dpkg -i $CHEF_DK
fi

## Setup Solo Environment
#
mkdir -p $CHEFDIR
mkdir -p $CHEF_FILE_CACHE
mkdir -p $CHEF_FILE_BACKUP
rm -rf $CHEF_RUNTIME_COOKBOOKS

## FILE: solo.rb
cat > $CHEF_SOLO_CONFIG << EOF
file_cache_path '$CHEF_FILE_CACHE'
file_backup_path '$CHEF_FILE_BACKUP'
cookbook_path '$CHEF_RUNTIME_COOKBOOKS'
role_path '$CHEF_ROLES'
data_bag_path '$CHEF_DATABAGS'
EOF

chown root:root $CHEF_SOLO_CONFIG
chmod 644 $CHEF_SOLO_CONFIG