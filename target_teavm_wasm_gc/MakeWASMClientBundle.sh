#!/bin/sh
cd ../
sh ./scripts/wrapper.sh
./gradlew target_teavm_wasm_gc:makeMainWasmClientBundle
