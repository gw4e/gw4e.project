
#!/bin/bash
set -e # Exit with nonzero exit code if anything fails


SOURCE_BRANCH="master"
TARGET_BRANCH="p2repo"


# Pull requests and commits to other branches shouldn't try to deploy, just build to verify
if [ "$TRAVIS_PULL_REQUEST" != "false" -o "$TRAVIS_BRANCH" != "$SOURCE_BRANCH" ]; then
    echo "Skipping deploy; just doing a build."
    exit 0
fi


# Save some useful information
REPO=`git config remote.origin.url`
SSH_REPO=${REPO/https:\/\/github.com\//git@github.com:}
SHA=`git rev-parse --verify HEAD`

echo XXXXXXXXX $REPO
echo XXXXXXXXX $SSH_REPO
echo XXXXXXXXX $SHA

# Clone the existing branch for this repo into out/
# Create a new empty branch if TARGET_BRANCH doesn't exist yet (should only happen on first deply)
git clone $REPO out
cd out
git checkout $TARGET_BRANCH || git checkout --orphan $TARGET_BRANCH
cd ..

# Clean out existing contents
rm -rf out/* || exit 0

mkdir ./out/repository

echo XXXXXXXXXPWD
pwd
echo XXXXXXXXXLS
ls
echo XXXXXXXXX

unzip $HOME/.m2/repository/org/gw4e/tycho/org.gw4e.tycho.update/4.0.0-SNAPSHOT/org.gw4e.tycho.update-4.0.0-SNAPSHOT.zip -d ./out/repository

cd out
pwd 
# Commit the "changes", i.e. the new version.
# The delta will show diffs between new and old versions.
git add -A .
echo XXXXXX 3
git status
echo XXXXXX 4
git commit -m "Deploy to GitHub : ${SHA}"
echo XXXXXX 5

git show-ref
echo XXXXXX 6 $SSH_REPO $TARGET_BRANCH


# Now that we're all set up, we can push.
git push "https://${GH_TOKEN}@github.com/${GITHUB_REPO}.git" $TARGET_BRANCH




