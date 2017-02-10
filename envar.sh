#!/bin/bash

FILE=$BITBUCKET_CLONE_DIR/gradle.properties

# If there is no 'gradle.properties' file, create one.
if [ ! -f $FILE ]; then
    touch $FILE
fi

echo >> $FILE
echo >> $FILE
echo "# Start of Bitbucket Pipelines Environment Variable" >> $FILE
echo "nexusUsername=$NEXUS_USERNAME" >> $FILE
echo "nexusPassword=$NEXUS_PASSWORD" >> $FILE
echo "REPOSITORY_URL=$REPOSITORY_URL" >> $FILE
echo "SNAPSHOT_REPOSITORY_URL=$SNAPSHOT_REPOSITORY_URL" >> $FILE
echo "POM_GROUP=$POM_GROUP" >> $FILE
echo "# End of Bitbucket Pipelines Environment Variable" >> $FILE
echo >> $FILE