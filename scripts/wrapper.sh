if ! [ -f "gradlew" ]; then
  gradle wrapper
fi;
chmod +x gradlew