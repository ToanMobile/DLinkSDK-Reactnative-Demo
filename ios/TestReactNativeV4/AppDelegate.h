#import <React/RCTBridgeDelegate.h>
#import <UIKit/UIKit.h>
#import <NetAloFull/NetAloFull-Swift.h>
#import <UserNotifications/UserNotifications.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate, UNUserNotificationCenterDelegate, RCTBridgeDelegate>

+ (AppDelegate *) sharedInstance;
@property (nonatomic, strong) UIWindow *window;
@property (nonatomic, strong) NetAloFull *sdk;

@end
