import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:textdetect_widget/textdetect_widget.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
  }

  void _openCamera() {
    var companies = ["Tourism Holdings Limited","Port of Tauranga Limited","Metlifecare Limited"];
    TextdetectWidget.openCamera(companies);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Text Detection Sample'),
        ),
        body: Center(
          child: CupertinoButton(
            onPressed: _openCamera,
            child: Text('Open Camera'),
          )
        ),
      ),
    );
  }
}