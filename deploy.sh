#!/bin/bash
YEAR=$(date +"%Y")
MONTH=$(date +"%m")
DAY=$(date +"%d")
HOUR=$(date +"%k%M")
git config --global user.email "${GIT_EMAIL}"
git config --global user.name "${GIT_NAME}"
git config --global push.default simple
git remote add gw4e https://github.com/gw4e/gw4e.project.git
export GIT_TAG=GW4E.$YEAR-$MONTH-$DAY-$HOUR.$TRAVIS_BUILD_NUMBER
git fetch --tags
msg="Tag Generated from TravisCI for build $TRAVIS_BUILD_NUMBER"
if git tag $GIT_TAG -a -m "$msg" 2>/dev/null; then
git tag $GIT_TAG -a -m "Tag Generated from TravisCI for build $TRAVIS_BUILD_NUMBER"
git push gw4e master && git push gw4e master --tags
ls -aR
else echo Tag already exists!; fi