"use strict";var precacheConfig=[["/jhf/adm/index.html","1fbfc5269b6b73424e2a2764bdca34ee"],["/jhf/adm/static/css/main.e48ae35d.css","5fdf2f6bb29cb4273396fce84bb7d64b"],["/jhf/adm/static/js/0.420f3d2d.chunk.js","9f67d10c01454ae9618b9973ed914e35"],["/jhf/adm/static/js/1.5c448b08.chunk.js","9fa02edb06f5f2dedf91d13889cf41f1"],["/jhf/adm/static/js/10.413a0cdc.chunk.js","d2b91e97732c305d80a57fa584a2df0c"],["/jhf/adm/static/js/11.f4d3ab7a.chunk.js","394094426c8138ffeb413a5a6127874d"],["/jhf/adm/static/js/12.8dff3ef7.chunk.js","9b8aa6c91f45dce79bc58f7d58a68576"],["/jhf/adm/static/js/13.0e46b07b.chunk.js","cd8953a15655dd621acb6221fc6e9a33"],["/jhf/adm/static/js/14.7560cb9d.chunk.js","e7784ab4e3ec44fd2971d80f813e4854"],["/jhf/adm/static/js/15.cd24f27d.chunk.js","a472a79d8c891fbe60580b3941ed980a"],["/jhf/adm/static/js/16.ee8fbd9c.chunk.js","de776543395f9c60e5a7b4ffef1efbbd"],["/jhf/adm/static/js/17.30f7dfe3.chunk.js","318e323071183e9d54de2bd0b18058d2"],["/jhf/adm/static/js/18.0420a260.chunk.js","f8a5579e2678275bd35007287d63ae99"],["/jhf/adm/static/js/19.1b974fbf.chunk.js","70f698151db72c7edf9c1f6b2511da81"],["/jhf/adm/static/js/2.2ee2e175.chunk.js","270b6f9ad06f8fd18fb447bbe514e51a"],["/jhf/adm/static/js/20.f070609c.chunk.js","8eeadd23cada77c2f7ce6068dfa0fba7"],["/jhf/adm/static/js/21.e3e374ef.chunk.js","56432d1cfb753e8a6b10577a1530e2fc"],["/jhf/adm/static/js/22.fb2f168c.chunk.js","26eaeabddc890b7f0d40fc21a834c63c"],["/jhf/adm/static/js/3.6f5e34b9.chunk.js","0d253b2bd965105abb1b2fe75453acf3"],["/jhf/adm/static/js/4.87005fbe.chunk.js","acdeaa85887bd49e2e52addea37edee2"],["/jhf/adm/static/js/5.434ab705.chunk.js","fcd5bbad7b8545b9511a19b7f4a1cd62"],["/jhf/adm/static/js/6.2e3c333b.chunk.js","9cef0f81cbea3ecd619942b3f7492046"],["/jhf/adm/static/js/7.b131adfe.chunk.js","952a036176532a0f90d188bbcbc19289"],["/jhf/adm/static/js/8.0b97b265.chunk.js","9a67eee42eff10be63f795b1e1a8e6af"],["/jhf/adm/static/js/9.2011ceb4.chunk.js","2f54121e47af854355f0227e3ad3bd8b"],["/jhf/adm/static/js/main.f384ac9b.js","2294c0a71e9c56ba1d823a3f1b5216ad"],["/jhf/adm/static/media/bg.302916e1.svg","302916e1917bceccb1b142501cbbd41e"],["/jhf/adm/static/media/fontawesome-webfont.674f50d2.eot","674f50d287a8c48dc19ba404d20fe713"],["/jhf/adm/static/media/fontawesome-webfont.912ec66d.svg","912ec66d7572ff821749319396470bde"],["/jhf/adm/static/media/fontawesome-webfont.af7ae505.woff2","af7ae505a9eed503f8b8e6982036873e"],["/jhf/adm/static/media/fontawesome-webfont.b06871f2.ttf","b06871f281fee6b241d60582ae9369b9"],["/jhf/adm/static/media/fontawesome-webfont.fee66e71.woff","fee66e712a8a08eef5805a46892932ad"],["/jhf/adm/static/media/loginLogo.567eafca.png","567eafca26678a396c4ecc612a16697b"]],cacheName="sw-precache-v3-sw-precache-webpack-plugin-"+(self.registration?self.registration.scope:""),ignoreUrlParametersMatching=[/^utm_/],addDirectoryIndex=function(e,a){var t=new URL(e);return"/"===t.pathname.slice(-1)&&(t.pathname+=a),t.toString()},cleanResponse=function(e){return e.redirected?("body"in e?Promise.resolve(e.body):e.blob()).then(function(a){return new Response(a,{headers:e.headers,status:e.status,statusText:e.statusText})}):Promise.resolve(e)},createCacheKey=function(e,a,t,c){var n=new URL(e);return c&&n.pathname.match(c)||(n.search+=(n.search?"&":"")+encodeURIComponent(a)+"="+encodeURIComponent(t)),n.toString()},isPathWhitelisted=function(e,a){if(0===e.length)return!0;var t=new URL(a).pathname;return e.some(function(e){return t.match(e)})},stripIgnoredUrlParameters=function(e,a){var t=new URL(e);return t.hash="",t.search=t.search.slice(1).split("&").map(function(e){return e.split("=")}).filter(function(e){return a.every(function(a){return!a.test(e[0])})}).map(function(e){return e.join("=")}).join("&"),t.toString()},hashParamName="_sw-precache",urlsToCacheKeys=new Map(precacheConfig.map(function(e){var a=e[0],t=e[1],c=new URL(a,self.location),n=createCacheKey(c,hashParamName,t,/\.\w{8}\./);return[c.toString(),n]}));function setOfCachedUrls(e){return e.keys().then(function(e){return e.map(function(e){return e.url})}).then(function(e){return new Set(e)})}self.addEventListener("install",function(e){e.waitUntil(caches.open(cacheName).then(function(e){return setOfCachedUrls(e).then(function(a){return Promise.all(Array.from(urlsToCacheKeys.values()).map(function(t){if(!a.has(t)){var c=new Request(t,{credentials:"same-origin"});return fetch(c).then(function(a){if(!a.ok)throw new Error("Request for "+t+" returned a response with status "+a.status);return cleanResponse(a).then(function(a){return e.put(t,a)})})}}))})}).then(function(){return self.skipWaiting()}))}),self.addEventListener("activate",function(e){var a=new Set(urlsToCacheKeys.values());e.waitUntil(caches.open(cacheName).then(function(e){return e.keys().then(function(t){return Promise.all(t.map(function(t){if(!a.has(t.url))return e.delete(t)}))})}).then(function(){return self.clients.claim()}))}),self.addEventListener("fetch",function(e){if("GET"===e.request.method){var a,t=stripIgnoredUrlParameters(e.request.url,ignoreUrlParametersMatching),c="index.html";(a=urlsToCacheKeys.has(t))||(t=addDirectoryIndex(t,c),a=urlsToCacheKeys.has(t));var n="/jhf/adm/index.html";!a&&"navigate"===e.request.mode&&isPathWhitelisted(["^(?!\\/__).*"],e.request.url)&&(t=new URL(n,self.location).toString(),a=urlsToCacheKeys.has(t)),a&&e.respondWith(caches.open(cacheName).then(function(e){return e.match(urlsToCacheKeys.get(t)).then(function(e){if(e)return e;throw Error("The cached response that was expected is missing.")})}).catch(function(a){return console.warn('Couldn\'t serve response for "%s" from cache: %O',e.request.url,a),fetch(e.request)}))}});