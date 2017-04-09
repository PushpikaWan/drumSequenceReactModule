/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableHighlight,
  NativeModules
} from 'react-native';

let MyDrumSequence  = NativeModules.MyDrumSequence;
export default class drumSequence extends Component {

  componentDidMount(){
    noteSeq = "00010101010101010101010000000010";
    MyDrumSequence.initalizeSoundMatirx(8,200,noteSeq);
  }

   playSound(){
      MyDrumSequence.play();
      return true;
   }

   pause(){
      MyDrumSequence.pause();
   }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.android.js
        </Text>
        <Text style={styles.instructions}>
          Double tap R on your keyboard to reload,{'\n'}
          Shake or press menu button for dev menu
        </Text>
        <TouchableHighlight
            onPress={this.playSound.bind(this)}>
            <Text style={{ textAlign: 'center' }}>Play</Text>
        </TouchableHighlight>
        <TouchableHighlight
            onPress={this.pause.bind(this)}>
            <Text style={{ textAlign: 'center' }}>pause</Text>
        </TouchableHighlight>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('drumSequence', () => drumSequence);
