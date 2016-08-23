# Android Keytool User Guide


- You want to integrate your Android app with Facebook Android SDK, Google Play Services or Firebase.
- With Facebook, you need to config key hash. You can use Facebook manual to get your own key hash.

  ###### On Windows
  
  - `C:\Users\Me>keytool -exportcert -alias <RELEASE_KEY_ALIAS> -keystore <RELEASE_KEY_PATH> -storepass <RELEASE_KEY_PASSWORD> > mycert.bin`
  - `C:\Users\Me>openssl sha1 -binary mycert.bin > sha1.bin`
  - `C:\Users\Me>openssl base64 -in sha1.bin -out base64.txt`
  
  References: http://stackoverflow.com/questions/4388992/key-hash-for-android-facebook-app#7564008
  
  ###### On Linux or Mac OS X
  
  - `keytool -exportcert -alias <RELEASE_KEY_ALIAS> -keystore <RELEASE_KEY_PATH> | openssl sha1 -binary | openssl base64`
  
  References: https://developers.facebook.com/docs/android/getting-started/#release-key-hash
  
- With Google Play Services or Firebase, you need to configure SHA1 fingerprint of the certificate

  - `keytool -list -keystore <RELEASE_KEY_PATH>`
  
  `keytool` will list alias inside keystore file. You can copy SHA1 fingerprint.
  
  References: https://developers.google.com/games/services/console/enabling

But you need to know command line :(.

Don't worry! Now you can get Facebook key hash or SHA1 fingerprint or MD5 of the certificate without command line. Both developer and production can use, so easy! You only need to install Java Runtime Environment (JRE).


#### Video demo

  https://www.youtube.com/watch?v=nIx4Ajq_3lI


#### Download

  Open `release` page https://github.com/quydm/android-keytool/releases and download latest jar file.



## Features

- Generate MD5, SHA1 and Facebook key hash
- Copy hashes to clipboard

## Upcoming features

- Get hashes from APK file. Don't need keystore file, don't need password