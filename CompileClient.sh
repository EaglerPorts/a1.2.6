mkdir -p client
( cd target_teavm_javascript && sh ./MakeOfflineDownload.sh )
cp target_teavm_javascript/javascript/classes.js client/classes.js
cp target_teavm_javascript/javascript/assets.epk client/assets.epk
cp target_teavm_javascript/javascript/EaglercraftX_1.8_Offline_International.html client/Eaglercraft_Offline_JS.html
( cd target_teavm_wasm_gc && sh ./CompileBootstrapJS.sh && sh ./MakeWASMClientBundle.sh )
cp target_teavm_wasm_gc/javascript_dist/bootstrap.js client/bootstrap.js
cp target_teavm_wasm_gc/javascript_dist/assets.epw client/assets.epw
cp target_teavm_wasm_gc/javascript_dist/EaglercraftX_1.8_WASM-GC_Offline_Download.html client/Eaglercraft_Offline_WASM.html
cp src/web/index.html client/index.html