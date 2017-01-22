#!/bin/bash

ideaVersion="14.0.2"

if [ ! -d ./idea-IC ]; then
    # Get our IDEA dependency
    if [ -f ~/Tools/ideaIC-${ideaVersion}.tar.gz ];
    then
        cp ~/Tools/ideaIC-${ideaVersion}.tar.gz .
    else
        wget http://download-cf.jetbrains.com/idea/ideaIC-${ideaVersion}.tar.gz
    fi

    # Unzip IDEA
    tar zxf ideaIC-${ideaVersion}.tar.gz
    rm -rf ideaIC-${ideaVersion}.tar.gz

    # Move the versioned IDEA folder to a known location
    ideaPath=$(find . -name 'idea-IC*' | head -n 1)
    mv ${ideaPath} ./idea-IC
fi