@echo off
mkdir client 2>nul
( cd target_teavm_javascript && bash ./MakeOfflineDownload.sh )
copy /Y target_teavm_javascript\javascript\classes.js client\classes.js
copy /Y target_teavm_javascript\javascript\assets.epk client\assets.epk
copy /Y target_teavm_javascript\javascript\EaglercraftX_1.8_Offline_International.html client\Eaglercraft_Offline_JS.html
( cd target_teavm_wasm_gc && bash ./CompileBootstrapJS.sh && bash ./MakeWASMClientBundle.sh )
copy /Y target_teavm_wasm_gc\javascript_dist\bootstrap.js client\bootstrap.js
copy /Y target_teavm_wasm_gc\javascript_dist\assets.epw client\assets.epw
copy /Y target_teavm_wasm_gc\javascript_dist\EaglercraftX_1.8_WASM-GC_Offline_Download.html client\Eaglercraft_Offline_WASM.html
copy /Y src\web\index.html client\index.html