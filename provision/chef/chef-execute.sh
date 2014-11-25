#!/bin/bash

source $(dirname $0)/chef-vars.sh

## If Chef is already installed and does not match the expected Chef version, do not proceed with changes to the system.
if [ `command -v chef-solo` ] && [ `chef-solo -v|awk '{print $2}'` != "$CHEF_VERSION" ]; then echo "`chef-solo -v` detected. This script requires Chef $CHEF_VERSION"; exit -1; fi

NODEDIR="solo-nodes"

NODE_JSON="$1"
BERKSFILE="$2"
CHEF_EXTRA="$3"

if [ -z $NODE_JSON ]; then NODE_JSON='base.json'; fi
if [ -z $BERKSFILE ]; then BERKSFILE='kaleido'; fi
if [ -z $NODE_JSON ]; then NODE_JSON='base.json'; fi

# NODE_JSON could be a full path, but if not, we'll try to find it within the
# repo, inside ${NODEDIR}
# if we can't locate at all, we fail.

if [ ! -f "$NODE_JSON" ]; then
   if [ -f "/vagrant/$NODE_JSON" ]; then
      echo "Found $NODE_JSON in /vagrant"
      NODE_JSON="/vagrant/$NODE_JSON";
   elif [ -f "$CHEF_SITE_ROOT/${NODEDIR}/$NODE_JSON" ]; then
      echo "Found $NODE_JSON in $CHEF_SITE_ROOT/${NODEDIR}/"
      NODE_JSON="$CHEF_SITE_ROOT/${NODEDIR}/$NODE_JSON";
   else
      echo "Couldn't locate '$NODE_JSON'"
      exit 1
   fi;
fi;

# if we have a berks executable, execute it else, we fail.
if [ -x ${BERKS} ]; then
    echo "Running Berkshelf"
    # Need switch to the appropriate directory for our selected Berksfile
    pushd "${CHEF_BERKSFILES}/${BERKSFILE}"

    # Now execute Berks install
    BERKS_VENDOR="${BERKS} vendor $CHEF_RUNTIME_COOKBOOKS"
    BERKS_INSTALL="${BERKS} install"
    echo ${BERKS_VENDOR}
    echo ${BERKS_INSTALL}
    ${BERKS_VENDOR}
    ${BERKS_INSTALL}
    berksret=$?
    echo "Berks run exited with status code '${berksret}'"
    if [ ${berksret} -ne 0 ]; then
        # Get us back to where we started
        popd
        exit ${berksret}
    fi
    # Get us back to where we started
    popd
else
    echo "Failed to execute berks (wanted to execute '${BERKS}' but it failed -x test)"
    exit 2
fi
echo ""

# if we have a chef-solo executable, execute it and exit with its exit status
# or else, we fail.
if [ -x ${CHEFSOLO} ]; then
    echo "Running Chef"
   ${CHEFSOLO} -c ${CHEFDIR}/solo.rb -j "$NODE_JSON" $CHEF_EXTRA
   chefret=$?
   echo "Chef Solo run complete.  Exiting with its status code '${chefret}'"
   exit ${chefret}
else
   echo "Failed to execute chef-solo (wanted to execute '${CHEFSOLO}' but it failed -x test)"
   exit 2
fi