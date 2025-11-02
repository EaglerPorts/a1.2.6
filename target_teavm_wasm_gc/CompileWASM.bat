@echo off
cd ../
call scripts/wrapper.bat
call gradlew target_teavm_wasm_gc:assembleMainComponents
pause