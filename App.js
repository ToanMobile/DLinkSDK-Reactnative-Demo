/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component, useState } from "react";
import { NativeModules } from "react-native";
import {
  StyleSheet,
  Text,
  SafeAreaView,
  View,
  Button,
  TouchableOpacity,
  TextInput
} from "react-native";

const { NetAloSDK } = NativeModules;

const App = () => {
  // const [text, onChangeText] = React.useState("Useless Text");
  const [isProduction, setProduction] = useState(true);
  const [isSelect, setIsSelect] = useState(false);
  const [shouldShowA, setShouldShowA] = useState(false);
  const [shouldShowB, setShouldShowB] = useState(false);

  return (
    <SafeAreaView style={{ flex: 1 }}>
      <View style={styles.container}>
        <Button style={[styles.button]} title="Back" onPress={() => { setIsSelect(false) }} />
        <Text style={styles.bigtitle}>DLink Demo React Native</Text>
        <View style={styles.marginTitle} />
        {/* <TouchableOpacity
          style={[styles.button]}
          onPress={() => {
            NetAloSDK.setDomainLoadAvatarNetAloSdk("your_cdn");
          }}>
          <Text style={styles.title}>Init Config URL Avatar</Text>
        </TouchableOpacity> */}
        {!isSelect ? (
          <View>
            <View style={styles.margin} />
            <Text>DLink Demo Application, Please select user:</Text>
            <View style={styles.marginButton} />
            <TouchableOpacity
              style={[styles.button]}
              onPress={() => {
                NetAloSDK.setUser(
                  "4785074617331968",
                  "381118bd76901c806a4c815696358033ee9cf2c0" ,
                  "ipa\\dlink.netacom",
                  "",
                  "",
                  "ipa\\dlink.netacom"
                );
                setIsSelect(true);
                setShouldShowA(false);
                setShouldShowB(!shouldShowB);
              }}
            >
              <Text style={styles.title}>Set User A</Text>
            </TouchableOpacity>
            <View style={styles.margin} />
            <TouchableOpacity
              style={[styles.button]}
              onPress={() => {
                NetAloSDK.setUser(
                  "4785074617709103",
                  "045626a0acadb8dd4365991965a1c9bc1378Hvns" ,
                  "ToanMobile",
                  "",
                  "",
                  "ToanMobile"
                );
                setIsSelect(true);
                setShouldShowA(!shouldShowA);
                setShouldShowB(false);
              }}
            >
              <Text style={styles.title}>Set User B</Text>
            </TouchableOpacity>
          </View>
        ) : null}
        <View style={styles.margin} />
        {isSelect && (shouldShowA || shouldShowB) ? (
          <View>
            <TouchableOpacity
              style={[styles.button]}
              onPress={() => NetAloSDK.setLanguage("en")}
            >
              <Text style={styles.title}>Set Language EN</Text>
            </TouchableOpacity>
            <View style={styles.margin} />
          </View>
        ) : null}
        {isSelect && (shouldShowA || shouldShowB) ? (
          <View>
            <TouchableOpacity
              style={[styles.button]}
              onPress={() => NetAloSDK.setLanguage("vi")}
            >
              <Text style={styles.title}>Set Language VN</Text>
            </TouchableOpacity>
            <View style={styles.margin} />
          </View>
        ) : null}
        {isSelect && (shouldShowA || shouldShowB) ? (
          <View>
            <TouchableOpacity
              style={[styles.button]}
              onPress={() => NetAloSDK.showListConversations()}
            >
              <Text style={styles.title}>Show List Conversations</Text>
            </TouchableOpacity>
            <View style={styles.margin} />
          </View>
        ) : null}
        {isSelect && shouldShowA ? (
          <TouchableOpacity
            style={[styles.button]}
            onPress={() =>
              NetAloSDK.openChatWithUser(
                "281474977724836",
                "G20",
                "pFz0jhyeUzamyXcRx2dXkWUYApADL3Hcr2y6_nrCEV0qhblqq1Rzn4wyMxu2nqnH",
                "aaa@gmail.com",
                "+84101000020"
              )
            }
          >
            <Text style={styles.title}>Open Chat With User</Text>
          </TouchableOpacity>
        ) : null}
        {isSelect && shouldShowB ? (
          <TouchableOpacity
            style={[styles.button]}
            onPress={() =>
              NetAloSDK.openChatWithUser(
                "281474977724836",
                "G20",
                "pFz0jhyeUzamyXcRx2dXkWUYApADL3Hcr2y6_nrCEV0qhblqq1Rzn4wyMxu2nqnH",
                "aaa@gmail.com",
                "+84101000020"
              )
            } >
            <Text style={styles.title}>Open Chat With User</Text>
          </TouchableOpacity>
        ) : null}
        {isSelect && (shouldShowA || shouldShowB) ? (
          <View>
            <View style={styles.margin} />
            <TouchableOpacity
              style={[styles.button]}
              onPress={() => {
                NetAloSDK.logOut();
              }}>
              <Text style={styles.title}>LogOut</Text>
            </TouchableOpacity>
            <View style={styles.margin} />
          </View>
        ) : null}
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  input: {
    height: 50,
    padding: 10,
    margin: 18,
    fontSize: 14,
    borderWidth: 1,
    borderRadius: 10,
    borderColor: "#ff9900",
    backgroundColor: "rgba(0,0,0,0)",
  },
  button: {
    fontSize: 22,
    height: 50,
    backgroundColor: "#ff9900",
    borderRadius: 10,
    paddingHorizontal: 20,
    alignItems: "center",
    justifyContent: "center",
  },
  title: {
    textAlign: "center",
    fontSize: 22,
    color: "#fff",
  },
  bigtitle: {
    fontSize: 30,
    color: "#ff9900",
    textAlign: "center",
  },
  margin: {
    height: 20,
  },
  marginTitle: {
    height: 100,
  },
  marginButton: {
    height: 10,
  },
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
  },
});

function testConfigAvatarDomain() {
  NetAloSDK.setDomainLoadAvatarNetAloSdk('https://cdn.com/');
}

export default App;
