//
//  Injection.swift
//  NotificationExtension
//
//  Created by Hieu Bui Van  on 19/01/2022.
//

import Resolver
import NotificationComponent

extension MyResolver: ResolverRegistering {
    public static func registerAllServices() {
        register { NotificationComponentImpl(enviroment: BuildConfig.enviroment, appGroupId: BuildConfig.appGroupId) as NotificationComponentImpl }
            .scope(.cached)
    }
}
