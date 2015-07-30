OAuth2
==================
Google+, Facebook and Twitter authentication
---------------------------------------------

Many social services offer their own authentication providers and many users prefer to sign in via
a single button click rather than entering a username and a password.

This toolkit would have to allow the user to authenticate with the four most common social services:
Google+, Facebook, Twitter and LinkedIn. The task will be considered as complete once the android
client has received it’s short-lived authentication token from the OAuth2 provider.

#FACEBOOK
There are two steps to complete during the Facebook Authentication integration. The first step is
to create a standard facebook account which will be used to access the Facebook Developer console
that can be found at developers.facebook.com. When the access to the console is established,
a facebook app can be created. An important part to note is that a hash key will have to be obtained
by using java keytool on the android keystore which will be used to sign the android APK file.
This hash is used to verify the authenticity of the app we have created.
Second step requires the import of facebook SDK which is downloadable from the Facebook console.
Once the SDK has been imported, we can define a "Login with facebook" button and assign a callback
method which will be executed once the login process is complete.

#TWITTER
Authenticating with Twitter is almost identical to authentication with Facebook.
Twitter SDK, which can be downloaded from https://apps.twitter.com/, requires INTERNET and
ACCESS NETWORK STATE to work properly.

#GOOGLE+
Google+ configuration json file that provides application specific configuration is obtainable from
https://developers.google.com/. Google+ SDK required three permissions: INTERNET - for authentication,
GET_ACCOUNTS - to make autocompletion suggestions, and USE_CREDENTIALS - to access existing google
account on the android device. The rest of the integration process is very similar to
Facebook and Twitter procedures.
