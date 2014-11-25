#!/usr/bin/bash

RUBY_VERSION="1.9.1"
#CHEF_VERSION="11.12.2"
#CHEF_DK="chefdk_0.0.1-1_amd64.deb"
CHEF_VERSION="11.14.0.rc.2"
CHEF_DK="chefdk_0.2.0-2_amd64.deb"
CHEF_DK_URL="https://opscode-omnibus-packages.s3.amazonaws.com/ubuntu/12.04/x86_64/$CHEF_DK"

CHEFDIR="/opt/chef-solo"
CHEFDATA="/mnt/chef-solo"

CHEFSOLO="/usr/bin/chef-solo"
BERKS="/usr/bin/berks"

CHEF_SOLO_CONFIG="$CHEFDIR/solo.rb"
CHEF_RUNTIME_COOKBOOKS="$CHEFDATA/cookbooks"
CHEF_FILE_CACHE="$CHEFDATA/tmp"
CHEF_FILE_BACKUP="$CHEFDATA/backup"

CHEF_SITE_ROOT="$CHEFDIR/provisioning/chef"
CHEF_SITE_COOKBOOKS="$CHEF_SITE_ROOT/cookbooks"
CHEF_ROLES="$CHEF_SITE_ROOT/roles"
CHEF_DATABAGS="$CHEF_SITE_ROOT/data_bags"
CHEF_BERKSFILES="$CHEF_SITE_ROOT/berks"
