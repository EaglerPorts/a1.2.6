#!/bin/sh
cd ../
sh ./scripts/wrapper.sh
./gradlew target_teavm_javascript:assembleMainComponents $@
