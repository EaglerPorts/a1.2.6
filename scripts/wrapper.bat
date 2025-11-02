@echo off
if not exist gradlew (
  gradle wrapper
)