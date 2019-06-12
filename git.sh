remoteOrigin="https://github.com/qwertycody/Java-Response-Server.git"

rm -Rf ./target

if [ "$1" == "push" ]; then
    git add *
    git remote add origin "$remoteOrigin"
	echo "Type Commit Message:"
	read commitMessage
    git commit -m "$commitMessage"
    git push --force -u origin master    
fi

if [ "$1" == "pull" ]; then
    git remote add origin "$remoteOrigin"
    git pull origin master
fi

if [ "$1" == "setup" ]; then
    git init
    git remote add origin "$remoteOrigin"
    git pull origin master
fi
