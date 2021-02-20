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
        git push
    else
        echo "git not changed"
    fi
}

cd basic
checkGitStatus

cd ../common
checkGitStatus

cd ../buildSrc
checkGitStatus

exit -1