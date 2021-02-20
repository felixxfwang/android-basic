#!/bin/sh

echo 'pre-push hook:'

checkGitStatus() {
    status=`git status|grep 'nothing to commit'`
    echo "$status"
    if [[ -z $status ]]; then
        echo "git changed"
        git add .
        git commit -m "update submodule by pre-commit hook"
        git push
    fi
}

cd basic
pwd
checkGitStatus

exit -1