@echo off
cd ../
call scripts/wrapper.bat
call gradlew target_teavm_javascript:assembleMainComponents
pause