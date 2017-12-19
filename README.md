# Synerise Android SDK - User documentation #

## Event Tracker ##

You can log events from your mobile app to Synerise platform with Tracker class.
First of all, you need to initialize Tracker with `init` method and provide `Api Key`, `Application name` and `Application instance`.
Init method can be called only once during whole application lifecycle, so it is recommended to call this method in your `Application` class.

### Api Key ###
To get `Api Key` sign in to your Synerise account and go to https://app.synerise.com/api/.
Please generate new `Api Key` for `Business Profile` Audience.

To send Event simply use `Tracker`:

```
Tracker.send(new CustomEvent("ButtonClick", "addEventButton"));
```

You can also pass your custom parameters:

```
TrackerParams params = new TrackerParams.Builder()
                .add("name", "John")
                .add("age", 27)
                .add("isGreat", true)
                .add("lastOrder", 384.28)
                .add("count", 0x7fffffffffffffffL)
                .add("someObject", new MySerializableObject(0))
                .build();

Tracker.send(new CustomEvent("ButtonClick", "addEventButton", params));
```

### Tracker ###

In your `Application` class:

```
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String syneriseApiKey = getString(R.string.synerise_api_key);
        String appName = getString(R.string.app_name);

        Tracker.init(this, syneriseApiKey, appName);
    }
```

#### In your /values strings file (e.g. `string.xml`): ####

```
<resources>
    <string name="app_name" translatable="false">Your GREAT application name</string>
    <string name="synerise_api_key" translatable="false">A75DA38F-A2E9-5A25-2884-38AD12B98FAA</string> <!-- replace with valid api key -->
</resources>
```

## Events ###

### Session Events ###
Group of events related to user's session.

#### LoggedInEvent ####
Record a 'client logged in' event.

#### LoggedOutEvent ####
Record a 'client logged out' event.


### Products Events ###
Group of events related to products and cart.

#### AddedToFavoritesEvent ####
Record a 'client added product to favorites' event.

#### AddedToCartEvent ####
Record a 'client added product to cart' event.

#### RemovedFromCartEvent ####
Record a 'client removed product from cart' event.


### Transaction Events ###
Group of events related to user's transactions.

#### CancelledTransactionEvent ####
Record a 'client cancelled transaction' event.

#### CompletedTransactionEvent ####
Record a 'client completed transaction' event.


### Other Events ###
Group of uncategorized events related to user's location and actions.

#### AppearedInLocationEvent ####
Record a 'client appeared in location' event.

#### HitTimerEvent ###
Record a 'client hit timer' event. This could be used for profiling or activity time monitoring - you can send "hit timer" when your client starts doing something and send it once again when finishes, but this time with different time signature. Then you can use our analytics engine to measure e.g. average activity time.

#### SearchedEvent ###
Record a 'client searched' event.

#### SharedEvent ###
Record a 'client shared' event.

#### VisitedScreenEvent ###
Record a 'client visited screen' event.


### Custom Event ###
This is the only event which requires `action` field. Log your custom data with TrackerParams class.


### Tracker.setDebugMode(isDebugModeEnabled) ###
This method enables/disables console logs from Tracker SDK.
It is not recommended to use debug mode in release version of your application.



## Client ###

First of all, you need to initialize Client with `init` method and provide `Api Key`, `Application name` and `Application instance`.
Init method can be called only once during whole application lifecycle, so it is recommended to call this method in your `Application` class.
In Client SDK you can also provide you custom `Authorization Configuration`. At this moment, configuration handles `Base URL` changes.

### Client.logIn(email, password, deviceId) ###
Log in a client in order to obtain the JWT token, which could be used in subsequent requests. The token is valid for 1 hour.
This SDK will refresh token before each call if it is expiring (but not expired).
Method requires valid and non-null email and password. Device ID is optional.
Method returns `IApiCall` to execute request.

### Client.getAccount() ###
Use this method to get client's account information.
This method returns `IDataApiCall` with parametrized `AccountInformation` object to execute request.

### Client.updateAccount(accountInformation) ###
Use this method to update client's account information.
This method requires `AccountInformation` Builder Pattern object with client's account information. Not provided fields are not modified.
Method returns `IApiCall` to execute request.

### Client.setDebugMode(isDebugModeEnabled) ###
This method enables/disables console logs from Client SDK.
It is not recommended to use debug mode in release version of your application.



## Profile ###

First of all, you need to initialize Profile with `init` method and provide `Api Key`, `Application name` and `Application instance`.
Init method can be called only once during whole application lifecycle, so it is recommended to call this method in your `Application` class.

### Profile.createClient(createClient) ###
Create a new client record if no identifier has been assigned for him before in Synerise.
This method requires `CreateClient` Builder Pattern object with client's optional data. Not provided fields are not modified.
Method returns IApiCall object to execute request.

### Profile.registerClient(registerClient) ###
Register new Client with email, password and optional data.
This method requires `RegisterClient` Builder Pattern object with client's email, password and optional data. Not provided fields are not modified.
Method returns IApiCall object to execute request.

### Profile.updateClient(updateClient) ###
Update client with ID and optional data.
This method requires `UpdateClient` Builder Pattern object with client's optional data. Not provided fields are not modified.
Method returns IApiCall object to execute request.

### Profile.deleteClient(id) ###
Delete client with ID.
This method requires client's id.
Method returns IApiCall object to execute request.

### Profile.requestPasswordReset(email) ###
Request client's password reset with email. Client will receive a token on provided email address in order to use Profile.confirmResetPassword(password, token).
This method requires client's email.
Method returns IApiCall object to execute request.

### Profile.confirmResetPassword(password, token) ###
Confirm client's password reset with new password and token provided by Profile.requestPasswordReset(email).
This method requires client's password and confirmation token sent on email address.
Method returns IApiCall object to execute request.

### Profile.setDebugMode(isDebugModeEnabled) ###
This method enables/disables console logs from Profile SDK.
It is not recommended to use debug mode in release version of your application.



## Injector ##

The Synerise Android InjectorSDK is designed to be simple to develop with, allowing you to integrate Synerise Mobile Content into your apps easily.

For more info about Synerise visit the [Synerise Website](http://synerise.com)

### Requirements ###

- Android minimum SDK 19

### Installation ###

#### Maven artifact ####

Import maven dependency in your project gradle file:
```
...
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url "https://codebite.bintray.com/public-test-repo"
        }
    }
}
```

Import dependency in your app gradle file:
```
...
dependencies {
  ...
  // Synerise Mobile SDK
  implementation 'com.synerise.sdk:synerise-mobile-sdk:3.0.0'
}
```

Sometimes MultiDex errors may occur. In that case please enable MultiDex as follows (API 21):
```
defaultConfig {
    applicationId "com.your.app"
    minSdkVersion 21
    ...
    multiDexEnabled true
}
```

### Configuration ###

#### Api Key ####
To get `Api Key` sign in to your Synerise account and go to https://app.synerise.com/api/.
Please generate new `Api Key` for `Business Profile` Audience.

#### Init ####

In your `Application` class:
```
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String apiKey = getString(R.string.businessProfileApiKey);
        String appId = getString(R.string.sample_app_name);

        Injector.init(this, apiKey, appId);
        Profile.init(this, apiKey, appId);
    }
```

In your ../values strings file (e.g. `string.xml`):
```
<resources>
    <string name="app_name" translatable="false">Your GREAT application name</string>
    <string name="businessProfileApiKey" translatable="false">A75DA38F-A2E9-5A25-2884-38AD12B98FAA</string> <!-- replace with valid api key -->
</resources>
```

After these steps you have fully initialized InjectorSDK and it's ready to work.

### Mobile Content integration ###

#### Banners ####

To integrate handling Mobile Content banners you have to register your app for push notifications first. Incoming push notifications have to be passed to `Injector`. `Injector` will then handle payload and display banner if provided payload is correctly validated.

### Handling push notifications ###

You have to pass incoming push notification payload to `Injector`in your `FirebaseMessagingService` implementation:

```
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Injector.handlePushPayload(remoteMessage.getData());
    }
}
```

Also register for push messages:
```
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        IApiCall call = Profile.registerForPush(refreshedToken);
        call.execute(new ActionListener() {
            @Override
            public void onAction() {
                Log.d(TAG, "Register for Push Successful " + refreshedToken);
            }
        }, new DataActionListener<ApiError>() {
            @Override
            public void onDataAction(ApiError apiError) {
              //handle error and try again later
            }
        });
    }
}
```

Please remember to register services in AndroidManifest as follows:
```
<application
        android:name=".App"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        ...

        <service
            android:name=".service.MyFirebaseMessagingService">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

        <service
            android:name=".service.MyFirebaseInstanceIDService">
            <intent-filter>
                <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
            </intent-filter>
        </service>

    </application>
```

### Onboarding and Welcome Screen ###

Welcome Screen and Onboarding methods are called on demand.

Call this in your `Activity` class eg. `SplashScreenActivity`
```
private static final int ONBOARDING_REQUEST_CODE = 201;
private static final String BUCKET_NAME = "yourBucketName";

Injector.showOnboardingIfPresent(this, ONBOARDING_REQUEST_CODE, BUCKET_NAME);
```

And get result in `onActivityResult` method:
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == ONBOARDING_REQUEST_CODE) {
        if (resultCode == Injector.ResultCodes.OK) {
            //onboarding has been shown and closed
            Log.d(getClass().getSimpleName(), "onActivityResult: ONBOARDING - OK");

        } else if (resultCode == Injector.ResultCodes.NOTHING_TO_SHOW) {
           // nothing has been shown
            Log.d(getClass().getSimpleName(), "onActivityResult: ONBOARDING - NOTHING_TO_SHOW");
        }
    }
}
```

For Welcome Screen it looks almost the same:
```
private static final int WELCOME_REQUEST_CODE = 202;
private static final String BUCKET_NAME = "yourBucketName";
...
Injector.showWelcomeScreenIfPresent(this, WELCOME_REQUEST_CODE, BUCKET_NAME);
```

And you also get result in `onActivityResult` method:
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  if (requestCode == WELCOME_REQUEST_CODE) {
         if (resultCode == Injector.ResultCodes.OK) {
             Log.d(getClass().getSimpleName(), "onActivityResult: WELCOME SCREEN - OK");
         } else if (resultCode == Injector.ResultCodes.NOTHING_TO_SHOW) {
             Log.d(getClass().getSimpleName(), "onActivityResult: WELCOME SCREEN - NOTHING_TO_SHOW");
         }
     }
}
```

## Author ##

Synerise, developer@synerise.com. If you need support please feel free and contact us.

## License ##

InjectorSDK is available under the MIT license. See the LICENSE file for more info.
