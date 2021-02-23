#!/bin/sh

echo 'pre-push hook:'

checkGitStatus() {
    pwd
    status=`git status|grep 'nothing to commit'`
    if [[ -z $status ]];
    then
        echo "git changed"
        git add .
        git commit -m "update submodule by pre-push hook"
    else
        echo "git not changed"
    fi
    git push
}

cd basic
checkGitStatus

cd ../common
checkGitStatus

cd ../buildSrc
checkGitStatus

cd ../buildscripts
checkGitStatus