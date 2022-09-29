#import "AppDelegate.h"

#import <React/RCTBridge.h>
#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>

#ifdef FB_SONARKIT_ENABLED
#import <FlipperKit/FlipperClient.h>
#import <FlipperKitLayoutPlugin/FlipperKitLayoutPlugin.h>
#import <FlipperKitUserDefaultsPlugin/FKUserDefaultsPlugin.h>
#import <FlipperKitNetworkPlugin/FlipperKitNetworkPlugin.h>
#import <SKIOSNetworkPlugin/SKIOSNetworkAdapter.h>
#import <FlipperKitReactPlugin/FlipperKitReactPlugin.h>
#import <UserNotifications/UserNotifications.h>

static void InitializeFlipper(UIApplication *application) {
  FlipperClient *client = [FlipperClient sharedClient];
  SKDescriptorMapper *layoutDescriptorMapper = [[SKDescriptorMapper alloc] initWithDefaults];
  [client addPlugin:[[FlipperKitLayoutPlugin alloc] initWithRootNode:application withDescriptorMapper:layoutDescriptorMapper]];
  [client addPlugin:[[FKUserDefaultsPlugin alloc] initWithSuiteName:nil]];
  [client addPlugin:[FlipperKitReactPlugin new]];
  [client addPlugin:[[FlipperKitNetworkPlugin alloc] initWithNetworkAdapter:[SKIOSNetworkAdapter new]]];
  [client start];
}
#endif

@implementation AppDelegate

+ (AppDelegate *)sharedInstance {
  return (AppDelegate *)[UIApplication sharedApplication].delegate;
}

/*
 Init Netalo SDKs and config parameters
 Description enviroment:
  - developer  = 0
  - production = 2
 */
- (instancetype)init
{
  self = [super init];
  if (self) {
    NetaloConfiguration *config = [[NetaloConfiguration alloc]
                                   initWithEnviroment:0
                                   appId:17
                                   appKey:@"B2D89AC8B8ECF"
                                   accountKey:@"adminkey"
                                   appGroupIdentifier: @"group.vn.netacom.vndirect-dev"
                                   storeUrl:@"https://apps.apple.com/vn/app/vndirect/id1594533471"
                                   forceUpdateProfile:YES
                                   allowCustomUsername:YES
                                   allowCustomProfile:NO
                                   allowCustomAlert:NO
                                   allowAddContact:YES
                                   allowBlockContact:YES
                                   allowSetUserProfileUrl:NO
                                   allowEnableLocationFeature:NO
                                   allowTrackingUsingSDK:NO
                                   isHiddenEditProfile:YES
                                   allowAddNewContact:NO
                                   allowEditContact:NO
                                   isVideoCallEnable:YES
                                   isVoiceCallEnable:YES
                                   isHiddenSecretChat:YES
                                   isSyncDataInApp:YES
                                   allowReferralCode:NO
                                   searchByLike:YES
                                   allowReplaceCountrycode:NO
                                   isSyncContactInApp:YES
                                   permissions:@[[NSNumber numberWithInt:1]]
    ];
   
    _sdk = [[NetAloFull alloc] initWithConfig:config];
  }
  
  return self;
}


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  __weak typeof(self) weakSelf = self;
  
  [_sdk initializeObjc:^(BOOL status) {
    [weakSelf.sdk buildSDKModule];
    
    RCTBridge *bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:launchOptions];
    RCTRootView *rootView = [[RCTRootView alloc]
                             initWithBridge:bridge
                             moduleName:@"TestReactNativeV4"
                             initialProperties:nil];
    rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];
    weakSelf.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    UIViewController *rootViewController = [UIViewController new];
    rootViewController.view = rootView;
    weakSelf.window.rootViewController = rootViewController;
    [weakSelf.window makeKeyAndVisible];
  }];
  
  //Register push notification
  [self registerPush];
  
  BOOL success = [_sdk application:application didFinishLaunchingWithOptions:launchOptions];
  return success;
}

- (void)registerPush {
    UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
    center.delegate = self;
    [center requestAuthorizationWithOptions:(UNAuthorizationOptionSound | UNAuthorizationOptionAlert | UNAuthorizationOptionBadge) completionHandler:^(BOOL granted, NSError * _Nullable error){
        if( !error ){
            dispatch_async(dispatch_get_main_queue(), ^{
             [[UIApplication sharedApplication] registerForRemoteNotifications];
          });
        }
    }];
}

# pragma mark UNNotificationCenter Delegate Methods

- (void)userNotificationCenter:(UNUserNotificationCenter *)center
       willPresentNotification:(UNNotification *)notification
         withCompletionHandler:(void (^)(UNNotificationPresentationOptions options))completionHandler {
    NSLog(@"APPDELEGATE: willPresentNotification %@", notification.request.content.userInfo);
  [_sdk userNotificationCenter:center willPresentNotification:notification withCompletionHandler:completionHandler];
}

- (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)(void))completionHandler {

    NSLog(@"APPDELEGATE: didReceiveNotificationResponse: withCompletionHandler %@", response.notification.request.content.userInfo);
    // if you wish CleverTap to record the notification open and fire any deep links contained in the payload
  [_sdk userNotificationCenter:center didReceiveNotificationResponse:response withCompletionHandler:completionHandler];
}

//

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
  [_sdk application:application didRegisterForRemoteNotificationsWithDeviceToken:deviceToken];
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
  [_sdk applicationDidBecomeActive:application];
}

- (void)applicationWillTerminate:(UIApplication *)application {
  [_sdk applicationWillTerminate:application];
}

- (void)applicationWillResignActive:(UIApplication *)application {
  [_sdk applicationWillResignActive:application];
}

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge
{
#if DEBUG
  return [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index" fallbackResource:nil];
#else
  return [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];
#endif
}

@end
